package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;
import java.util.List;

import org.oxerr.peatio.rest.Peatio;
import org.oxerr.peatio.rest.PeatioAdapters;
import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.dto.Market;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * Base service. Provides {@link Peatio} instance for subclasses.
 */
public class PeatioBasePollingService extends BaseExchangeService implements
		BasePollingService {

	protected final Peatio peatio;

	protected PeatioBasePollingService(Exchange exchange) {
		super(exchange);
		String baseUrl = exchange.getExchangeSpecification().getSslUri();
		Assert.notNull(baseUrl, "Exchange specification URI cannot be null");
		peatio = RestProxyFactory.createProxy(Peatio.class, baseUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CurrencyPair> getExchangeSymbols() throws PeatioException,
			IOException {
		return PeatioAdapters.adaptCurrencyPairs(getMarkets());
	}

	public Market[] getMarkets() throws PeatioException, IOException {
		return peatio.getMarkets();
	}

}
