package org.oxerr.peatio.rest;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Peatio exception.
 */
public class PeatioException extends RuntimeException {

	private static final long serialVersionUID = 2015010101L;

	private final Error error;

	public PeatioException(@JsonProperty("error") Error error) {
		super(error.getMessage());
		this.error = error;
	}

	public int getCode() {
		return error.getCode();
	}

	public static class Error implements Serializable {

		private static final long serialVersionUID = PeatioException.serialVersionUID;

		private final int code;
		private final String message;

		public Error(
				@JsonProperty("code") int code,
				@JsonProperty("message") String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}

}
