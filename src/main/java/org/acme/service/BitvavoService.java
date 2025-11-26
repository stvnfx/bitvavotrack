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

    @Inject
    @RestClient
    BitvavoMarketsClient marketsClient;

    public List<MarketDataDTO> getAllMarkets() {
        Log.info("Fetching all markets from Bitvavo REST API...");
        try {
            List<MarketDataDTO> markets = marketsClient.getMarkets();
            Log.info("Successfully fetched " + markets.size() + " markets.");
            return markets;
        } catch (Exception e) {
            Log.error("Failed to fetch markets from Bitvavo API", e);
            return List.of();
        }
    }

    public List<org.acme.dto.AssetDTO> getAllAssets() {
        Log.info("Fetching all assets from Bitvavo REST API...");
        try {
            List<org.acme.dto.AssetDTO> assets = marketsClient.getAssets();
            Log.info("Successfully fetched " + assets.size() + " assets.");
            return assets;
        } catch (Exception e) {
            Log.error("Failed to fetch assets from Bitvavo API", e);
            return List.of();
        }
    }

    public List<org.acme.dto.Ticker24hDTO> getTicker24h() {
        Log.info("Fetching 24h ticker data from Bitvavo REST API...");
        try {
            List<org.acme.dto.Ticker24hDTO> ticker24h = marketsClient.getTicker24h();
            Log.info("Successfully fetched " + ticker24h.size() + " ticker 24h data.");
            return ticker24h;
        } catch (Exception e) {
            Log.error("Failed to fetch 24h ticker data from Bitvavo API", e);
            return List.of();
        }
    }
}