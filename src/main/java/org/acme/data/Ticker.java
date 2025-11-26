package org.acme.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "tickers")
public class Ticker extends PanacheEntity {

    /**
     * A reference to the Market entity this ticker data belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "market_id", nullable = false)
    public Market market;

    /**
     * The timestamp when the ticker event occurred.
     */
    @Column(nullable = false)
    public ZonedDateTime timestamp;

    /**
     * The best bid price.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal bestBid;

    /**
     * The size of the best bid.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal bestBidSize;

    /**
     * The best ask price.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal bestAsk;

    /**
     * The size of the best ask.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal bestAskSize;

    /**
     * The last trade price.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal lastPrice;
}