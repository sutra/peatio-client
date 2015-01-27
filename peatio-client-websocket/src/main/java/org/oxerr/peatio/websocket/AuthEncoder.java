package org.oxerr.peatio.websocket;

import java.io.IOException;
import java.io.Writer;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.oxerr.peatio.websocket.dto.Auth;

public class AuthEncoder implements Encoder.TextStream<Auth> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(EndpointConfig config) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void encode(Auth auth, Writer writer) throws EncodeException,
			IOException {
		Json.createGenerator(writer)
			.writeStartObject()
				.writeStartObject("auth")
				.write("access_key", auth.getAccessKey())
				.write("answer", auth.getAnswer())
				.writeEnd()
			.writeEnd()
		.flush();
	}
}
