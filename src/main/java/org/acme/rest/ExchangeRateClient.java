package org.acme.rest;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.dto.ExchangeRateResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import org.jboss.resteasy.reactive.RestQuery;

@RegisterRestClient(baseUri = "https://api.frankfurter.app")
public interface ExchangeRateClient {

    @GET
    @Path("/latest")
    Uni<ExchangeRateResponse> getLatestRates(@RestQuery String base);
}
