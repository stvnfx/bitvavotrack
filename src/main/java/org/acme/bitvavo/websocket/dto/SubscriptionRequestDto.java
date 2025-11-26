package org.acme.bitvavo.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// DTO for subscription requests (both trades and candles)
public class SubscriptionRequestDto extends BaseRequestDto {
    @JsonProperty("channels")
    public List<ChannelDto> channels;

    public SubscriptionRequestDto(List<ChannelDto> channels) {
        super();
    }
}