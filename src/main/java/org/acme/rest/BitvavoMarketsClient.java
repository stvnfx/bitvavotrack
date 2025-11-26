package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.dto.MarketDataDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.bitvavo.com/v2")
@Path("/markets")
public interface BitvavoMarketsClient {

    /**
     * Gets a list of all markets from the Bitvavo REST API.
     * @return A list of MarketDataDTO objects.
     */
    @GET
    List<MarketDataDTO> getMarkets();
}