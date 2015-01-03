package org.oxerr.peatio.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account extends BaseObject {

	private final String currency;
	private final BigDecimal balance;
	private final BigDecimal locked;

	/**
	 *
	 * @param currency account type, like 'btc' or 'cny'.
	 * @param balance account balance, exclude locked funds.
	 * @param locked locked funds.
	 */
	public Account(
			@JsonProperty("currency") String currency,
			@JsonProperty("balance") BigDecimal balance,
			@JsonProperty("locked") BigDecimal locked) {
		this.currency = currency;
		this.balance = balance;
		this.locked = locked;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal getLocked() {
		return locked;
	}

}
