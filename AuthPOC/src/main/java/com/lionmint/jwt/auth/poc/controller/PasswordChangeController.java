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
@RequestMapping("/api/changepassword")
public class PasswordChangeController {

	private static Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Value(value = "${sendgrid.api.key}")
	private String sendgridKey;

	@Value(value = "${xframe.allowed.origins.deployed_dev}")
	private String angular_address;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void changePassword(@RequestBody UserEntity user) {
		
		final Optional<UserEntity> loginUser = userRepository.findOneByEmailIgnoreCase(user.getEmail());
		if(loginUser.isPresent())
		{
			final String temp = user.getPassword();
			user = loginUser.get();
			user.setPassword(Cryptographer.hashPassword(temp));
			userRepository.save(user);
		}
	}
	
	@RequestMapping(value = "/request/{email}", method = RequestMethod.GET)
	public void requestPasswordChange(@PathVariable("email") String email) {		
		final Optional<UserEntity> userCall = userRepository.findOneByEmailIgnoreCase(email);
		if(userCall.isPresent())
		{   
			MailService.SendPasswordChangeConfirmationMail(email, sendgridKey, angular_address, email);
		}	
	}
}
