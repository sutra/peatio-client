package org.oxerr.peatio.examples.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.PeatioExchange;
import org.oxerr.peatio.rest.dto.Order;
import org.oxerr.peatio.rest.dto.Trade;
import org.oxerr.peatio.rest.service.polling.PeatioTradeServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class PeatioTradeServiceExample {

	private static final Logger log = LoggerFactory
			.getLogger(PeatioTradeServiceExample.class);

	public static void main(String[] args) throws PeatioException, IOException {
		String accessKey = args[0], secretKey = args[1];
		boolean demoAsk = args.length > 2 ? Boolean.parseBoolean(args[2]) : false;
		boolean demoBid = args.length > 3 ? Boolean.parseBoolean(args[3]) : false;

		// Cancel all my orders
		boolean clear = args.length > 4 ? Boolean.parseBoolean(args[4]) : false;

		Long cancelOrderId = args.length > 5 ? new Long(args[5]) : null;

		ExchangeSpecification spec = new ExchangeSpecification(PeatioExchange.class);
		spec.setSslUri("https://yunbi.com");
		spec.setApiKey(accessKey);
		spec.setSecretKey(secretKey);

		Exchange peatio = ExchangeFactory.INSTANCE.createExchange(spec);
		PollingTradeService tradeService = peatio.getPollingTradeService();
		PeatioTradeServiceRaw tradeServiceRaw = (PeatioTradeServiceRaw) tradeService;

		final String market = "btccny";

		// Get orders
		Order[] orders = tradeServiceRaw.getOrders(market, null, null, null, null);
		log.info("open order count: {}, orders: {}", orders.length, Arrays.toString(orders));
		for (Order order: orders) {
			log.info("order: {}", tradeServiceRaw.getOrder(order.getId()));
		}

		final BigDecimal minVolumePerOrder = new BigDecimal("0.0001");
		final BigDecimal minPrice = new BigDecimal("0.01");

		if (demoAsk) {
			// Place ask order
			Order order = tradeServiceRaw.placeOrder(market, "sell",
					minVolumePerOrder,
					new BigDecimal(Integer.MAX_VALUE), "limit");
			log.info("ask order: {}", order);
		}
		if (demoBid) {
			// Place bid order
			Order order = tradeServiceRaw.placeOrder(market, "buy",
					minVolumePerOrder,
					minPrice, "limit");
			log.info("bid order: {}", order);
		}

		if (clear) {
			Order[] cancelledOrders = tradeServiceRaw.clearOrders();
			log.info("cancelled order: {}", Arrays.toString(cancelledOrders));
		}

		if (cancelOrderId != null) {
			Order deletedOrder = tradeServiceRaw.deleteOrder(cancelOrderId);
			log.info("deleted order: {}", deletedOrder);
		}

		// Get my trades.
		Trade[] myTrades = tradeServiceRaw.getMyTrades(market, null, null, null, null, null);
		log.info("my trades: {}", Arrays.toString(myTrades));
	}

}
