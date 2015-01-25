package org.oxerr.peatio.websocket;

import java.io.IOException;
import java.io.Reader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.oxerr.peatio.websocket.dto.OrderBook;
import org.oxerr.peatio.websocket.dto.Trade;

/**
 * Decoder to decode the WebSocket message to {@link PeatioData}.
 */
public class PeatioDecoder implements Decoder.TextStream<PeatioData> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(EndpointConfig config) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PeatioData decode(Reader reader) throws DecodeException, IOException {
		JsonReader jsonReader = Json.createReader(reader);
		JsonObject jsonObject = jsonReader.readObject();
		String type = jsonObject.keySet().stream().findFirst().get();
		Object object = decode(type, jsonObject);
		PeatioData peatioData = new PeatioData(type, object);
		return peatioData;
	}

	private Object decode(String type, JsonObject jsonObject) {
		final Object object;

		switch (type) {
		case "orderbook":
			object = new OrderBook(jsonObject.getJsonObject(type));
			break;
		case "trade":
			object = new Trade(jsonObject.getJsonObject(type));
			break;
		case "challenge":
			object = jsonObject.getString(type);
			break;
		case "success":
		case "error":
			object = jsonObject.getJsonObject(type).getString("message");
			break;
		default:
			object = null;
			break;
		}

		return object;
	}

}
