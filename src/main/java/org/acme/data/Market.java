package org.acme.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "markets")
public class Market extends PanacheEntity {

    /**
     * The unique trading symbol for the market (e.g., "BTC-EUR").
     * We'll make this unique so we can easily find a market.
     */
    @Column(unique = true, nullable = false)
    public String symbol;

    /**
     * A human-readable name for the market (e.g., "Bitcoin/Euro").
     */
    public String name;

    /**
     * The base currency of the trading pair (e.g., "BTC").
     */
    public String baseCurrency;

    /**
     * The quote currency of the trading pair (e.g., "EUR").
     */
    public String quoteCurrency;

    /**
     * The minimum order size for this market.
     */
    public BigDecimal minOrderSize;

    /**
     * The maximum order size for this market.
     */
    public BigDecimal maxOrderSize;

    /**
     * The last recorded price. This can be updated periodically.
     */
    public BigDecimal lastPrice;

    /**
     * The last recorded volume.
     */
    public BigDecimal lastVolume;

    // We can also define relationships here. For example, a market can have
    // many candles, but we'll manage that relationship from the Candle entity.
}