package org.acme.bitvavo.websocket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

// DTO for a channel within a subscription
public class ChannelDto {
    @JsonProperty("name")
    public String name;

    @JsonProperty("markets")
    public List<String> markets;

    // Use JsonInclude.Include.NON_NULL to exclude this property if it's null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("interval")
    public List<String> interval;

    public ChannelDto(String name, String market, String interval) {
        this.name = name;
        this.markets = Collections.singletonList(market);
        this.interval = (interval != null) ? Collections.singletonList(interval) : null;
    }
}