package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.repository.CandleRepository;
import org.jboss.logging.Logger;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.BarSeriesManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
public class BacktestingService {

    private static final Logger LOG = Logger.getLogger(BacktestingService.class);

    @Inject
    CandleRepository candleRepository;

    public void runBacktest(String market) {
        LOG.info("Running backtest for market: " + market);

        BarSeries series = new BaseBarSeries(market);
        List<org.acme.entity.Candle> candles = candleRepository.list("market = ?1 order by timestamp", market);
        for (org.acme.entity.Candle candle : candles) {
            ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(candle.getTimestamp()), ZoneId.systemDefault());
            series.addBar(dateTime, candle.getOpen(), candle.getHigh(), candle.getLow(), candle.getClose(), candle.getVolume());
        }

        if (series.isEmpty()) {
            LOG.warn("No data for market: " + market);
            return;
        }

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
        SMAIndicator longSma = new SMAIndicator(closePrice, 20);

        Rule entryRule = new CrossedUpIndicatorRule(shortSma, longSma);
        Rule exitRule = new CrossedDownIndicatorRule(shortSma, longSma);

        Strategy strategy = new BaseStrategy(entryRule, exitRule);

        BarSeriesManager seriesManager = new BarSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);

        TotalProfitCriterion totalProfit = new TotalProfitCriterion();
        LOG.info("Total profit for " + market + ": " + totalProfit.calculate(series, tradingRecord));
    }
}
