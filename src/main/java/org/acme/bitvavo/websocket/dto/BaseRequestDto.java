package org.acme.bitvavo.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Base class for all requests
public class BaseRequestDto {
    @JsonProperty("action")
    public String action;
}