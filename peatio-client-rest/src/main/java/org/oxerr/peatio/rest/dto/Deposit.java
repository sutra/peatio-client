package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Deposit information.
 */
public class Deposit extends BaseObject {

	private final String currency;
	private final BigDecimal amount;
	private final BigDecimal fee;
	private final String txid;
	private final Date createdAt;
	private final int confirmations;
	private final Date doneAt;
	private final String state;

	public Deposit(@JsonProperty("currency") String currency,
			@JsonProperty("amount") BigDecimal amount,
			@JsonProperty("fee") BigDecimal fee,
			@JsonProperty("txid") String txid,
			@JsonProperty("created_at")
			@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
			Date createdAt,
			@JsonProperty("confirmations") int confirmations,
			@JsonProperty("done_at")
			@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
			Date doneAt,
			@JsonProperty("state") String state) {
		this.currency = currency;
		this.amount = amount;
		this.fee = fee;
		this.txid = txid;
		this.createdAt = createdAt;
		this.confirmations = confirmations;
		this.doneAt = doneAt;
		this.state = state;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public String getTxid() {
		return txid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public Date getDoneAt() {
		return doneAt;
	}

	public String getState() {
		return state;
	}

}
