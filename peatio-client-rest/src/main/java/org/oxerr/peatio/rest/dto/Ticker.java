package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticker extends BaseObject {

	private final BigDecimal buy;
	private final BigDecimal sell;
	private final BigDecimal low;
	private final BigDecimal high;
	private final BigDecimal last;
	private final BigDecimal vol;

	public Ticker(
			@JsonProperty("buy") BigDecimal buy,
			@JsonProperty("sell") BigDecimal sell,
			@JsonProperty("low") BigDecimal low,
			@JsonProperty("high") BigDecimal high,
			@JsonProperty("last") BigDecimal last,
			@JsonProperty("vol") BigDecimal vol) {
		this.buy = buy;
		this.sell = sell;
		this.low = low;
		this.high = high;
		this.last = last;
		this.vol = vol;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public BigDecimal getLow() {
		return low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public BigDecimal getLast() {
		return last;
	}

	public BigDecimal getVol() {
		return vol;
	}

}
