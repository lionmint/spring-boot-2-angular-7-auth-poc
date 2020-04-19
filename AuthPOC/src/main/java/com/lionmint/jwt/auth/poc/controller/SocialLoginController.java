package com.lionmint.jwt.auth.poc.controller;

import static com.lionmint.jwt.auth.poc.entities.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lionmint.jwt.auth.poc.entities.AuthorizationResponse;
import com.lionmint.jwt.auth.poc.entities.UserEntity;
import com.lionmint.jwt.auth.poc.repository.UserRepository;
import com.lionmint.jwt.auth.poc.security.JwtTokenUtil;
import com.lionmint.jwt.auth.poc.services.Cryptographer;
import com.lionmint.jwt.auth.poc.services.MailService;
import com.lionmint.jwt.auth.poc.services.RandomString;

import io.jsonwebtoken.Claims;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@RequestMapping("/api/social")
public class SocialLoginController {
	
	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Value(value = "${sendgrid.api.key}")
	private String sendgridKey;

	@Value(value = "${xframe.allowed.origins.deployed_dev}")
	private String angular_address;

	@Value(value = "${google.clientId}")
	private String googleClientId;

	@Value(value = "${google.publicKeyModulus}")
	private String googlePublicKeyModulus;
	
	@Value(value = "${google.publicKeyExponent}")
	private String googlePublicKeyExponent;
	
	@Value(value = "${facebook.appAccessToken}")
	private String facebookAccessToken;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public boolean userIsAvailable(String email) {
		
		final Optional<UserEntity> user = userRepository.findOneByEmailIgnoreCase(email);
		if(!user.isPresent())
		{
			return true;
		}

		return false;
	}

	public ResponseEntity<AuthorizationResponse> socialLogin(UserEntity socialUser) throws AuthenticationException{

        AuthorizationResponse response = new AuthorizationResponse();
		
		final Optional<UserEntity> checkUser = userRepository.findOneByEmailIgnoreCase(socialUser.getEmail());
		// generate Password
		RandomString random = new RandomString();
		final String newPassword = random.nextString();
		final String hashedPassword = Cryptographer.hashPassword(newPassword);
		if(!checkUser.isPresent())
		{	
			socialUser.setPassword(hashedPassword);
			
			socialUser.setAuthorized(true);
			response.setNewUser(true);
			userRepository.save(socialUser);

			// send per mail
			final String jwts = Cryptographer.encryptorHS256(this.googleClientId, socialUser.getEmail());
		    logger.info("Email: " + jwts);
		    
			MailService.SendPasswordMail(newPassword, socialUser.getEmail(), sendgridKey, angular_address, jwts);
		}
		
		UserEntity user = userRepository.findOneByEmailIgnoreCase(socialUser.getEmail()).get();
	
		final String oldPassword = user.getPassword();
		
		if(!user.isGoogleAuthroized() && socialUser.isGoogleAuthroized())
		{
			user.setGoogleAuthroized(true);
		}
		
		if(!user.isFacebookAuthorized() && socialUser.isFacebookAuthorized())
		{
			user.setFacebookAuthorized(true);
		}
		user.setPassword(hashedPassword);
		
		userRepository.save(user);
		
    	final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		user.getEmail(),
                		newPassword
                )
        );
		
    	user.setPassword(oldPassword);
    	userRepository.save(user);
    	
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        final String token = jwtTokenUtil.generateToken(user);
        
		response.setEmail(user.getEmail());
		
		// User Roles can be later used to replace this.
		response.setAdmin(true);
		response.setAuthorized(true);
		response.setAccessToken(token);
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setExpiresIn(ACCESS_TOKEN_VALIDITY_SECONDS);
        
		logger.info(response.getAccessToken());
		
        return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public ResponseEntity<AuthorizationResponse> loginGoogle(@RequestBody String idToken) 
	{
		// Verify the id-token.
		final String token = idToken.substring(1, idToken.length()-1);
		logger.info("IDTOKEN: " + token);
		
        boolean signitureIsCorrect = false;
        boolean audIsCorrect = false;
        boolean issIsCorrect = false;
        boolean expired = false;
        
        final Claims claims = Cryptographer.decryptorRSA(googlePublicKeyModulus, googlePublicKeyExponent, token);
        signitureIsCorrect = true;

	    logger.info("Tokencall: " + claims.toString());	
	    final String iss =  claims.get("iss").toString();
	    final String aud =  claims.get("aud").toString();
	    final long exp = Long.parseLong(claims.get("exp").toString());
	   
	    Date now = new Date();
	    final long time = now.getTime() / 1000;
	    if(exp > time)
	    	expired = true;

	    if(aud.equals(this.googleClientId))
	    	audIsCorrect = true;
	    
	    if(iss.equals("accounts.google.com") || iss.equals("https://accounts.google.com"))
	    	issIsCorrect = true;
        
	    logger.info("ISS: " + issIsCorrect);
	    logger.info("AUD: " + audIsCorrect);
	    logger.info("EXP: " + expired);
	    
		// check user existence 
        if(signitureIsCorrect && audIsCorrect && issIsCorrect && expired)
        {
        	logger.info("Social User Authorized");
        	UserEntity user = new UserEntity();
        	user.setEmail(claims.get("email").toString());
        	user.setFirstName(claims.get("given_name").toString());
        	user.setLastName(claims.get("family_name").toString());
        	user.setGoogleAuthroized(true);

        	//fill with Data
        	return this.socialLogin(user);
        }else
        {
        	return ResponseEntity.ok(new AuthorizationResponse(false));
        }
	}

	
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public ResponseEntity<AuthorizationResponse> loginFacebook(@RequestBody String authToken) 
	{
		// Verify the id-token.
		final String token = authToken.substring(1, authToken.length()-1);
		logger.info("Token: " + token);
		// Verify the id-token.
           
		// check user existence 
		if(this.validateAccessToken(token))
        {
        	UserEntity user = new UserEntity();
        	
        	final User facebookUser = this.getUser(token);
        	logger.info("Facebook User Email: " + facebookUser.getEmail());
        	
        	user.setEmail(facebookUser.getEmail());
        	user.setFirstName(facebookUser.getFirstName());
        	user.setLastName(facebookUser.getLastName());
        	user.setFacebookAuthorized(true);
        	//fill with Data
        	
        	return this.socialLogin(user);
        }else
        {
        	return ResponseEntity.ok(new AuthorizationResponse(false));
        }
	}
	
	private boolean validateAccessToken(String accessToken) {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://graph.facebook.com/debug_token?input_token=" + accessToken +"&access_token=" + this.facebookAccessToken)
		  .get()
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			logger.info("Access Token is valid is " + response.isSuccessful());
			return response.isSuccessful();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private User getUser(String fbAccessToken) {
        Facebook facebook = new FacebookTemplate(fbAccessToken);
        final String[] fields = { "id", "email", "first_name", "last_name" };
        return facebook.fetchObject("me", User.class, fields);
    }
	
	
}

