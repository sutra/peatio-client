package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order extends BaseObject {

	private final long id;
	private final String side;
	private final String ordType;
	private final BigDecimal price;
	private final BigDecimal avgPrice;
	private final String state;
	private final String market;
	private final Date createdAt;
	private final BigDecimal volume;
	private final BigDecimal remainingVolume;
	private final BigDecimal executedVolume;

	/**
	 *
	 * @param id unique order ID.
	 * @param side Buy/Sell.
	 * @param ordType the order type.
	 * @param price order price.
	 * @param avgPrice average execution price.
	 * @param state wait, done or cancel.
	 * 'wait' represents the order is active, it may be a new order or partial complete order;
	 * 'done' means the order has been fulfilled completely;
	 * 'cancel' means the order has been cancelled.
	 * @param market which market the order belongs to.
	 * @param createdAt order created time.
	 * @param volume volume to buy/sell.
	 * @param remaingVolume remaining volume is always less than or equal to volume.
	 * @param executedVolume volume = remaingVolume + executedVolume.
	 */
	public Order(
			@JsonProperty("id") long id,
			@JsonProperty("side") String side,
			@JsonProperty("ord_type") String ordType,
			@JsonProperty("price") BigDecimal price,
			@JsonProperty("avg_price") BigDecimal avgPrice,
			@JsonProperty("state") String state,
			@JsonProperty("market") String market,
			@JsonProperty("created_at")
			@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX") Date createdAt,
			@JsonProperty("volume") BigDecimal volume,
			@JsonProperty("remaining_volume") BigDecimal remainingVolume,
			@JsonProperty("executed_volume") BigDecimal executedVolume) {
		this.id = id;
		this.side = side;
		this.ordType = ordType;
		this.price = price;
		this.avgPrice = avgPrice;
		this.state = state;
		this.market = market;
		this.createdAt = createdAt;
		this.volume = volume;
		this.remainingVolume = remainingVolume;
		this.executedVolume = executedVolume;
	}

	public long getId() {
		return id;
	}

	public String getSide() {
		return side;
	}

	public String getOrdType() {
		return ordType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public String getState() {
		return state;
	}

	public String getMarket() {
		return market;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public BigDecimal getRemainingVolume() {
		return remainingVolume;
	}

	public BigDecimal getExecutedVolume() {
		return executedVolume;
	}

}
