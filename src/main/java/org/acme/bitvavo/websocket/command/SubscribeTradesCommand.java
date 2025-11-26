package org.acme.bitvavo.websocket.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.acme.bitvavo.websocket.dto.BaseRequestDto;
import org.acme.bitvavo.websocket.dto.ChannelDto;
import org.acme.bitvavo.websocket.dto.SubscriptionRequestDto;
import org.acme.util.ObjectMapperFactory;

import java.util.Collections;
import java.util.List;

// Concrete command for subscribing to trades
class SubscribeTradesCommand extends AbstractJsonCommand {
    private final String market;

    public SubscribeTradesCommand(String market) {
        this.market = market;
    }

    @Override
    protected BaseRequestDto getDto() {
        List<ChannelDto> channels = Collections.singletonList(new ChannelDto("trades", this.market, null));
        return new SubscriptionRequestDto(channels);
    }
}