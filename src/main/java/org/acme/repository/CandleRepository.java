package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Candle;

@ApplicationScoped
public class CandleRepository implements PanacheRepository<Candle> {
}
