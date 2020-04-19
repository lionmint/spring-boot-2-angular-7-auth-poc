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
	
	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

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
	

	public static String encryptorHS256(String secret, String inputString)
	{
		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		final Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		final String jwts = Jwts.builder().setSubject(inputString).signWith(signatureAlgorithm, signingKey).compact();
		return jwts;
	}
	
	public static String decryptorHS256(String secret, String encryptedString)
	{
		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		final Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		final String decryptedString = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(encryptedString).getBody().getSubject();
		return decryptedString;
	}
	
	public static byte[] base64UrlDecodeToBytes(String input)
    {
        Base64 decoder = new Base64(-1, null, true);
        final byte[] decodedBytes = decoder.decode(input);

        return decodedBytes;
    }
	
	private static PublicKey CreatePublicKey(String publicKeyModulus, String publicKeyExponent)
	{
		final byte[] nb = base64UrlDecodeToBytes(publicKeyModulus);
		final byte[] eb = base64UrlDecodeToBytes(publicKeyExponent);
		final BigInteger n = new BigInteger(1, nb);
		final BigInteger e = new BigInteger(1, eb);

		final RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);
        try
        {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(rsaPublicKeySpec);

            logger.info("PublicKey created");
            return publicKey;
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Cant create public key", ex);
        }
	}

    public static Claims decryptorRSA(String publicKeyModulus, String publicKeyExponent, String encryptedString)
    {
    	final PublicKey key = CreatePublicKey(publicKeyModulus, publicKeyExponent);
    	final Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(encryptedString).getBody();
        return claims;
    }
}
