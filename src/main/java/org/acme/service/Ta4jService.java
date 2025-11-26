package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

@ApplicationScoped
public class Ta4jService {

    public BarSeries createBarSeries() {
        return new BaseBarSeriesBuilder().withName("my-series").build();
    }

    public SMAIndicator createSmaIndicator(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        return new SMAIndicator(closePrice, 12);
    }
}
