package org.oxerr.peatio.websocket;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import javax.websocket.EncodeException;

import org.junit.Before;
import org.junit.Test;
import org.oxerr.peatio.websocket.dto.Auth;

public class PeatioEncoderTest {

	private AuthEncoder encoder;

	@Before
	public void setUp() {
		encoder = new AuthEncoder();
	}

	@Test
	public void testEncode() throws EncodeException, IOException {
		Auth auth = new Auth("your_access_key", "the_signature");
		StringWriter writer = new StringWriter();
		encoder.encode(auth, writer);
		writer.close();
		assertEquals(
				"{\"auth\":{\"access_key\":\"your_access_key\",\"answer\":\"the_signature\"}}",
				writer.toString());
	}

}
