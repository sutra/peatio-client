package org.oxerr.peatio.examples.rest;

import java.io.IOException;
import java.util.Arrays;

import org.oxerr.peatio.rest.PeatioExchange;
import org.oxerr.peatio.rest.dto.Deposit;
import org.oxerr.peatio.rest.dto.Member;
import org.oxerr.peatio.rest.service.polling.PeatioAccountServiceRaw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class PeatioAccountServiceExample {

	private static final Logger log = LoggerFactory.getLogger(PeatioAccountServiceExample.class);

	public static void main(String[] args) throws IOException {
		String accessKey = args[0], secretKey = args[1];

		ExchangeSpecification spec = new ExchangeSpecification(PeatioExchange.class);
		spec.setSslUri("https://yunbi.com");
		spec.setApiKey(accessKey);
		spec.setSecretKey(secretKey);

		Exchange peatio = ExchangeFactory.INSTANCE.createExchange(spec);
		PollingAccountService accountService = peatio.getPollingAccountService();
		PeatioAccountServiceRaw accountServiceRaw = (PeatioAccountServiceRaw) accountService;

		// Get your profile and accounts info.
		Member member = accountServiceRaw.getMe();
		log.info("me: {}", member);

		AccountInfo accountInfo = accountService.getAccountInfo();
		log.info("account info: {}", accountInfo);

		// Get your deposits information.
		Deposit[] deposits = accountServiceRaw.getDeposits(null);
		log.info("deposits: {}", Arrays.toString(deposits));

		for (Deposit deposit : deposits) {
			// Get single deposit information.
			Deposit d = accountServiceRaw.getDeposit(deposit.getTxid());
			log.info("deposit: {}", d);
		}
	}

}
