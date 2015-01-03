package org.oxerr.peatio.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.oxerr.peatio.rest.dto.Deposit;
import org.oxerr.peatio.rest.dto.Depth;
import org.oxerr.peatio.rest.dto.KWithPendingTrades;
import org.oxerr.peatio.rest.dto.Market;
import org.oxerr.peatio.rest.dto.MarketTicker;
import org.oxerr.peatio.rest.dto.Member;
import org.oxerr.peatio.rest.dto.Order;
import org.oxerr.peatio.rest.dto.OrderBook;
import org.oxerr.peatio.rest.dto.Trade;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/v2/")
public interface Peatio {

	/**
	 * Get all available markets.
	 *
	 * @return all available markets.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("markets.json")
	Market[] getMarkets() throws IOException, PeatioException;

	/**
	 * Get ticker of all markets.
	 *
	 * @return ticker of all markets.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("tickers.json")
	Map<String, MarketTicker> getTickers()
			throws IOException, PeatioException;

	/**
	 * Get ticker of specific market.
	 *
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @return the ticker of the specific market.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("tickers/{market}.json")
	MarketTicker getTicker(
			@PathParam("market") String market)
					throws IOException, PeatioException;

	/**
	 * Get your profile and accounts info.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of you request payload, generated
	 * using your secret key.
	 * @return your profile and accounts info.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("members/me.json")
	Member getMe(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature)
					throws IOException, PeatioException;

	/**
	 * Get your deposits information.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated
	 * using your secret key.
	 * @param currency currency value.
	 * @return deposits information.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("deposits.json")
	Deposit[] getDeposits(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature,
			@QueryParam("currency") String currency)
					throws IOException, PeatioException;

	/**
	 * Get single deposit information.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated
	 * using your secret key.
	 * @param txid the transaction id.
	 * @return single deposit information.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("deposit.json")
	Deposit getDeposit(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature,
			@QueryParam("txid") String txid)
					throws IOException, PeatioException;

	/**
	 * Get your orders.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elaapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated
	 * using your secret key.
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param state filter order by state, default to 'wait'(active orders).
	 * @param limit limite the number of returned orders, default to 10.
	 * @return your orders.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("orders.json")
	Order[] getOrders(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature,
			@QueryParam("market") String market,
			@QueryParam("state") String state,
			@QueryParam("limit") Integer limit)
					throws IOException, PeatioException;

	/**
	 * Create a sell/buy order.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated
	 * using your secret key.
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param side either 'sell' or 'buy'.
	 * @param volume the amount user want to sell/buy. Ano order could be
	 * partially executed, e.g. an order sell 5 btc can be matched with
	 * a buy 3 btc order, left 2 btc to be sold;  in this case the order's
	 * volume would be '5.0', its remaining_volume would be '2.0', its executed
	 * volume is '3.0'.
	 * @param price price for each unit. e.g. if you want to sell/buy 1 btc at
	 * 3000 CNY, the price is '3000.0'.
	 * @param ordType the order type.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@POST
	@Path("orders.json")
	void placeOrder(
			@FormParam("access_key") String accessKey,
			@FormParam("tonce") SynchronizedValueFactory<Long> tonce,
			@FormParam("signature") ParamsDigest signature,
			@FormParam("market") String market,
			@FormParam("side") String side,
			@FormParam("volume") BigDecimal volume,
			@FormParam("price") BigDecimal price,
			@FormParam("ord_type") String ordType)
					throws IOException, PeatioException;

	/**
	 * Create multiple sell/buy orders.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated using
	 * your secret key.
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote
	 * currency code, e.g. 'btccny'. All available markets can be
	 * found at {@link #getMarkets()}.
	 * @param sides either 'sell' or 'buy'.
	 * @param volumes the amount user want to sell/buy. Ano order could be
	 * partially executed, e.g. an order sell 5 btc can be matched with
	 * a buy 3 btc order, left 2 btc to be sold;  in this case the order's
	 * volume would be '5.0', its remaining_volume would be '2.0', its executed
	 * volume is '3.0'.
	 * @param prices price for each unit. e.g. if you want to sell/buy 1 btc at
	 * 3000 CNY, the price is '3000.0'.
	 * @param ordTypes the order types.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@POST
	@Path("orders/multi.json")
	void placeMultiOrders(
			@FormParam("access_key") String accessKey,
			@FormParam("tonce") SynchronizedValueFactory<Long> tonce,
			@FormParam("signature") ParamsDigest signature,
			@FormParam("market") String market,
			@FormParam("orders[side]") String[] sides,
			@FormParam("orders[volume]") BigDecimal[] volumes,
			@FormParam("orders[price]") BigDecimal[] prices,
			@FormParam("orders[ord_type]") String[] ordTypes)
					throws IOException, PeatioException;

	/**
	 * Cancel all my orders.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@POST
	@Path("orders/clear.json")
	void clear(
			@FormParam("access_key") String accessKey,
			@FormParam("tonce") SynchronizedValueFactory<Long> tonce,
			@FormParam("signature") ParamsDigest signature)
					throws IOException, PeatioException;

	/**
	 * Get information of specified order.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, genereated
	 * using your secret key.
	 * @param id unique order id.
	 * @return order information of specified order.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchagne exception.
	 */
	@GET
	@Path("order.json")
	Order getOrder(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature,
			@QueryParam("id") int id)
					throws IOException, PeatioException;

	/**
	 * Cancel an order.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated
	 * using your secret key.
	 * @param id unique order id.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@POST
	@Path("order/delete.json")
	void deleteOrder(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature,
			@QueryParam("id") int id)
					throws IOException, PeatioException;

	/**
	 * Get the order book of specified market.
	 *
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param asksLimit limit the number of returned sell orders. Default to 20.
	 * @param bidsLimit limit the number of returned buy orders. Default to 20.
	 * @return the order book of the specified market.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("order_book.json")
	OrderBook getOrderBook(
			@QueryParam("market") String market,
			@QueryParam("asks_limit") Integer asksLimit,
			@QueryParam("bids_limit") Integer bidsLimit)
					throws IOException, PeatioException;

	/**
	 * Get depth of specified market. Both asks and bids are sorted from
	 * highest price to lowest.
	 *
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param limit limit the number of returned price levels. Default to 300.
	 * @return the depth of the specified market.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("depth.json")
	Depth getDepth(
			@QueryParam("market") String market,
			@QueryParam("limit") Integer limit)
					throws IOException, PeatioException;

	/**
	 * Get recent trades on market, each trade is included only once.
	 * Trades are sorted in reverse creation order.
	 *
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param limit limit the number of returned trades. Default to 50.
	 * @param timestamp an integer represents the seconds elapsed since Unix epoch.
	 * If set, only trades executed before the time will be returned.
	 * @param from trade id.
	 * If set, only trades created after the trade will be returned.
	 * @param to trade id.
	 * If set, only trades created before the trade will be returned.
	 * @param orderBy if set, returned trades will be sorted in specific order,
	 * default to 'desc'.
	 * @return the recent trades on the specified market.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("trades.json")
	Trade[] getTrades(
			@QueryParam("market") String market,
			@QueryParam("limit") Integer limit,
			@QueryParam("timestamp") Long timestamp,
			@QueryParam("from") Integer from,
			@QueryParam("to") Integer to,
			@QueryParam("order_by") String orderBy)
					throws IOException, PeatioException;

	/**
	 * Get your executed trades. Trades are sorted in reverse creation order.
	 *
	 * @param accessKey access key.
	 * @param tonce tonce is an integer represents the milliseconds elapsed
	 * since Unix epoch.
	 * @param signature the signature of your request payload, generated
	 * using your secret key.
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param limit limit the number of returned trades. Default to 50.
	 * @param timestamp an integer represents the seconds elapsed since Unix epoch.
	 * If set, only trades executed before the time will be returned.
	 * @param from trade id.
	 * If set, only trades created after the trade will be returned.
	 * @param to trade id.
	 * If set, only trades created before the trade will be returned.
	 * @param orderBy if set, returned trades will be sorted in specific order,
	 * default to 'desc'.
	 * @return your executed trades.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("trades/my.json")
	Trade[] getMyTrades(
			@QueryParam("access_key") String accessKey,
			@QueryParam("tonce") SynchronizedValueFactory<Long> tonce,
			@QueryParam("signature") ParamsDigest signature,
			@QueryParam("market") String market,
			@QueryParam("limit") Integer limit,
			@QueryParam("timestamp") Long timestamp,
			@QueryParam("from") Integer from,
			@QueryParam("to") Integer to,
			@QueryParam("order_by") String orderBy)
					throws IOException, PeatioException;

	/**
	 * Get OHLC(k line) of specified market.
	 *
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param limit limit the number of returned data points, default to 30.
	 * @param period time period of K line, default to 1.
	 * You can choose from 1, 5, 15, 30, 60, 120, 240, 360, 720, 1440, 4320, 10080.
	 * @param timestamp an integer represents the seconds elapsed since Unix epoch.
	 * If set, only k-line data after that time will be returned.
	 * @return the OHLC of the specified market.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("k.json")
	BigDecimal[][] getK(
			@QueryParam("market") String market,
			@QueryParam("limit") Integer limit,
			@QueryParam("period") Integer period,
			@QueryParam("timestamp") Long timestamp)
					throws IOException, PeatioException;

	/**
	 * Get K data with pending trades, which are the trades not included
	 * in K data yet, because there's delay between trade generated and
	 * processed by K data generator.
	 *
	 * @param market unique market id. It's always in the form of xxxyyy,
	 * where xxx is the base currency code, yyy is the quote currency code,
	 * e.g. 'btccny'. All available markets can be found at {@link #getMarkets()}.
	 * @param tradeId the trade id of the first trade you received.
	 * @param limit limite the number of returned data points, default to 30.
	 * @param period time period of K line, default to 1. You can choose from
	 * 1, 5, 15, 30, 60, 120, 240, 360, 720, 1440, 4320, 10080.
	 * @param timestamp an integer represents the seconds elapsed since Unix epoch.
	 * If set, only k-line data after that time will be returned.
	 * @return K data wit pending trades.
	 * @throws IOException indicates I/O exception.
	 * @throws PeatioException indicates peatio exchange exception.
	 */
	@GET
	@Path("k_with_pending_trades.json")
	KWithPendingTrades getKWithPendingTrades(
			@QueryParam("market") String market,
			@QueryParam("trade_id") Integer tradeId,
			@QueryParam("limit") Integer limit,
			@QueryParam("period") Integer period,
			@QueryParam("timestamp") Long timestamp)
					throws IOException, PeatioException;

}
