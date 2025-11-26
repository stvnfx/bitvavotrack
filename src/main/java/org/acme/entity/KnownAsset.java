package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class KnownAsset extends PanacheEntity {

    public String symbol;

    public KnownAsset() {
    }

    public KnownAsset(String symbol) {
        this.symbol = symbol;
    }
}
