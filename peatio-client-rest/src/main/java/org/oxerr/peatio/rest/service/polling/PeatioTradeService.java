package org.oxerr.peatio.rest.service.polling;

import static org.oxerr.peatio.rest.PeatioAdapters.adaptMarketId;
import static org.oxerr.peatio.rest.PeatioAdapters.adaptOpenOrders;
import static org.oxerr.peatio.rest.PeatioAdapters.adaptSide;
import static org.oxerr.peatio.rest.PeatioAdapters.adaptUserTrades;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.oxerr.peatio.rest.PeatioAdapters;
import org.oxerr.peatio.rest.dto.Market;
import org.oxerr.peatio.rest.dto.Order;
import org.oxerr.peatio.rest.dto.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamsAll;

/**
 * Trade service.
 */
public class PeatioTradeService extends PeatioTradeServiceRaw implements
		PollingTradeService {

	private final Logger log = LoggerFactory.getLogger(PeatioTradeService.class);

	public PeatioTradeService(ExchangeSpecification exchangeSpecification,
			SynchronizedValueFactory<Long> tonce) {
		super(exchangeSpecification, tonce);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OpenOrders getOpenOrders() throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		Market[] markets = getMarkets();
		Map<Market, Order[]> ordersMap = new LinkedHashMap<>(markets.length);
		for (Market market : markets) {
			Order[] orders = getOrders(market.getId(), null, null, null, null);
			ordersMap.put(market, orders);
		}
		return adaptOpenOrders(ordersMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String placeMarketOrder(MarketOrder marketOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		String market = adaptMarketId(marketOrder.getCurrencyPair());
		String side = adaptSide(marketOrder.getType());
		Order order = placeOrder(market, side, marketOrder.getTradableAmount(),
				null, "market");
		log.debug("order: {}", order);
		return String.valueOf(order.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String placeLimitOrder(LimitOrder limitOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		String market = adaptMarketId(limitOrder.getCurrencyPair());
		String side = adaptSide(limitOrder.getType());
		Order order = placeOrder(market, side, limitOrder.getTradableAmount(),
				limitOrder.getLimitPrice(), "limit");
		log.debug("order: {}", order);
		return String.valueOf(order.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cancelOrder(String orderId) throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		Order order = deleteOrder(Long.parseLong(orderId));
		log.debug("order: {}", order);

		// the state before we deleting it, is 'wait', that means we cancelled it successfully.
		return order.getState().equals("wait");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTrades getTradeHistory(Object... arguments)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		Market[] markets = getMarkets();
		Map<Market, Trade[]> ordersMap = new LinkedHashMap<>(markets.length);
		for (Market market : markets) {
			Trade[] trades = getMyTrades(market.getId(), null, null, null, null, null);
			ordersMap.put(market, trades);
		}
		return adaptUserTrades(ordersMap);


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTrades getTradeHistory(TradeHistoryParams params)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		PeatioTradeHistoryParams p = (PeatioTradeHistoryParams) params;
		String market = PeatioAdapters.adaptMarketId(p.getCurrencyPair());
		Integer limit = p.getPageLength();
		Long timestamp = p.getEndTime() == null ? null : p.getEndTime().getTime();
		Long from = p.getStartId() == null ? null : new Long(p.getStartId());
		Long to = p.getEndId() == null ? null : new Long(p.getEndId());
		String orderBy = p.getOrderBy();
		return adaptUserTrades(p.getCurrencyPair(),
				getMyTrades(market, limit, timestamp, from, to, orderBy));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TradeHistoryParams createTradeHistoryParams() {
		return new TradeHistoryParamsAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<CurrencyPair, ? extends TradeServiceHelper> getTradeServiceHelperMap()
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotAvailableFromExchangeException();
	}

	public class PeatioTradeHistoryParams extends TradeHistoryParamsAll {

		private String orderBy;

		public String getOrderBy() {
			return orderBy;
		}

		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
		}

	}

}
