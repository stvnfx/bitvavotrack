package org.acme.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.service.BitvavoTimeService;
import org.acme.service.ServerTimeService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

// The new health check class to expose the server and Bitvavo times.
@Readiness
@ApplicationScoped
class BitvavoTimeHealthCheck implements HealthCheck {
    
    @Inject
    BitvavoTimeService bitvavoTimeService;
    
    @Inject
    ServerTimeService serverTimeService;

    @Override
    public HealthCheckResponse call() {
        long localTimestamp = serverTimeService.getCurrentServerTimestamp();
        long bitvavoTimestamp = bitvavoTimeService.getCurrentServerTimestamp();
        long deviation = Math.abs(localTimestamp - bitvavoTimestamp);

        return HealthCheckResponse.named("Bitvavo Time Check")
                .withData("localTimestamp", localTimestamp)
                .withData("bitvavoTimestamp", bitvavoTimestamp)
                .withData("deviationMillis", deviation)
                .status(true)
                .build();
    }
}