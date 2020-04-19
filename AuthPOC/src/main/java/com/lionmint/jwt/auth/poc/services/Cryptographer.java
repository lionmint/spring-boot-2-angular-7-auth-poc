package com.lionmint.jwt.auth.poc.services;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.lionmint.jwt.auth.poc.controller.SignUpController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Cryptographer {
	
	public static boolean checkPassword(String plainPassword, String hashedPassword)
	{
		if(BCrypt.checkpw(plainPassword, hashedPassword))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public static String hashPassword(String plainPassword)
	{
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}
}
