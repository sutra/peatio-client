package org.oxerr.peatio.rest.dto;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MarketTickerTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testTickerObject() throws JsonParseException, JsonMappingException, IOException {
		MarketTicker tickerObject = mapper.readValue(getClass().getResource("btccny.json"), MarketTicker.class);
		assertEquals(1420112237L, tickerObject.getAt().getTime() / 1000);
	}

}
