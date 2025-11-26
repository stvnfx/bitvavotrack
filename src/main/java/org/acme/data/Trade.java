package org.acme.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "trades")
public class Trade extends PanacheEntity {

    /**
     * The unique order ID from the Bitvavo API. This helps with tracking.
     */
    @Column(nullable = false, unique = true)
    public String orderId;

    /**
     * A reference to the Market entity that was traded.
     */
    @ManyToOne
    @JoinColumn(name = "market_id", nullable = false)
    public Market market;

    /**
     * A reference to the User who made this trade.
     * We'll always assign a user here. If the trade wasn't made by a human user,
     * we'll create a special "system" user to assign it to, which keeps the database
     * clean and maintains the integrity of the `user_id` relationship.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    /**
     * The type of trade, either BUY or SELL.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TradeType type;

    /**
     * The timestamp when the trade was executed.
     */
    @Column(nullable = false)
    public ZonedDateTime timestamp;

    /**
     * The price at which the trade was executed.
     */
    @Column(precision = 19, scale = 8, nullable = false)
    public BigDecimal price;

    /**
     * The amount of the base currency that was traded.
     */
    @Column(precision = 19, scale = 8, nullable = false)
    public BigDecimal amount;

    /**
     * The fee paid for the trade.
     */
    @Column(precision = 19, scale = 8)
    public BigDecimal fee;
    
    /**
     * A reference to the Asset entity that was affected by this trade.
     * This establishes the link between a specific transaction and the user's holdings.
     */
    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    public Asset asset;
}