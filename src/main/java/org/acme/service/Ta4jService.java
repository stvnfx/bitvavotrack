package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

@ApplicationScoped
public class Ta4jService {

    public BarSeries createBarSeries() {
        return new BaseBarSeries("my-series");
    }

    public SMAIndicator createSmaIndicator(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        return new SMAIndicator(closePrice, 12);
    }
}
