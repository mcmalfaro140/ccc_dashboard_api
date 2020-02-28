package com.ccc.api.controller;

import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class GenerateJwtSecret {

  public static void main(String args[]) {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    System.out.println(Encoders.BASE64URL.encode(key.getEncoded()));
  }
}