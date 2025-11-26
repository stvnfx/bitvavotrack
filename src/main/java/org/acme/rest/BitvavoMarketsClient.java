package org.acme.rest;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.dto.AssetDTO;
import org.acme.dto.MarketDataDTO;
import org.acme.dto.Ticker24hDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.bitvavo.com/v2")
public interface BitvavoMarketsClient {

    @GET
    @Path("/markets")
    Uni<List<MarketDataDTO>> getMarkets();

    @GET
    @Path("/assets")
    Uni<List<AssetDTO>> getAssets();

    @GET
    @Path("/ticker/24h")
    Uni<List<Ticker24hDTO>> getTicker24h();

    @GET
    @Path("/{market}/candles")
    Uni<List<List<Object>>> getCandles(@org.jboss.resteasy.reactive.RestPath String market, @org.jboss.resteasy.reactive.RestQuery String interval);
}