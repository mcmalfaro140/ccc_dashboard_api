package com.ccc.api.util;

import javax.crypto.SecretKey;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class GenerateJwtSecret {
	public static void main(String args[]) {
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}
}