package org.oxerr.peatio.rest.service.polling;

import org.oxerr.peatio.rest.service.PeatioDigest;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

/**
 * Base private service. Provides {@link SynchronizedValueFactory} and {@link PeatioDigest} for subclasses.
 */
public class PeatioBasePrivatePollingService extends PeatioBasePollingService
		implements BasePollingService {

	protected final SynchronizedValueFactory<Long> tonce;
	protected final String accessKey;
	protected final PeatioDigest signature;

	protected PeatioBasePrivatePollingService(Exchange exchange) {
		super(exchange);
		this.tonce = exchange.getNonceFactory();
		ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
		this.accessKey = exchangeSpecification.getApiKey();
		String secretKey = exchangeSpecification.getSecretKey();

		Assert.notNull(accessKey, "Exchange specification access key cannot be null");
		Assert.notNull(secretKey, "Exchange specification secret key cannot be null.");

		signature = new PeatioDigest(secretKey);
	}

}
