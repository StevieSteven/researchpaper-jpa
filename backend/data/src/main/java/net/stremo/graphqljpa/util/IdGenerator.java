package net.stremo.graphqljpa.util;

import java.security.SecureRandom;
import java.util.Base64;

public class IdGenerator {

	public static String newId() {
		SecureRandom random = new SecureRandom();

		byte[] randomBytes = new byte[12];

		random.nextBytes(randomBytes);

		return Base64.getEncoder().encodeToString(randomBytes).replace("/", "_");
	}
}
