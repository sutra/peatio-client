package org.oxerr.peatio.websocket.dto;

import org.oxerr.peatio.rest.dto.BaseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The answer for the challenge message.
 */
public class Auth extends BaseObject {

	@JsonProperty("access_key")
	private final String accessKey;
	private final String answer;

	public Auth(String accessKey, String answer) {
		this.accessKey = accessKey;
		this.answer = answer;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getAnswer() {
		return answer;
	}

}
