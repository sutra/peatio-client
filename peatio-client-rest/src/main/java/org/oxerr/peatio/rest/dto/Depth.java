package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Depth extends BaseObject {

	private final Date timestamp;
	private final BigDecimal[][] asks;
	private final BigDecimal[][] bids;

	public Depth(
			@JsonProperty("timestamp")
			@JsonDeserialize(using = UnixTimestampDeserializer.class) Date timestamp,
			@JsonProperty("asks") BigDecimal[][] asks,
			@JsonProperty("bids") BigDecimal[][] bids) {
		this.timestamp = timestamp;
		this.asks = asks;
		this.bids = bids;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public BigDecimal[][] getAsks() {
		return asks;
	}

	public BigDecimal[][] getBids() {
		return bids;
	}

}
