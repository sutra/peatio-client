package org.oxerr.peatio.rest;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.oxerr.peatio.rest.dto.Depth;
import org.oxerr.peatio.rest.dto.Market;
import org.oxerr.peatio.rest.dto.MarketTicker;
import org.oxerr.peatio.rest.dto.Member;
import org.oxerr.peatio.rest.dto.Order;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from Peatio DTOs to XChange DTOs.
 */
public final class PeatioAdapters {

	private PeatioAdapters() {
	}

	public static Collection<CurrencyPair> adaptCurrencyPairs(Market[] markets) {
		return Arrays.stream(markets).map(market -> adaptCurrencyPair(market)).collect(toList());
	}

	public static CurrencyPair adaptCurrencyPair(Market market) {
		String[] parts = market.getName().split("/");
		return new CurrencyPair(parts[0], parts[1]);
	}

	public static CurrencyPair adaptCurrencyPair(String marketId) {
		int l = marketId.length();
		String counterSymbol = marketId.substring(l - 3, l);
		String baseSymbol = marketId.substring(0, l - 3);
		return new CurrencyPair(baseSymbol.toUpperCase(), counterSymbol.toUpperCase());
	}

	public static String adaptMarketId(CurrencyPair currencyPair) {
		return (currencyPair.baseSymbol + currencyPair.counterSymbol).toLowerCase();
	}

	public static Ticker adaptTicker(CurrencyPair currencyPair, MarketTicker ticker) {
		return new Ticker.Builder()
			.currencyPair(currencyPair)
			.timestamp(ticker.getAt())
			.bid(ticker.getTicker().getBuy())
			.ask(ticker.getTicker().getSell())
			.low(ticker.getTicker().getLow())
			.high(ticker.getTicker().getHigh())
			.last(ticker.getTicker().getLast())
			.volume(ticker.getTicker().getVol())
			.build();
	}

	/**
	 * Adapt market depth to generic order book.
	 *
	 * @param currencyPair the currency pair.
	 * @param depth the raw depth.
	 * @return the generic order book.
	 */
	public static OrderBook adaptOrderBook(CurrencyPair currencyPair, Depth depth) {
		List<LimitOrder> asks = adaptLimitOrders(currencyPair, OrderType.ASK, depth.getAsks());
		List<LimitOrder> bids = adaptLimitOrders(currencyPair, OrderType.BID, depth.getBids());
		return new OrderBook(depth.getTimestamp(), asks, bids);
	}

	/**
	 * Adapt depth items to limit order list.
	 *
	 * @param currencyPair the currency pair.
	 * @param orderType the order type, bid or ask.
	 * @param items the depth items.
	 * @return the sorted limit order list.
	 */
	public static List<LimitOrder> adaptLimitOrders(CurrencyPair currencyPair,
			OrderType orderType, BigDecimal[][] items) {
		return Arrays
			.stream(items)
			.map(
				item -> new LimitOrder.Builder(orderType, currencyPair)
					.limitPrice(item[0])
					.tradableAmount(item[1])
					.build()
			)
			.sorted()
			.collect(toList());
	}

	public static List<LimitOrder> adaptLimitOrders(Order[] orders) {
		return Arrays.stream(orders).map(order -> adaptLimitOrder(order)).collect(toList());
	}

	public static List<LimitOrder> adaptLimitOrders(Market market, Order[] orders) {
		CurrencyPair currencyPair = adaptCurrencyPair(market);
		return adaptLimitOrders(currencyPair, orders);
	}

	public static List<LimitOrder> adaptLimitOrders(CurrencyPair currencyPair, Order[] orders) {
		return Arrays.stream(orders).map(order -> adaptLimitOrder(currencyPair, order)).collect(toList());
	}

	public static LimitOrder adaptLimitOrder(Order order) {
		return adaptLimitOrder(adaptCurrencyPair(order.getMarket()), order);
	}

	public static LimitOrder adaptLimitOrder(CurrencyPair currencyPair, Order order) {
		return new LimitOrder.Builder(
				adaptOrderType(order.getSide()), currencyPair)
			.id(String.valueOf(order.getId()))
			.limitPrice(order.getPrice())
			.timestamp(order.getCreatedAt())
			.tradableAmount(order.getRemainingVolume())
			.build();
	}

	public static OrderType adaptOrderType(String side) {
		if (side == null) {
			return null;
		}

		OrderType orderType;
		switch (side) {
		case "sell":
		case "ask":
			orderType = OrderType.ASK;
			break;
		case "buy":
		case "bid":
			orderType = OrderType.BID;
			break;
		default:
			throw new IllegalArgumentException("Unknow order side: " + side);
		}
		return orderType;
	}

	public static String adaptSide(OrderType orderType) {
		return orderType == OrderType.ASK ? "sell" : "buy";
	}

	public static Trades adaptTrades(
			org.oxerr.peatio.rest.dto.Trade[] tradeArray) {
		List<Trade> tradeList = Arrays.stream(tradeArray)
				.map(trade -> adaptTrade(trade)).collect(toList());
		return new Trades(tradeList,
				tradeList.size() > 0 ? new Long(tradeList.get(0).getId()) : null,
				TradeSortType.SortByID);
	}

	public static Trade adaptTrade(org.oxerr.peatio.rest.dto.Trade trade) {
		return new Trade.Builder()
			.id(String.valueOf(trade.getId()))
			.price(trade.getPrice())
			.tradableAmount(trade.getVolume())
			.currencyPair(adaptCurrencyPair(trade.getMarket()))
			.timestamp(trade.getCreatedAt())
			.type(adaptOrderType(trade.getSide()))
			.build();
	}

	public static AccountInfo adaptMember(Member member) {
		List<Wallet> wallets = Arrays
				.stream(member.getAccounts())
				.map(account -> new Wallet(account.getCurrency().toUpperCase(),
						account.getBalance())).collect(toList());
		AccountInfo accountInfo = new AccountInfo(member.getEmail(), wallets);
		return accountInfo;
	}

	public static OpenOrders adaptOpenOrders(Map<Market, Order[]> ordersMap) {
		List<LimitOrder> openOrders = ordersMap
				.entrySet()
				.stream()
				.map(e -> adaptLimitOrders(e.getKey(), e.getValue()))
				.flatMap(x -> x.stream())
				.collect(toList());
		return new OpenOrders(openOrders);
	}

	public static UserTrade adaptUserTrade(CurrencyPair currencyPair,
			org.oxerr.peatio.rest.dto.Trade trade) {
		return new UserTrade.Builder()
			.id(String.valueOf(trade.getId()))
			.price(trade.getPrice())
			.tradableAmount(trade.getVolume())
			.currencyPair(currencyPair)
			.timestamp(trade.getCreatedAt())
			.type(adaptOrderType(trade.getSide()))
			.orderId(String.valueOf(trade.getOrderId()))
			.build();
	}

	public static List<UserTrade> adaptUserTradeList(Market market,
			org.oxerr.peatio.rest.dto.Trade[] trade) {
		return adaptUserTradeList(adaptCurrencyPair(market), trade);
	}

	public static List<UserTrade> adaptUserTradeList(CurrencyPair currencyPair,
			org.oxerr.peatio.rest.dto.Trade[] trades) {
		return Arrays.stream(trades)
				.map(trade -> adaptUserTrade(currencyPair, trade))
				.collect(toList());
	}

	public static UserTrades adaptUserTrades(CurrencyPair currencyPair,
			org.oxerr.peatio.rest.dto.Trade[] trades) {
		long lastID = trades.length > 0 ? trades[trades.length - 1].getId() : 0;
		return new UserTrades(adaptUserTradeList(currencyPair, trades), lastID,
				TradeSortType.SortByID);
	}

	public static UserTrades adaptUserTrades(
			Map<Market, org.oxerr.peatio.rest.dto.Trade[]> tradesMap) {
		List<UserTrade> trades = tradesMap
				.entrySet()
				.stream()
				.map(e -> adaptUserTradeList(e.getKey(), e.getValue()))
				.flatMap(x -> x.stream())
				.collect(toList());
		int size = trades.size();
		long lastID = size > 0 ? Long.parseLong(trades.get(size - 1).getId()) : 0;
		return new UserTrades(trades, lastID, TradeSortType.SortByID);
	}

}
