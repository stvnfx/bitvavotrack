package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Trade;

@ApplicationScoped
public class TradeRepository implements PanacheRepository<Trade> {
}
