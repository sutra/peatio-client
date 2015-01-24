package org.oxerr.peatio.examples.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.oxerr.peatio.rest.PeatioAdapters;
import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.PeatioExchange;
import org.oxerr.peatio.rest.dto.Depth;
import org.oxerr.peatio.rest.dto.KWithPendingTrades;
import org.oxerr.peatio.rest.dto.Market;
import org.oxerr.peatio.rest.dto.MarketTicker;
import org.oxerr.peatio.rest.dto.OrderBook;
import org.oxerr.peatio.rest.dto.Trade;
import org.oxerr.peatio.rest.service.polling.PeatioMarketDataServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class PeatioMarketServiceExample {

	private static final Logger log = LoggerFactory.getLogger(PeatioMarketServiceExample.class);

	public static void main(String[] args) throws IOException {
		ExchangeSpecification spec = new ExchangeSpecification(PeatioExchange.class);
		spec.setSslUri("https://demo.peatio.com");
		Exchange peatio = ExchangeFactory.INSTANCE.createExchange(spec);
		PollingMarketDataService mdService = peatio.getPollingMarketDataService();
		PeatioMarketDataServiceRaw mdServiceRaw = (PeatioMarketDataServiceRaw) mdService;

		// PeatioException
		try {
			mdServiceRaw.getTicker("invalid market code");
		} catch (PeatioException e) {
			log.info("{}: {}", e.getCode(), e.getMessage());
		}

		// Get all available markets.
		Market[] markets = mdServiceRaw.getMarkets();
		Collection<CurrencyPair> exchangeSymbols = mdService.getExchangeSymbols();
		log.info("exchange symbols: {}", exchangeSymbols);

		// Get ticker of all markets.
		Map<String, MarketTicker> tickers = mdServiceRaw.getTickers();
		for (Map.Entry<String, MarketTicker> ticker : tickers.entrySet()) {
			log.info("{}: {}", ticker.getKey(), ticker.getValue());
		}

		for (Market market : markets) {
			CurrencyPair currencyPair = PeatioAdapters.adaptCurrencyPair(market);
			log.info("Market: {}, currency pair: {}", market, currencyPair);

			// Get ticker of specific market.
			MarketTicker marketTicker = mdServiceRaw.getTicker(market.getId());
			log.info("{}: {}", market, marketTicker);

			Ticker ticker = mdService.getTicker(currencyPair);
			log.info("{}: {}", market, ticker);

			// Get the order book of specified market.
			OrderBook orderBook = mdServiceRaw.getOrderBook(market.getId(), 1, null);
			log.info("asks size: {}, bids size: {}",
					orderBook.getAsks().length, orderBook.getBids().length);
			log.info("{}: {}", market, orderBook);

			log.info("{}: {}", market, mdService.getOrderBook(currencyPair));

			// Get the depth of specified market. Both asks and bids are sorted from highest price to lowest.
			Depth depth = mdServiceRaw.getDepth(market.getId(), null);
			log.info("{}: {}", market, depth);

			// Get recent trades on market.
			Trade[] trades = mdServiceRaw.getTrades(market.getId(), 2, null, null, null, null);
			log.info("{}: {}", market, trades);

			log.info("{}: {}", market, mdService.getTrades(currencyPair));

			// Get OHLC.
			BigDecimal[][] k = mdServiceRaw.getK(market.getId(), null, null, null);
			log.info("{}: {}", market, k);

			// Get K data with pending trades.
			KWithPendingTrades kWithPendingTrades = mdServiceRaw.getKWithPendingTrades(market.getId(), 1, null, null, null);
			log.info("{}: {}, pending trades: {}", market, kWithPendingTrades.getK(), kWithPendingTrades.getTrades());
		}

	}

}
