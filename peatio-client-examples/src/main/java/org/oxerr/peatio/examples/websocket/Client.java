package org.oxerr.peatio.examples.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.oxerr.peatio.websocket.PeatioClientEndpoint;
import org.oxerr.peatio.websocket.event.PeatioDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	private static final Logger log = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) throws DeploymentException,
			IOException, URISyntaxException, InterruptedException {
		String accessKey = args[0], secretKey = args[1];

		PeatioClientEndpoint endpoint = new PeatioClientEndpoint(accessKey, secretKey);
		PeatioDataListener listener = new PeatioDataListener() {

			@Override
			public void onMessage(Session session, Object data) {
				log.info("{}", data);
			}
		};

		endpoint.addDataListener("trade", listener);
		endpoint.addDataListener("orderbook", listener);

		WebSocketContainer container = ContainerProvider
				.getWebSocketContainer();
		container.connectToServer(endpoint, new URI(
				"wss://yunbi.com:8080"));

		TimeUnit.MINUTES.sleep(5);
	}

}
