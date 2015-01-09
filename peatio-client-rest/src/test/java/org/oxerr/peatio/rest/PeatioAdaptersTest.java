package org.oxerr.peatio.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;

public class PeatioAdaptersTest {

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
		assertEquals(OrderType.ASK, PeatioAdapters.adaptOrderType("sell"));
		assertEquals(OrderType.BID, PeatioAdapters.adaptOrderType("buy"));
	}

}
