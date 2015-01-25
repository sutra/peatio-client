package org.oxerr.peatio.websocket.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import javax.json.JsonObject;

public class Order extends org.oxerr.peatio.rest.dto.Order {

	public Order(JsonObject jsonObject) {
		super(
			jsonObject.getJsonNumber("id").longValue(),
			jsonObject.getString("side"),
			jsonObject.getString("ord_type"),
			new BigDecimal(jsonObject.getString("price")),
			new BigDecimal(jsonObject.getString("avg_price")),
			jsonObject.getString("state"),
			jsonObject.getString("market"),
			Date.from(Instant.parse(jsonObject.getString("created_at"))),
			new BigDecimal(jsonObject.getString("volume")),
			new BigDecimal(jsonObject.getString("remaining_volume")),
			new BigDecimal(jsonObject.getString("executed_volume")),
			new Integer(jsonObject.getInt("trades_count"))
		);
	}

	public static Order from(JsonObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		Order order = new Order(jsonObject);

		return order;
	}

}
