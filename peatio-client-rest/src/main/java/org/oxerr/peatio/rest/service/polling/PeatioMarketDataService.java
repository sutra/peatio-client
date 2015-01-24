package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;
import java.util.Date;

import org.oxerr.peatio.rest.PeatioAdapters;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Market data service.
 */
public class PeatioMarketDataService extends PeatioMarketDataServiceRaw
		implements PollingMarketDataService {

	/**
	 * @param exchangeSpecification the exchange specification.
	 */
	public PeatioMarketDataService(ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		String market = PeatioAdapters.adaptMarketId(currencyPair);
		return PeatioAdapters.adaptTicker(currencyPair, getTicker(market));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		String market = PeatioAdapters.adaptMarketId(currencyPair);
		Integer limit = args.length > 0 ? ((Number) args[0]).intValue() : null;
		return PeatioAdapters.adaptOrderBook(currencyPair, getDepth(market, limit));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args)
			throws ExchangeException, NotAvailableFromExchangeException,
			NotYetImplementedForExchangeException, IOException {
		String market = PeatioAdapters.adaptMarketId(currencyPair);
		Integer limit = args.length > 0 ? ((Number) args[0]).intValue() : null;
		Long timestamp = args.length > 1 ? ((Date) args[1]).getTime() : null;
		Integer from = args.length > 2 ? ((Number) args[2]).intValue() : null;
		Integer to = args.length > 3 ? ((Number) args[3]).intValue() : null;
		String orderBy = args.length > 4 ? (String) args[4] : null;
		return PeatioAdapters.adaptTrades(getTrades(market, limit, timestamp, from, to, orderBy));
	}

}
