package com.lionmint.jwt.auth.poc.controller;

import static com.lionmint.jwt.auth.poc.entities.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lionmint.jwt.auth.poc.entities.AuthorizationResponse;
import com.lionmint.jwt.auth.poc.entities.LoginUser;
import com.lionmint.jwt.auth.poc.entities.UserEntity;
import com.lionmint.jwt.auth.poc.repository.UserRepository;
import com.lionmint.jwt.auth.poc.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/login")
public class LogInController {

	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public List<UserEntity> getUserData(@PathVariable("email") String email) {
		
		List<UserEntity> userList = new LinkedList<UserEntity>();
		final Optional<UserEntity> user = userRepository.findOneByEmailIgnoreCase(email);
		if(user.isPresent())
		{
			userList.add(user.get());
		}
		return userList;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<AuthorizationResponse> logIn(@RequestBody LoginUser loginUser) throws AuthenticationException{
		
		final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
		
		logger.info("Authentication: " + authentication.toString());
		
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        final UserEntity user = userRepository.findOneByEmailIgnoreCase(loginUser.getEmail()).get();
        
        final String token = jwtTokenUtil.generateToken(user);
		
        AuthorizationResponse response = new AuthorizationResponse(false);
		
        if(user.isAuthorized())
		{
			response.setAuthorized(true);
			response.setEmail(user.getEmail());
			
			// User Roles can be later used to replace this.
			response.setAdmin(true);
			response.setAccessToken(token);
			response.setFirstName(user.getFirstName());
			response.setLastName(user.getLastName());
			response.setExpiresIn(ACCESS_TOKEN_VALIDITY_SECONDS);
		}
        return ResponseEntity.ok(response);
	}
}
