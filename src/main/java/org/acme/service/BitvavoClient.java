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
import org.acme.entity.Candle;
import org.acme.entity.Ticker;
import org.acme.entity.Trade;
import org.acme.repository.CandleRepository;
import org.acme.repository.TickerRepository;
import org.acme.repository.TradeRepository;
import org.jboss.logging.Logger;

@Startup
@ApplicationScoped
@ClientEndpoint
public class BitvavoClient {
    private static final Logger LOG = Logger.getLogger(BitvavoClient.class);

    @Inject
    BitvavoService bitvavoService;

    @Inject
    CandleRepository candleRepository;

    @Inject
    TickerRepository tickerRepository;

    @Inject
    TradeRepository tradeRepository;

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
            List<String> markets = bitvavoService.getAllMarkets().stream().map(MarketDataDTO::getMarket).toList();

            SubscriptionMessage subMessage = new SubscriptionMessage();
            subMessage.action = "subscribe";
            
            Channel tickerChannel = new Channel();
            tickerChannel.name = "ticker";
            tickerChannel.markets = markets;

            Channel tradesChannel = new Channel();
            tradesChannel.name = "trades";
            tradesChannel.markets = markets;

            Channel candlesChannel = new Channel();
            candlesChannel.name = "candles";
            candlesChannel.markets = markets;
            candlesChannel.interval = List.of("1m");
            
            subMessage.channels = List.of(tickerChannel, tradesChannel, candlesChannel);
            
            String jsonMessage = MAPPER.writeValueAsString(subMessage);
            session.getAsyncRemote().sendText(jsonMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        LOG.info("Received message: " + message);
        try {
            com.fasterxml.jackson.databind.JsonNode rootNode = MAPPER.readTree(message);
            if (rootNode.has("event")) {
                String event = rootNode.get("event").asText();
                switch (event) {
                    case "ticker":
                        Ticker ticker = MAPPER.treeToValue(rootNode, Ticker.class);
                        LOG.info("Ticker: " + ticker.getMarket() + " " + ticker.getPrice());
                        tickerRepository.persist(ticker);
                        break;
                    case "trade":
                        Trade trade = MAPPER.treeToValue(rootNode, Trade.class);
                        LOG.info("Trade: " + trade.getMarket() + " " + trade.getPrice());
                        tradeRepository.persist(trade);
                        break;
                    case "candle":
                        Candle candle = MAPPER.treeToValue(rootNode, Candle.class);
                        LOG.info("Candle: " + candle.getMarket() + " " + candle.getClose());
                        candleRepository.persist(candle);
                        break;
                }
            }
        } catch (Exception e) {
            LOG.error("Failed to process message", e);
        }
    }
}
