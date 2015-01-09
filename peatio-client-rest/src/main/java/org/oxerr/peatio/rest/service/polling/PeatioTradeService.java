package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

/**
 * Trade service.
 */
public class PeatioTradeService extends PeatioTradeServiceRaw implements
		PollingTradeService {

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
		throw new NotYetImplementedForExchangeException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String placeMarketOrder(MarketOrder marketOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String placeLimitOrder(LimitOrder limitOrder)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cancelOrder(String orderId) throws ExchangeException,
			NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTrades getTradeHistory(Object... arguments)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTrades getTradeHistory(TradeHistoryParams params)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		throw new NotYetImplementedForExchangeException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TradeHistoryParams createTradeHistoryParams() {
		return null;
	}

}
