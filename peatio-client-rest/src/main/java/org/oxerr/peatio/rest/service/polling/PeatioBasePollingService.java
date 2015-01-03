package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.oxerr.peatio.rest.Peatio;
import org.oxerr.peatio.rest.PeatioAdapters;
import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.dto.Market;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

public class PeatioBasePollingService extends BaseExchangeService implements
		BasePollingService {

	protected final Peatio peatio;

	protected PeatioBasePollingService(
			ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
		String baseUrl = exchangeSpecification.getSslUri();
		Assert.notNull(baseUrl, "Exchange specification URI cannot be null");
		peatio = RestProxyFactory.createProxy(Peatio.class, baseUrl);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
		return PeatioAdapters.adaptCurrencyPairs(getMarkets());
	}

	public Market[] getMarkets() throws PeatioException, IOException {
		return peatio.getMarkets();
	}

}
