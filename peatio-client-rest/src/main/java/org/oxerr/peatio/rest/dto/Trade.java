package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.oxerr.peatio.rest.Peatio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Trade extends BaseObject {

	private final long id;
	private final BigDecimal price;
	private final BigDecimal volume;
	private final BigDecimal funds;
	private final String market;
	private final Date createdAt;
	private final String side;
	private final Long orderId;

	/**
	 *
	 * @param id unique ID.
	 * @param price trade price.
	 * @param volume trade volume.
	 * @param funds the funds.
	 * @param market the market trade belongs to, like 'btccny'.
	 * @param createdAt trade time.
	 * @param side the side.
	 * @param orderId my order ID related to this trade.
	 * Only {@link Peatio#getMyTrades} returns this field,
	 * indicates which order this trade belongs to.
	 */
	public Trade(
			@JsonProperty("id") long id,
			@JsonProperty("price") BigDecimal price,
			@JsonProperty("volume") BigDecimal volume,
			@JsonProperty("funds") BigDecimal funds,
			@JsonProperty("market") String market,
			@JsonProperty("created_at")
			@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX") Date createdAt,
			@JsonProperty("side") String side,
			@JsonProperty("order_id") Long orderId) {
		this.id = id;
		this.price = price;
		this.volume = volume;
		this.funds = funds;
		this.market = market;
		this.createdAt = createdAt;
		this.side = side;
		this.orderId = orderId;
	}

	public long getId() {
		return id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public BigDecimal getFunds() {
		return funds;
	}

	public String getMarket() {
		return market;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getSide() {
		return side;
	}

	public Long getOrderId() {
		return orderId;
	}

}
