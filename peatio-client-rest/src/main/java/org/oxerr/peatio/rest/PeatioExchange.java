package org.oxerr.peatio.rest;

import org.oxerr.peatio.rest.service.polling.PeatioAccountService;
import org.oxerr.peatio.rest.service.polling.PeatioMarketDataService;
import org.oxerr.peatio.rest.service.polling.PeatioTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

/**
 * Peatio exchange.
 */
public class PeatioExchange extends BaseExchange implements Exchange {

	@Override
	public void applySpecification(ExchangeSpecification exchangeSpecification) {
		super.applySpecification(exchangeSpecification);
		this.pollingMarketDataService = new PeatioMarketDataService(exchangeSpecification);
		if (exchangeSpecification.getApiKey() != null) {
			final SynchronizedValueFactory<Long> tonce = new LongTimeNonceFactory();
			this.pollingAccountService = new PeatioAccountService(exchangeSpecification, tonce);
			this.pollingTradeService = new PeatioTradeService(exchangeSpecification, tonce);
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

}
