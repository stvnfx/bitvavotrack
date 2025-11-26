package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Ticker;

@ApplicationScoped
public class TickerRepository implements PanacheRepository<Ticker> {
}
