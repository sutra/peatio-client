package org.oxerr.peatio.websocket.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import javax.json.JsonObject;

public class Trade extends org.oxerr.peatio.rest.dto.Trade {

	private final Order bid, ask;

	public Trade(long id, BigDecimal price, BigDecimal volume,
			BigDecimal funds, String market, Date createdAt, String side,
			Order bid, Order ask) {
		super(id, price, volume, funds, market, createdAt, side, getOrderId(side, bid, ask));
		this.bid = bid;
		this.ask = ask;
	}

	public Trade (JsonObject jsonObject) {
		this(
			jsonObject.getJsonNumber("id").longValue(),
			new BigDecimal(jsonObject.getString("price")),
			new BigDecimal(jsonObject.getString("volume")),
			new BigDecimal(jsonObject.getString("funds")),
			jsonObject.getString("market"),
			Date.from(Instant.parse(jsonObject.getString("created_at"))),
			jsonObject.getString("side"),
			Order.from(jsonObject.getJsonObject("bid")),
			Order.from(jsonObject.getJsonObject("ask"))
		);
	}

	private static Long getOrderId(String side, Order bid, Order ask) {
		return side.equals("bid") ? bid.getId() : ask.getId();
	}

	public Order getOrder() {
		return getSide().equals("bid") ? bid : ask;
	}

}
