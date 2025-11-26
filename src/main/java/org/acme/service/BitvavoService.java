package org.acme.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.AssetDTO;
import org.acme.dto.MarketDataDTO;
import org.acme.dto.Ticker24hDTO;
import org.acme.rest.BitvavoMarketsClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class BitvavoService {

    @Inject
    @RestClient
    BitvavoMarketsClient marketsClient;

    public Uni<List<MarketDataDTO>> getAllMarkets() {
        Log.info("Fetching all markets from Bitvavo REST API...");
        return marketsClient.getMarkets()
                .onItem().invoke(markets -> Log.info("Successfully fetched " + markets.size() + " markets."))
                .onFailure().invoke(e -> Log.error("Failed to fetch markets from Bitvavo API", e));
    }

    public Uni<List<AssetDTO>> getAllAssets() {
        Log.info("Fetching all assets from Bitvavo REST API...");
        return marketsClient.getAssets()
                .onItem().invoke(assets -> Log.info("Successfully fetched " + assets.size() + " assets."))
                .onFailure().invoke(e -> Log.error("Failed to fetch assets from Bitvavo API", e));
    }

    public Uni<List<Ticker24hDTO>> getTicker24h() {
        Log.info("Fetching 24h ticker data from Bitvavo REST API...");
        return marketsClient.getTicker24h()
                .onItem().invoke(ticker24h -> Log.info("Successfully fetched " + ticker24h.size() + " ticker 24h data."))
                .onFailure().invoke(e -> Log.error("Failed to fetch 24h ticker data from Bitvavo API", e));
    }

    public Uni<List<List<Object>>> getCandles(String market, String interval) {
        Log.info("Fetching candles from Bitvavo REST API...");
        return marketsClient.getCandles(market, interval)
                .onItem().invoke(candles -> Log.info("Successfully fetched " + candles.size() + " candles."))
                .onFailure().invoke(e -> Log.error("Failed to fetch candles from Bitvavo API", e));
    }
}