package org.oxerr.peatio.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Market extends BaseObject {

	private final String id;
	private final String name;

	public Market(
			@JsonProperty("id") String id,
			@JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
