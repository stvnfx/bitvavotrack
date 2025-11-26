package org.acme.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.repository.CandleRepository;
import org.jboss.logging.Logger;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.criteria.pnl.GrossProfitCriterion;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.rules.CrossedDownIndicatorRule;
import org.ta4j.core.rules.CrossedUpIndicatorRule;
import org.ta4j.core.BarSeriesManager;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class BacktestingService {

    private static final Logger LOG = Logger.getLogger(BacktestingService.class);

    @Inject
    CandleRepository candleRepository;

    public void runBacktest(String market) {
        LOG.info("Running backtest for market: " + market);

        final BarSeries series = new BaseBarSeriesBuilder().withName(market).build();

        Panache.withTransaction(() -> candleRepository.list("market = ?1 order by timestamp", market))
                .onItem().invoke(candles -> {
                    for (org.acme.entity.Candle candle : candles) {
                        ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(candle.getTimestamp()), ZoneId.systemDefault());
                        series.addBar(Duration.ofMinutes(1), dateTime, candle.getOpen(), candle.getHigh(), candle.getLow(), candle.getClose(), candle.getVolume());
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

                    GrossProfitCriterion totalProfit = new GrossProfitCriterion();
                    LOG.info("Total profit for " + market + ": " + totalProfit.calculate(series, tradingRecord));
                })
                .await().indefinitely();
    }
}
