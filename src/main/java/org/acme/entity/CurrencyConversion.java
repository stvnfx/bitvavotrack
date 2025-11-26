package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class CurrencyConversion extends PanacheEntity {

    public String fromCurrency;
    public String toCurrency;
    public double rate;

    public CurrencyConversion() {
    }

    public CurrencyConversion(String fromCurrency, String toCurrency, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }
}
