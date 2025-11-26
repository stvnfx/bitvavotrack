package org.acme.data;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "candles", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"market_id", "timestamp"})
})
public class Candle extends PanacheEntityBase {

    /**
     * We're manually defining the primary key to be a unique identifier
     * for each candle record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * A reference to the Market entity this candle belongs to.
     * @ManyToOne indicates that many candles can belong to one market.
     */
    @ManyToOne
    @JoinColumn(name = "market_id", nullable = false)
    public Market market;

    /**
     * The timestamp of the candle. This will be the time-series
     * dimension for TimescaleDB.
     */
    @Column(nullable = false)
    public ZonedDateTime timestamp;

    /**
     * The opening price of the candle.
     */
    @Column(precision = 19, scale = 8) // Precision is important for prices
    public BigDecimal open;

    /**
     * The highest price reached during the candle's period.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal high;

    /**
     * The lowest price reached during the candle's period.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal low;

    /**
     * The closing price of the candle.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal close;

    /**
     * The trading volume during the candle's period.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal volume;

    // We can add a simple equals and hashCode for entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candle candle = (Candle) o;
        return Objects.equals(id, candle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}