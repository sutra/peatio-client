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

public class OrderTest {

	private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	private static final ObjectMapper mapper = new ObjectMapper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	}

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		Order order = mapper.readValue(getClass().getResource("order.json"), Order.class);
		assertEquals(9170451L, order.getId());
		assertEquals("sell", order.getSide());
		assertEquals(new BigDecimal("2147483647.0"), order.getPrice());
		assertEquals(new BigDecimal("0.0"), order.getAvgPrice());
		assertEquals("btccny", order.getMarket());
		assertEquals("2015-01-11T12:52:31+08:00", fmt.format(order.getCreatedAt()));
		assertEquals(new BigDecimal("0.0001"), order.getVolume());
		assertEquals(new BigDecimal("0.0001"), order.getRemainingVolume());
		assertEquals(new BigDecimal("0.0"), order.getExecutedVolume());
		assertEquals(new Integer(0), order.getTradesCount());
	}

	@Test
	public void testClear() throws JsonParseException, JsonMappingException, IOException {
		Order[] orders = mapper.readValue(getClass().getResource("clear.json"), Order[].class);
		assertEquals(2, orders.length);

		Order order = orders[0];
		assertEquals("wait", order.getState());
	}

}
