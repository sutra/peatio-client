package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KWithPendingTrades extends BaseObject {

	private final BigDecimal[][] k;
	private final Trade[] trades;

	public KWithPendingTrades(
			@JsonProperty("k") BigDecimal[][] k,
			@JsonProperty("trades") Trade[] trades) {
		this.k = k;
		this.trades = trades;
	}

	public BigDecimal[][] getK() {
		return k;
	}

	public Trade[] getTrades() {
		return trades;
	}

}
