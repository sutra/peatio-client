package org.oxerr.peatio.rest.service.polling;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;

/**
 * Trade raw service.
 */
public class PeatioTradeServiceRaw extends PeatioBasePrivatePollingService {

	protected PeatioTradeServiceRaw(
			ExchangeSpecification exchangeSpecification,
			SynchronizedValueFactory<Long> tonce) {
		super(exchangeSpecification, tonce);
	}

}
