package org.acme.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.MarketDataDTO;
import org.acme.service.BitvavoService;

import java.util.stream.Collectors;

@Path("/api/v1/coins")
public class CoinDataResource {

    @Inject
    BitvavoService bitvavoService;

    @GET
    @Path("/market/{pair}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getMarketData(@PathParam("pair") String pair, @org.jboss.resteasy.reactive.RestQuery String interval) {
        return bitvavoService.getCandles(pair, interval)
                .onItem().transform(candles -> Response.ok(candles).build());
    }

    @GET
    @Path("/info/{symbol}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getCoinInfo(@PathParam("symbol") String symbol) {
        return bitvavoService.getAllAssets()
                .onItem().transform(allAssets -> allAssets.stream()
                        .filter(asset -> symbol.equalsIgnoreCase(asset.getSymbol()))
                        .findFirst()
                        .orElse(null))
                .onItem().transform(assetInfo -> {
                    if (assetInfo == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok(assetInfo).build();
                });
    }
}
