package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.dto.MarketDataDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.bitvavo.com/v2")
public interface BitvavoMarketsClient {

    @GET
    @Path("/markets")
    List<MarketDataDTO> getMarkets();

    @GET
    @Path("/assets")
    List<org.acme.dto.AssetDTO> getAssets();

    @GET
    @Path("/ticker/24h")
    List<org.acme.dto.Ticker24hDTO> getTicker24h();
}