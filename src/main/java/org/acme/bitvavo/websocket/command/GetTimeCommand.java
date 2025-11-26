package org.acme.bitvavo.websocket.command;

import org.acme.bitvavo.websocket.dto.BaseRequestDto;
import org.acme.bitvavo.websocket.dto.GetTimeRequestDto;

public class GetTimeCommand extends AbstractJsonCommand {
    private final int requestId;

    public GetTimeCommand(int requestId) {
        this.requestId = requestId;
    }

    @Override
    protected BaseRequestDto getDto() {
        return new GetTimeRequestDto(this.requestId);
    }
}