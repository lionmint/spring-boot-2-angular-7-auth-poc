package com.lionmint.jwt.auth.poc.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lionmint.jwt.auth.poc.entities.UserEntity;
import com.lionmint.jwt.auth.poc.repository.UserRepository;
import com.lionmint.jwt.auth.poc.services.Cryptographer;
import com.lionmint.jwt.auth.poc.services.MailService;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Value(value = "${sendgrid.api.key}")
	private String sendgridKey;

	@Value(value = "${xframe.allowed.origins.deployed_dev}")
	private String angular_address;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public boolean userIsAvailable(@PathVariable("email") String email) {
		
		final Optional<UserEntity> user = userRepository.findOneByEmailIgnoreCase(email);
		if(!user.isPresent())
		{
			return true;
		}

		return false;
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public boolean signUp(@RequestBody UserEntity user) {
		
		if(this.userIsAvailable(user.getEmail()))
		{
			user.setPassword(Cryptographer.hashPassword(user.getPassword()));
			user.setAuthorized(false);

			userRepository.save(user);

			user = userRepository.findOneByEmailIgnoreCase(user.getEmail()).get();
		    
			// Send authroization email with endpoint link userIsAuthorized using user.getId
			MailService.SendRegistrationConfirmationMail(Long.toString(user.getId()), user.getEmail(), sendgridKey, angular_address);
			return true;
		}else
		{
			return false;
		}
	}
	
	@RequestMapping(value = "/authorize/{userId}", method = RequestMethod.GET)
	public boolean userIsAuthorized(@PathVariable("userId") String userId) {
	    
		UserEntity user = userRepository.findById(Long.parseLong(userId)).get();
		user.setAuthorized(true);
		userRepository.save(user);
		return true;
	}
}
