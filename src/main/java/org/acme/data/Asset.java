package org.acme.data;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
public class Asset extends PanacheEntity {

    /**
     * The symbol of the currency (e.g., "BTC", "EUR").
     * We'll make this unique since we only need to track one balance per currency.
     */
    @Column(unique = true, nullable = false)
    public String symbol;

    /**
     * The total balance of the asset.
     */
    @Column(precision = 19, scale = 8, nullable = false)
    public BigDecimal balance;

    /**
     * A reference to the user who owns this asset.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;
}