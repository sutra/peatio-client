package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.dto.Depth;
import org.oxerr.peatio.rest.dto.KWithPendingTrades;
import org.oxerr.peatio.rest.dto.MarketTicker;
import org.oxerr.peatio.rest.dto.OrderBook;
import org.oxerr.peatio.rest.dto.Trade;

import com.xeiam.xchange.ExchangeSpecification;

/**
 * Market data raw service.
 */
public class PeatioMarketDataServiceRaw extends PeatioBasePollingService {

	public PeatioMarketDataServiceRaw(
			ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
	}

	public Map<String, MarketTicker> getTickers() throws PeatioException,
			IOException {
		return peatio.getTickers();
	}

	public MarketTicker getTicker(String market) throws PeatioException,
			IOException {
		return peatio.getTicker(market);
	}

	public OrderBook getOrderBook(String market, Integer asksLimit,
			Integer bidsLimit) throws PeatioException, IOException {
		return peatio.getOrderBook(market, asksLimit, bidsLimit);
	}

	public Depth getDepth(String market, Integer limit) throws PeatioException,
			IOException {
		return peatio.getDepth(market, limit);
	}

	public Trade[] getTrades(String market, Integer limit, Long timestamp,
			Integer from, Integer to, String orderBy) throws PeatioException,
			IOException {
		return peatio.getTrades(market, limit, timestamp, from, to, orderBy);
	}

	public BigDecimal[][] getK(String market, Integer limit, Integer period,
			Long timestamp) throws PeatioException, IOException {
		return peatio.getK(market, limit, period, timestamp);
	}

	public KWithPendingTrades getKWithPendingTrades(String market,
			Integer tradeId, Integer limit, Integer period, Long timestamp)
			throws PeatioException, IOException {
		return peatio.getKWithPendingTrades(market, tradeId, limit, period,
				timestamp);
	}

}
