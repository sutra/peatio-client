package org.oxerr.peatio.websocket.event;

import java.util.EventListener;

import javax.websocket.Session;

public interface PeatioDataListener extends EventListener {

	void onMessage(Session session, Object data);

}
