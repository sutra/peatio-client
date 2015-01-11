package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.dto.Order;
import org.oxerr.peatio.rest.dto.Trade;

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

	public Order[] getOrders(String market, String state, Integer limit,
			Integer page, String orderBy) throws PeatioException, IOException {
		return peatio.getOrders(accessKey, tonce, signature, market, state,
				limit, page, orderBy);
	}

	public Order placeOrder(String market, String side, BigDecimal volume,
			BigDecimal price, String ordType) throws PeatioException,
			IOException {
		return peatio.placeOrder(accessKey, tonce, signature, market, side, volume,
				price, ordType);
	}

	public Order[] clearOrders() throws PeatioException, IOException {
		return peatio.clear(accessKey, tonce, signature);
	}

	public Order getOrder(long id) throws PeatioException, IOException {
		return peatio.getOrder(accessKey, tonce, signature, id);
	}

	public Order deleteOrder(long id) throws PeatioException, IOException {
		return peatio.deleteOrder(accessKey, tonce, signature, id);
	}

	public Trade[] getMyTrades(String market, Integer limit, Long timestamp,
			Long from, Long to, String orderBy) throws PeatioException,
			IOException {
		return peatio.getMyTrades(accessKey, tonce, signature, market, limit,
				timestamp, from, to, orderBy);
	}

}
