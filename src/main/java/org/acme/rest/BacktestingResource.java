package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.acme.service.BacktestingService;

@Path("/backtest")
public class BacktestingResource {

    @Inject
    BacktestingService backtestingService;

    @GET
    public Response runBacktest(@QueryParam("market") String market) {
        if (market == null || market.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Market parameter is required").build();
        }
        backtestingService.runBacktest(market);
        return Response.ok("Backtest for market " + market + " completed.").build();
    }
}
