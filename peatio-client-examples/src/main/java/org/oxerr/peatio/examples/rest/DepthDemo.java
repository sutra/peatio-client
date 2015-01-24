package org.oxerr.peatio.examples.rest;

import java.io.IOException;
import java.util.Arrays;

import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.PeatioExchange;
import org.oxerr.peatio.rest.dto.Depth;
import org.oxerr.peatio.rest.dto.OrderBook;
import org.oxerr.peatio.rest.service.polling.PeatioMarketDataServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class DepthDemo {

	private static final Logger log = LoggerFactory.getLogger(DepthDemo.class);

	public static void main(String[] args) throws PeatioException, IOException {
		ExchangeSpecification spec = new ExchangeSpecification(PeatioExchange.class);
		spec.setSslUri("https://yunbi.com");
		Exchange peatio = ExchangeFactory.INSTANCE.createExchange(spec);
		PollingMarketDataService mdService = peatio.getPollingMarketDataService();
		PeatioMarketDataServiceRaw mdServiceRaw = (PeatioMarketDataServiceRaw) mdService;
		String market = "btccny";

		// Generic service
		com.xeiam.xchange.dto.marketdata.OrderBook genericOrderBook = mdService.getOrderBook(CurrencyPair.BTC_CNY);
		log.info("---- generic order book ----");
		log.info("order book timestamp: {}", genericOrderBook.getTimeStamp());
		genericOrderBook.getBids().forEach(bid -> log.info("Bid {}@{}", bid.getTradableAmount(), bid.getLimitPrice()));
		genericOrderBook.getAsks().forEach(ask -> log.info("Ask {}@{}", ask.getTradableAmount(), ask.getLimitPrice()));

		// Raw service
		log.info("---- raw depth ----");
		Depth rawDepth = mdServiceRaw.getDepth(market, null);
		log.info("depth timestamp: {}", rawDepth.getTimestamp());
		Arrays.stream(rawDepth.getBids()).forEach(bid -> log.info("Bid {}@{}", bid[1], bid[0]));
		Arrays.stream(rawDepth.getAsks()).forEach(ask -> log.info("Ask {}@{}", ask[1], ask[0]));

		log.info("---- raw order book ----");
		OrderBook rawOrderBook = mdServiceRaw.getOrderBook(market, null, null);
		Arrays.stream(rawOrderBook.getBids()).forEach(bid -> log.info("Bid {} {} {}@{}", bid.getId(), bid.getCreatedAt(), bid.getRemainingVolume(), bid.getPrice()));
		Arrays.stream(rawOrderBook.getBids()).forEach(ask -> log.info("Ask {} {} {}@{}", ask.getId(), ask.getCreatedAt(), ask.getRemainingVolume(), ask.getPrice()));
	}

}
