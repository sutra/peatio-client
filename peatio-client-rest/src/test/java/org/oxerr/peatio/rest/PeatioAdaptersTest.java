package org.oxerr.peatio.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;

public class PeatioAdaptersTest {

	private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	private static final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testAdaptCurrencyPair() {
		assertEquals(CurrencyPair.BTC_CNY, PeatioAdapters.adaptCurrencyPair("btccny"));
	}

	@Test
	public void testAdaptMarketId() {
		assertEquals("btccny", PeatioAdapters.adaptMarketId(CurrencyPair.BTC_CNY));
	}

	@Test
	public void testAdaptOrderType() {
		assertNull(PeatioAdapters.adaptOrderType(null));
		assertEquals(OrderType.ASK, PeatioAdapters.adaptOrderType("ask"));
		assertEquals(OrderType.BID, PeatioAdapters.adaptOrderType("bid"));
	}

	public void testAdatpUserTrades() throws JsonParseException, JsonMappingException, IOException {
		org.oxerr.peatio.rest.dto.Trade[] trades = mapper.readValue(getClass().getResource("dto/myTrades.json"), org.oxerr.peatio.rest.dto.Trade[].class);
		List<UserTrade> userTrades = PeatioAdapters.adaptUserTradeList(CurrencyPair.BTC_CNY, trades);
		assertEquals(2, userTrades.size());

		UserTrade userTrade = userTrades.get(0);
		assertEquals("1618495", userTrade.getId());
		assertEquals(new BigDecimal("1678.06"), userTrade.getPrice());
		assertEquals(new BigDecimal("0.01"), userTrade.getTradableAmount());
		assertEquals(CurrencyPair.BTC_CNY, userTrade.getCurrencyPair());
		assertEquals("2015-01-11T17:44:08+08:00", fmt.format(userTrade.getTimestamp()));
		assertEquals(OrderType.BID, userTrade.getType());
		assertEquals("9181222", userTrade.getOrderId());
	}

}
