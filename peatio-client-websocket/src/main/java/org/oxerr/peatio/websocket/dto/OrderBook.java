package org.oxerr.peatio.websocket.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.json.JsonObject;

import org.oxerr.peatio.rest.dto.BaseObject;

public class OrderBook extends BaseObject {

	private final String action;
	private final Order order;
	private final String locale;

	public OrderBook(String action, Order order, String locale) {
		this.action = action;
		this.order = order;
		this.locale = locale;
	}

	public OrderBook(JsonObject jsonObject) {
		this.action = jsonObject.getString("action");
		this.order = new Order(jsonObject.getJsonObject("order"));
		this.locale = jsonObject.getString("locale");
	}

	public String getAction() {
		return action;
	}

	public Order getOrder() {
		return order;
	}

	public String getLocale() {
		return locale;
	}

	public static class Order extends BaseObject {
		private final long id;
		private final Instant timestamp;
		private final String type;
		private final BigDecimal volume;
		private final BigDecimal price;
		private final String market;
		private final String ordType;

		public Order(JsonObject jsonObject) {
			this.id = jsonObject.getJsonNumber("id").longValue();
			this.timestamp = Instant.ofEpochSecond(jsonObject.getJsonNumber("timestamp").longValue());
			this.type = jsonObject.getString("type");
			this.volume = new BigDecimal(jsonObject.getString("volume"));
			this.price = new BigDecimal(jsonObject.getString("price"));
			this.market = jsonObject.getString("market");
			this.ordType = jsonObject.getString("ord_type");
		}

		public long getId() {
			return id;
		}

		public Instant getTimestamp() {
			return timestamp;
		}

		public String getType() {
			return type;
		}

		public BigDecimal getVolume() {
			return volume;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public String getMarket() {
			return market;
		}

		public String getOrdType() {
			return ordType;
		}

	}

}
