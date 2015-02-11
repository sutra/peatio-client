package org.oxerr.peatio.rest;

import org.oxerr.peatio.rest.service.polling.PeatioAccountService;
import org.oxerr.peatio.rest.service.polling.PeatioMarketDataService;
import org.oxerr.peatio.rest.service.polling.PeatioTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

/**
 * Peatio exchange.
 */
public class PeatioExchange extends BaseExchange implements Exchange {

	private SynchronizedValueFactory<Long> tonce = new CurrentTimeNonceFactory();

	@Override
	public void applySpecification(ExchangeSpecification exchangeSpecification) {
		super.applySpecification(exchangeSpecification);
		this.pollingMarketDataService = new PeatioMarketDataService(this);
		if (exchangeSpecification.getApiKey() != null) {
			this.pollingAccountService = new PeatioAccountService(this);
			this.pollingTradeService = new PeatioTradeService(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExchangeSpecification getDefaultExchangeSpecification() {
		ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
		return exchangeSpecification;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SynchronizedValueFactory<Long> getNonceFactory() {
		return tonce;
	}

}
