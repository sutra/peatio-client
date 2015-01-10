package org.oxerr.peatio.rest.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DepositTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		Deposit[] deposits = mapper.readValue(getClass().getResource("deposits.json"), Deposit[].class);
		assertEquals(1, deposits.length);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		Deposit deposit = deposits[0];
		assertEquals("btc", deposit.getCurrency());
		assertEquals(new BigDecimal("1.0"), deposit.getAmount());
		assertEquals(new BigDecimal("0.0"), deposit.getFee());
		assertEquals("39232cf36f8fc5cf4eca45289be32b899910ce8ed6522ab37c40ba5458c51445", deposit.getTxid());
		assertEquals("2015-01-11T02:50:59+08:00", fmt.format(deposit.getCreatedAt()));
		assertEquals(1, deposit.getConfirmations());
		assertNull(deposit.getDoneAt());
		assertEquals("accepted", deposit.getState());
	}

}
