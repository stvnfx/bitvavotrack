package org.acme.service;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import java.net.URI;
import java.util.List;
import jakarta.websocket.ContainerProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.dto.Channel;
import org.acme.dto.MarketDataDTO;
import org.acme.dto.SubscriptionMessage;
import org.jboss.logging.Logger;

@Startup
@ApplicationScoped
@ClientEndpoint
public class BitvavoClient {
    private static final Logger LOG = Logger.getLogger(BitvavoClient.class);

    @Inject
    BitvavoService bitvavoService;

    private Session session;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @PostConstruct
    public void init() {
        List<MarketDataDTO> allMarkets = bitvavoService.getAllMarkets();
        LOG.info("Got " + allMarkets.size() + " markets from Bitvavo REST API");
        allMarkets.forEach(market -> LOG.info(market.toString()));
        LOG.info("Attempting to connect to Bitvavo WebSocket...");
        try {
            URI uri = new URI("wss://ws.bitvavo.com/v2");
            ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
        } catch (Exception e) {
            LOG.error("Failed to connect to WebSocket", e);
        }
    }

    public void connect() throws Exception {
        URI uri = new URI("wss://ws.bitvavo.com/v2");
        ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to WebSocket: " + session.getId());

        try {
            // Create the Java object for the subscription message
            SubscriptionMessage subMessage = new SubscriptionMessage();
            subMessage.action = "subscribe";
            
            Channel candlesChannel = new Channel();
            candlesChannel.name = "candles";
            candlesChannel.markets = List.of("BTC-EUR");
            candlesChannel.interval = List.of("1m");
            
            subMessage.channels = List.of(candlesChannel);
            
            // Convert the object to a JSON string
            String jsonMessage = MAPPER.writeValueAsString(subMessage);
            
            // Send the message
            session.getAsyncRemote().sendText(jsonMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        LOG.info("Received message: " + message);
        // We'll also want to transform this incoming message from JSON to an object
        // but that's a whole other step! 😉
    }
}