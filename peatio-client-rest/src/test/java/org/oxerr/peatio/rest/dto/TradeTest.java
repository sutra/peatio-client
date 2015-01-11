package org.oxerr.peatio.rest.dto;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TradeTest {

	private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	private static final ObjectMapper mapper = new ObjectMapper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	}

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		Trade[] trades = mapper.readValue(getClass().getResource("myTrades.json"), Trade[].class);
		assertEquals(2, trades.length);

		Trade trade = trades[0];
		assertEquals(1618495L, trade.getId());
		assertEquals(new BigDecimal("1678.06"), trade.getPrice());
		assertEquals(new BigDecimal("0.01"), trade.getVolume());
		assertEquals(new BigDecimal("16.7806"), trade.getFunds());
		assertEquals("btccny", trade.getMarket());
		assertEquals("2015-01-11T17:44:08+08:00", fmt.format(trade.getCreatedAt()));
		assertEquals("bid", trade.getSide());
		assertEquals(new Long(9181222L), trade.getOrderId());
	}

}
