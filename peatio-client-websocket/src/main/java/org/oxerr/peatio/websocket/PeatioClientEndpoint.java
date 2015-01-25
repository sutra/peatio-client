package org.oxerr.peatio.websocket;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.oxerr.peatio.rest.service.PeatioDigest;
import org.oxerr.peatio.websocket.dto.Auth;
import org.oxerr.peatio.websocket.dto.OrderBook;
import org.oxerr.peatio.websocket.dto.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Peatio WebSocket client endpoint.
 */
@ClientEndpoint(decoders = PeatioDecoder.class, encoders = AuthEncoder.class)
public class PeatioClientEndpoint {

	private final Logger log = LoggerFactory
			.getLogger(PeatioClientEndpoint.class);

	private final String accessKey, secretKey;

	public PeatioClientEndpoint(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		log.trace("open: {}, config: {}", session, config);
	}

	@OnMessage
	public void onMessage(PeatioData data, Session session) throws IOException,
			EncodeException {
		final String type = data.getType();
		final Object object = data.getObject();

		switch (type) {
		case "orderbook":
			onOrderBook((OrderBook) object, session);
			break;
		case "trade":
			onTrade((Trade) object, session);
			break;
		case "challenge":
			onChallenge((String) object, session);
			break;
		case "success":
			log.trace("success: {}", object);
			break;
		case "error":
			log.trace("error: {}", object);
			break;
		default:
			log.warn("Unknown data type: {}", type);
			break;
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		log.trace("close: {}, reason: {}", session, reason);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.trace("error: {}", session, throwable);
	}

	protected void onChallenge(String challenge, Session session)
			throws IOException, EncodeException {
		String payload = accessKey + challenge;
		log.debug("payload: {}", payload);
		String answer = new PeatioDigest(secretKey).sign(payload);
		log.debug("answer: {}", answer);
		Auth auth = new Auth(accessKey, answer);
		session.getBasicRemote().sendObject(auth);
	}

	protected void onOrderBook(OrderBook orderBook, Session sesssion) {
		log.trace("orderbook: {}", orderBook);
	}

	protected void onTrade(Trade trade, Session session) {
		log.trace("trade: {}", trade);
	}

}
