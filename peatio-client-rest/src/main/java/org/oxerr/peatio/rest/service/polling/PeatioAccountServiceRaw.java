package org.oxerr.peatio.rest.service.polling;

import java.io.IOException;

import org.oxerr.peatio.rest.PeatioException;
import org.oxerr.peatio.rest.dto.Deposit;
import org.oxerr.peatio.rest.dto.Member;

import com.xeiam.xchange.Exchange;

/**
 * Account raw service.
 */
public class PeatioAccountServiceRaw extends PeatioBasePrivatePollingService {

	protected PeatioAccountServiceRaw(Exchange exchange) {
		super(exchange);
	}

	public Member getMe() throws PeatioException, IOException {
		return peatio.getMe(accessKey, tonce, signature);
	}

	public Deposit[] getDeposits(String currency) throws PeatioException,
			IOException {
		return peatio.getDeposits(accessKey, tonce, signature, currency);
	}

	public Deposit getDeposit(String txid) throws PeatioException, IOException {
		return peatio.getDeposit(accessKey, tonce, signature, txid);
	}

}
