package org.oxerr.peatio.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.oxerr.peatio.rest.dto.MarketTicker;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
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
		assertEquals(OrderType.ASK, PeatioAdapters.adaptOrderType("sell"));
		assertEquals(OrderType.BID, PeatioAdapters.adaptOrderType("bid"));
		assertEquals(OrderType.BID, PeatioAdapters.adaptOrderType("buy"));
	}

	@Test
	public void testAdaptTicker() throws JsonParseException,
			JsonMappingException, IOException {
		Ticker ticker = PeatioAdapters.adaptTicker(CurrencyPair.BTC_CNY, mapper
				.readValue(getClass().getResource("dto/btccny.json"),
						MarketTicker.class));
		assertEquals(CurrencyPair.BTC_CNY, ticker.getCurrencyPair());
		assertEquals(1420112237_000L, ticker.getTimestamp().getTime());
		assertEquals(new BigDecimal("3586.0"), ticker.getBid());
		assertEquals(new BigDecimal("3606.78"), ticker.getAsk());
		assertEquals(new BigDecimal("3606.78"), ticker.getLow());
		assertEquals(new BigDecimal("3606.78"), ticker.getHigh());
		assertEquals(new BigDecimal("3606.78"), ticker.getLast());
		assertEquals(new BigDecimal("0.0"), ticker.getVolume());
	}

	@Test
	public void testAdaptOrderBook() throws JsonParseException,
			JsonMappingException, IOException {
		OrderBook orderBook = PeatioAdapters.adaptOrderBook(CurrencyPair.BTC_CNY,
				mapper.readValue(
				getClass().getResource("dto/order_book.json"),
				org.oxerr.peatio.rest.dto.OrderBook.class));
		List<LimitOrder> asks = orderBook.getAsks();
		List<LimitOrder> bids = orderBook.getBids();

		assertEquals(2, asks.size());
		assertEquals(2, bids.size());

		LimitOrder ask0 = asks.get(0);
		assertEquals("116012", ask0.getId());
		assertEquals(OrderType.ASK, ask0.getType());
		assertEquals(new BigDecimal("3606.78"), ask0.getLimitPrice());
		assertEquals(CurrencyPair.BTC_CNY, ask0.getCurrencyPair());
		assertEquals("2014-10-27T04:21:45+08:00", fmt.format(ask0.getTimestamp()));
		assertEquals(new BigDecimal("1.1188"), ask0.getTradableAmount());

		LimitOrder bid0 = bids.get(0);
		assertEquals("87515", bid0.getId());
		assertEquals(OrderType.BID, bid0.getType());
		assertEquals(new BigDecimal("3586.0"), bid0.getLimitPrice());
		assertEquals(CurrencyPair.BTC_CNY, bid0.getCurrencyPair());
		assertEquals("2014-08-03T23:23:15+08:00", fmt.format(bid0.getTimestamp()));
		assertEquals(new BigDecimal("1.87"), bid0.getTradableAmount());
	}

	@Test
	public void testAdatpTrades() throws JsonParseException,
			JsonMappingException, IOException {
		Trades trades = PeatioAdapters.adaptTrades(mapper.readValue(getClass()
				.getResource("dto/trades.json"),
				org.oxerr.peatio.rest.dto.Trade[].class));
		assertEquals(47272L, trades.getlastID());

		List<Trade> tradeList = trades.getTrades();
		Trade trade = tradeList.get(0);
		assertEquals("47271", trade.getId());
		assertEquals(new BigDecimal("3606.78"), trade.getPrice());
		assertEquals(new BigDecimal("2.22"), trade.getTradableAmount());
		assertEquals(CurrencyPair.BTC_CNY, trade.getCurrencyPair());
		assertEquals("2014-12-09T16:48:38+08:00", fmt.format(trade.getTimestamp()));
		assertNull(trade.getType());
	}

	@Test
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
