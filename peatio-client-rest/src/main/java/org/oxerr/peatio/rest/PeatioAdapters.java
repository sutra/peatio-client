package org.oxerr.peatio.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.oxerr.peatio.rest.dto.Account;
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
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from Peatio DTOs to XChange DTOs.
 */
public final class PeatioAdapters {

	private PeatioAdapters() {
	}

	public static Collection<CurrencyPair> adaptCurrencyPairs(Market[] markets) {
		Collection<CurrencyPair> pairs = new ArrayList<CurrencyPair>(markets.length);
		for (Market market : markets) {
			pairs.add(adaptCurrencyPair(market));
		}
		return pairs;
	}

	public static CurrencyPair adaptCurrencyPair(Market market) {
		String[] parts = market.getName().split("/");
		return new CurrencyPair(parts[0], parts[1]);
	}

	public static CurrencyPair adaptCurrencyPair(String marketId) {
		int l = marketId.length();
		String counterSymbol = marketId.substring(l - 4, l - 1);
		String baseSymbol = marketId.substring(0, l - 4);
		return new CurrencyPair(baseSymbol, counterSymbol);
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

	public static OrderBook adaptOrderBook(org.oxerr.peatio.rest.dto.OrderBook orderBook) {
		List<LimitOrder> asks = adaptLimitOrders(orderBook.getAsks());
		List<LimitOrder> bids = adaptLimitOrders(orderBook.getBids());
		return new OrderBook(new Date(), asks, bids);
	}

	public static List<LimitOrder> adaptLimitOrders(Order[] orders) {
		List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);
		for (Order order : orders) {
			limitOrders.add(adaptLimitOrder(order));
		}
		return limitOrders;
	}

	public static LimitOrder adaptLimitOrder(Order order) {
		return new LimitOrder.Builder(
				adaptOrderType(order.getSide()), adaptCurrencyPair(order.getMarket()))
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

		return side.equals("sell") ? OrderType.ASK : OrderType.BID;
	}

	public static Trades adaptTrades(org.oxerr.peatio.rest.dto.Trade[] tradeArray) {
		List<Trade> tradeList = new ArrayList<Trade>(tradeArray.length);
		for (org.oxerr.peatio.rest.dto.Trade trade : tradeArray) {
			tradeList.add(adaptTrade(trade));
		}
		return new Trades(tradeList, TradeSortType.SortByID);
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
		List<Wallet> wallets = new ArrayList<Wallet>(member.getAccounts().length);
		for(Account account : member.getAccounts()) {
			Wallet wallet = new Wallet(account.getCurrency().toUpperCase(), account.getBalance());
			wallets.add(wallet);
		}
		AccountInfo accountInfo = new AccountInfo(member.getEmail(), wallets);
		return accountInfo;
	}

}
