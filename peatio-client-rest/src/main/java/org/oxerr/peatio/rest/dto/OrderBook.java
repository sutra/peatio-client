package org.oxerr.peatio.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Order book shows orders on market.
 */
public class OrderBook extends BaseObject {

	private final Order[] asks;
	private final Order[] bids;

	public OrderBook(
			@JsonProperty("asks") Order[] asks,
			@JsonProperty("bids") Order[] bids) {
		this.asks = asks;
		this.bids = bids;
	}

	public Order[] getAsks() {
		return asks;
	}

	public Order[] getBids() {
		return bids;
	}

}
