package org.acme.bitvavo.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// DTO for the getTime request
public class GetTimeRequestDto extends BaseRequestDto {
    @JsonProperty("requestId")
    public int requestId;

    public GetTimeRequestDto(int requestId) {
        this.action = "getTime";
        this.requestId = requestId;
    }
}