package org.oxerr.peatio.rest.dto;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderBookTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		OrderBook orderBook = mapper.readValue(getClass().getResource("order_book.json"), OrderBook.class);

		assertEquals(2, orderBook.getAsks().length);
		assertEquals(2, orderBook.getBids().length);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		Order ask0 = orderBook.getAsks()[0];
		assertEquals(116012L, ask0.getId());
		assertEquals("sell", ask0.getSide());
		assertEquals("limit", ask0.getOrdType());
		assertEquals(new BigDecimal("3606.78"), ask0.getPrice());
		assertEquals(new BigDecimal("3606.78"), ask0.getAvgPrice());
		assertEquals("wait", ask0.getState());
		assertEquals("btccny", ask0.getMarket());
		assertEquals("2014-10-27T04:21:45+08:00", fmt.format(ask0.getCreatedAt()));
		assertEquals(new BigDecimal("2.22"), ask0.getVolume());
		assertEquals(new BigDecimal("1.1188"), ask0.getRemainingVolume());
		assertEquals(new BigDecimal("1.1012"), ask0.getExecutedVolume());

		Order bid0 = orderBook.getBids()[0];
		assertEquals(87515L, bid0.getId());
		assertEquals("buy", bid0.getSide());
		assertEquals("limit", bid0.getOrdType());
		assertEquals(new BigDecimal("3586.0"), bid0.getPrice());
		assertEquals(new BigDecimal("0.0"), bid0.getAvgPrice());
		assertEquals("wait", bid0.getState());
		assertEquals("btccny", bid0.getMarket());
		assertEquals("2014-08-03T23:23:15+08:00", fmt.format(bid0.getCreatedAt()));
		assertEquals(new BigDecimal("1.87"), bid0.getVolume());
		assertEquals(new BigDecimal("1.87"), bid0.getRemainingVolume());
		assertEquals(new BigDecimal("0.0"), bid0.getExecutedVolume());
	}

}
