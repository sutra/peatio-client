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
import org.oxerr.peatio.rest.dto.Order;

public class TradeTest {

	private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	@Test
	public void testParseTradeBid() throws IOException {
		try (
				InputStream inputStream = getClass().getResourceAsStream("trade-bid.json");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
				JsonReader reader = Json.createReader(inputStreamReader);
				) {
			Trade trade = new Trade(reader.readObject().getJsonObject("trade"));
			assertEquals(1750537L, trade.getId());
			assertEquals(new BigDecimal("1271.49"), trade.getPrice());
			assertEquals(new BigDecimal("0.01"), trade.getVolume());
			assertEquals(new BigDecimal("12.7149"), trade.getFunds());
			assertEquals("btccny", trade.getMarket());
			assertEquals("2015-01-20T07:47:26Z", fmt.format(trade.getCreatedAt()));
			assertEquals("bid", trade.getSide());

			Order bid = trade.getOrder();
			assertEquals(10049741L, bid.getId());
			assertEquals("buy", bid.getSide());
			assertEquals("limit", bid.getOrdType());
			assertEquals(new BigDecimal("1271.49"), bid.getPrice());
			assertEquals(new BigDecimal("1271.49"), bid.getAvgPrice());
			assertEquals("wait", bid.getState());
			assertEquals("btccny", bid.getMarket());
			assertEquals("2015-01-20T07:43:11Z", fmt.format(bid.getCreatedAt()));
			assertEquals(new BigDecimal("0.05"), bid.getVolume());
			assertEquals(new BigDecimal("0.04"), bid.getRemainingVolume());
			assertEquals(new BigDecimal("0.01"), bid.getExecutedVolume());
			assertEquals(new Integer(1), bid.getTradesCount());
		}
	}

	@Test
	public void testParseTradeAsk() throws IOException {
		try (
			InputStream inputStream = getClass().getResourceAsStream("trade-ask.json");
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, UTF_8);
			JsonReader reader = Json.createReader(inputStreamReader);
		) {
			Trade trade = new Trade(reader.readObject().getJsonObject("trade"));
			assertEquals(1750528L, trade.getId());
			assertEquals(new BigDecimal("1272.21"), trade.getPrice());
			assertEquals(new BigDecimal("0.05"), trade.getVolume());
			assertEquals(new BigDecimal("63.6105"), trade.getFunds());
			assertEquals("btccny", trade.getMarket());
			assertEquals("2015-01-20T07:45:36Z", fmt.format(trade.getCreatedAt()));
			assertEquals("ask", trade.getSide());

			Order ask = trade.getOrder();
			assertEquals(10049808L, ask.getId());
			assertEquals("sell", ask.getSide());
			assertEquals("limit", ask.getOrdType());
			assertEquals(new BigDecimal("1272.21"), ask.getPrice());
			assertEquals(new BigDecimal("1272.21"), ask.getAvgPrice());
			assertEquals("done", ask.getState());
			assertEquals("btccny", ask.getMarket());
			assertEquals("2015-01-20T07:45:36Z", fmt.format(ask.getCreatedAt()));
			assertEquals(new BigDecimal("0.05"), ask.getVolume());
			assertEquals(new BigDecimal("0.0"), ask.getRemainingVolume());
			assertEquals(new BigDecimal("0.05"), ask.getExecutedVolume());
			assertEquals(new Integer(1), ask.getTradesCount());
		}
	}

}
