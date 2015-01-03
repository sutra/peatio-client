package org.oxerr.peatio.rest.service.polling;

import org.oxerr.peatio.rest.service.PeatioDigest;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.Assert;

public class PeatioBasePrivatePollingService extends PeatioBasePollingService
		implements BasePollingService {

	protected final SynchronizedValueFactory<Long> tonce;
	protected final String accessKey;
	protected final PeatioDigest signature;

	protected PeatioBasePrivatePollingService(
			ExchangeSpecification exchangeSpecification,
			SynchronizedValueFactory<Long> tonce) {
		super(exchangeSpecification);
		this.tonce = tonce;
		this.accessKey = exchangeSpecification.getApiKey();
		String secretKey = exchangeSpecification.getSecretKey();

		Assert.notNull(accessKey, "Exchange specification access key cannot be null");
		Assert.notNull(secretKey, "Exchange specification secret key cannot be null.");

		signature = new PeatioDigest(secretKey);
	}

}
