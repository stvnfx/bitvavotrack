package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;

@ApplicationScoped
public class ServerTimeService {

    /**
     * Public method to get the current time as a Java Instant.
     * @return The current time as an Instant.
     */
    public Instant getNow() {
        // As per the user's request, this is now based on local system time.
        return Instant.now();
    }
    
    /**
     * Public method to get the current time as a Unix timestamp in milliseconds.
     * @return The current time as a Unix timestamp in milliseconds.
     */
    public long getCurrentServerTimestamp() {
        return Instant.now().toEpochMilli();
    }
}