package org.acme.service;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entity.CurrencyConversion;
import org.acme.rest.ExchangeRateClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.stream.Collectors;

@ApplicationScoped
public class CurrencyConversionService {

    @Inject
    @RestClient
    ExchangeRateClient exchangeRateClient;

    @Scheduled(every = "30m")
    public void updateConversionRates() {
        exchangeRateClient.getLatestRates("EUR")
                .subscribe().with(response -> {
                    Panache.withTransaction(() ->
                            CurrencyConversion.deleteAll()
                                    .chain(() -> CurrencyConversion.persist(response.rates.entrySet().stream()
                                            .map(entry -> new CurrencyConversion(response.base, entry.getKey(), entry.getValue()))
                                            .collect(Collectors.toList())))
                    ).onFailure().invoke(e -> {
                        Log.error("Failed to update currency conversion rates", e);
                    }).subscribe().with(v -> {});
                });
    }
}
