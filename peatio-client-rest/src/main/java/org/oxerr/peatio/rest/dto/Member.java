package org.oxerr.peatio.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member extends BaseObject {

	private final String sn;
	private final String name;
	private final String email;
	private final boolean activated;
	private final Account[] accounts;

	/**
	 *
	 * @param sn unique identifier of user.
	 * @param name user name.
	 * @param email user email.
	 * @param activated whether user is activated.
	 * @param accounts user's accounts info.
	 */
	public Member(
			@JsonProperty("sn") String sn,
			@JsonProperty("name") String name,
			@JsonProperty("email") String email,
			@JsonProperty("activated") boolean activated,
			@JsonProperty("accounts") Account[] accounts) {
		this.sn = sn;
		this.name = name;
		this.email = email;
		this.activated = activated;
		this.accounts = accounts;
	}

	public String getSn() {
		return sn;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public boolean isActivated() {
		return activated;
	}

	public Account[] getAccounts() {
		return accounts;
	}

}
