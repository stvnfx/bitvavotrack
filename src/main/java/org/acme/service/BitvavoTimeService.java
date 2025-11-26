package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class BitvavoTimeService {
    private final AtomicLong lastKnownTimestamp = new AtomicLong(0);

    /**
     * Public method for other services to get the latest timestamp in milliseconds.
     * @return The last known server timestamp.
     */
    public long getCurrentServerTimestamp() {
        return lastKnownTimestamp.get();
    }

    /**
     * Public method for other services to get the latest time as a Java Instant.
     * @return The last known server time as an Instant.
     */
    public Instant getCurrentServerInstant() {
        return Instant.ofEpochMilli(lastKnownTimestamp.get());
    }

}
