package org.oxerr.peatio.websocket.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testAuth() throws JsonProcessingException {
		Auth auth = new Auth("myAccessKey", "myAnswer");
		String json = mapper.writeValueAsString(auth);
		assertEquals("{\"answer\":\"myAnswer\",\"access_key\":\"myAccessKey\"}", json);
	}

}
