package org.oxerr.peatio.rest.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import com.xeiam.xchange.service.BaseParamsDigest;
import com.xeiam.xchange.utils.DigestUtils;

/**
 * {@link ParamsDigest} implementation for Peatio.
 */
public class PeatioDigest extends BaseParamsDigest {

	private final Logger log = LoggerFactory.getLogger(PeatioDigest.class);
	private final Field invocationUrlField;

	public PeatioDigest(String secretKey)
			throws IllegalArgumentException {
		super(secretKey, HMAC_SHA_256);

		try {
			invocationUrlField = RestInvocation.class.getDeclaredField("invocationUrl");
			invocationUrlField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String digestParams(RestInvocation restInvocation) {
		final String verb = restInvocation.getHttpMethod();
		final String uri = restInvocation.getPath();
		final String query = getSortedParams(restInvocation);

		final String payload = String.join("|", verb, uri, query);
		log.debug("payload: {}", payload);

		String signature = sign(payload);

		// Seems rescu does not support ParamsDigest in QueryParam.
		// hack to replace the signature in the invocation URL.
		String invocationUrl = restInvocation.getInvocationUrl();
		log.debug("old invocationUrl: {}", invocationUrl);
		String newInvocationUrl = UriBuilder.fromUri(invocationUrl).replaceQueryParam("signature", signature).build().toString();
		try {
			invocationUrlField.set(restInvocation, newInvocationUrl);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		log.debug("new invocationUrl: {}", restInvocation.getInvocationUrl());

		return signature;
	}

	public String sign(String payload) {
		Mac mac = getMac();
		byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
		String signature = DigestUtils.bytesToHex(hash).toLowerCase();
		return signature;
	}

	private String getSortedParams(RestInvocation restInvocation) {
		final Map<Class<? extends Annotation>, Params> paramsMap = restInvocation.getParamsMap();

		final Params queryParams = paramsMap.get(QueryParam.class);
		final Params formParams = paramsMap.get(FormParam.class);

		final SortedMap<String, String> sortedQueryParams = new TreeMap<>();
		sortedQueryParams.putAll(queryParams.asHttpHeaders());
		sortedQueryParams.putAll(formParams.asHttpHeaders());

		final Params sortedParams = Params.of();
		for (Map.Entry<String, String> param : sortedQueryParams.entrySet()) {
			if (!param.getKey().equals("signature")) {
				sortedParams.add(param.getKey(), param.getValue());
			}
		}

		return sortedParams.asQueryString();
	}

}
