package org.acme.service;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.MarketDataDTO;
import org.acme.rest.BitvavoMarketsClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class BitvavoService {

    // Inject the automatically generated REST Client
    @Inject
    @RestClient
    BitvavoMarketsClient marketsClient;

    /**
     * Retrieves all markets by calling the REST client.
     * This method provides a nice abstraction over the direct API call.
     *
     * @return A list of MarketDataDTO objects, or an empty list if an error occurs.
     */
    public List<MarketDataDTO> getAllMarkets() {
        Log.info("Fetching all markets from Bitvavo REST API...");
        try {
            return marketsClient.getMarkets();
        } catch (Exception e) {
            Log.error("Failed to fetch markets from Bitvavo API", e);
            return List.of();
        }
    }
}