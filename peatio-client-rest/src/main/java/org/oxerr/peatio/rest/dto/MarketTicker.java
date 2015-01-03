package org.oxerr.peatio.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class MarketTicker extends BaseObject {

	private final Date at;
	private final Ticker ticker;

	public MarketTicker(
			@JsonProperty("at")
			@JsonDeserialize(using = UnixTimestampDeserializer.class) Date at,
			@JsonProperty("ticker") Ticker ticker) {
		this.at = at;
		this.ticker = ticker;
	}

	public Date getAt() {
		return at;
	}

	public Ticker getTicker() {
		return ticker;
	}

}
