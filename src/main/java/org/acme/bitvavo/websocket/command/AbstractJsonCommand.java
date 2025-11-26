package org.acme.bitvavo.websocket.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.acme.bitvavo.websocket.dto.BaseRequestDto;
import org.acme.util.ObjectMapperFactory;

// New abstract base class to handle common serialization logic
abstract class AbstractJsonCommand implements WebSocketCommand {

    /**
     * This method is implemented by subclasses to return the specific DTO.
     *
     * @return The DTO to be serialized.
     */
    protected abstract BaseRequestDto getDto();

    @Override
    public String toJson() {
        try {
            return ObjectMapperFactory.getObjectMapper().writeValueAsString(getDto());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}