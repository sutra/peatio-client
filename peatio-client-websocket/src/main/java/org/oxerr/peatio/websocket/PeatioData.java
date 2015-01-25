package org.oxerr.peatio.websocket;

/**
 * The data that Peatio WebSocket API provided.
 */
public class PeatioData {

	/**
	 * Data type, could be 'challenge', 'success', 'error', 'orderbook', 'trade'.
	 */
	private final String type;

	private final Object object;

	public PeatioData(String type, Object object) {
		this.type = type;
		this.object = object;
	}

	public String getType() {
		return type;
	}

	public Object getObject() {
		return object;
	}

}
