package org.oxerr.peatio.websocket.dto;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.json.Json;
import javax.json.JsonReader;

import org.junit.BeforeClass;
import org.junit.Test;

public class OrderBookTest {

	private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	@Test
	public void testOrderBookAdd() throws IOException {
		try (
				InputStream inputStream = getClass().getResourceAsStream("orderbook-add.json");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
				JsonReader reader = Json.createReader(inputStreamReader);
				) {
			OrderBook orderBook = new OrderBook(reader.readObject().getJsonObject("orderbook"));
			assertEquals("add", orderBook.getAction());
			OrderBook.Order order = orderBook.getOrder();
			assertEquals(10049800L, order.getId());
			assertEquals(1421739930L, order.getTimestamp().getEpochSecond());
			assertEquals("ask", order.getType());
			assertEquals(new BigDecimal("0.0109"), order.getVolume());
			assertEquals(new BigDecimal("1272.27"), order.getPrice());
			assertEquals("btccny", order.getMarket());
			assertEquals("limit", order.getOrdType());
			assertEquals("zh-CN", orderBook.getLocale());
		}
	}

}
