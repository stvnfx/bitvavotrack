package org.acme.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.acme.entity.Candle;

import java.io.IOException;

public class CandleDeserializer extends JsonDeserializer<Candle> {

    @Override
    public Candle deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Candle candle = new Candle();
        candle.setMarket(node.get("market").asText());
        JsonNode candleNode = node.get("candle");
        if (candleNode.isArray() && candleNode.size() > 0) {
            JsonNode candleData = candleNode.get(0);
            if (candleData.isArray() && candleData.size() == 6) {
                candle.setTimestamp(candleData.get(0).asLong());
                candle.setOpen(candleData.get(1).asDouble());
                candle.setHigh(candleData.get(2).asDouble());
                candle.setLow(candleData.get(3).asDouble());
                candle.setClose(candleData.get(4).asDouble());
                candle.setVolume(candleData.get(5).asDouble());
            }
        }
        return candle;
    }
}
