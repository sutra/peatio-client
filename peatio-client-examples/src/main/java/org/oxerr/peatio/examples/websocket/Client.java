package org.oxerr.peatio.examples.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

import org.oxerr.peatio.websocket.PeatioClientEndpoint;

public class Client {
	public static void main(String[] args) throws DeploymentException,
			IOException, URISyntaxException {
		String accessKey = args[0], secretKey = args[1];

		PeatioClientEndpoint endpoint = new PeatioClientEndpoint(accessKey, secretKey);
		WebSocketContainer container = ContainerProvider
				.getWebSocketContainer();
		container.connectToServer(endpoint, new URI(
				"wss://yunbi.com:8080"));
		try {
			Thread.sleep(100000000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
