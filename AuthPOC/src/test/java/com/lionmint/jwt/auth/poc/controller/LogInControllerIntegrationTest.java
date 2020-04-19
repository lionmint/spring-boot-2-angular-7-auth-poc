package com.lionmint.jwt.auth.poc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lionmint.jwt.auth.poc.entities.UserEntity;
import com.lionmint.jwt.auth.poc.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogInControllerIntegrationTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testSuccessfulLogin()
	{
		UserEntity user = new UserEntity();
		user.setEmail("Firstname.Lastname@lionmint.com");
		user.setPassword("1234567890");
		user.setFirstName("Firstname");
		user.setLastName("Lastname");
		
		if(!userRepository.findOneByEmailIgnoreCase(user.getEmail()).isPresent())
			userRepository.save(user);

		boolean response = false;
		
		Optional<UserEntity> signUserOptional = userRepository.findOneByEmailIgnoreCase(user.getEmail());
		if(signUserOptional.isPresent())
		{
			if(signUserOptional.get().getPassword().compareTo(user.getPassword())==0)
			{
				response = true;
			}
		}
		
		assertThat(response, is(true));
	}
	
	@Test
	public void testUnsuccessfulLogin()
	{
		UserEntity user = new UserEntity();
		user.setEmail("Firstname.Lastname@lionmint.com");
		user.setPassword("1234567890");
		user.setFirstName("Firstname");
		user.setLastName("Lastname");
		
		if(!userRepository.findOneByEmailIgnoreCase(user.getEmail()).isPresent())
			userRepository.save(user);

		user.setPassword("987654321");
		
		boolean response = false;
		
		Optional<UserEntity> signUserOptional = userRepository.findOneByEmailIgnoreCase(user.getEmail());
		if(signUserOptional.isPresent())
		{
			if(signUserOptional.get().getPassword().compareTo(user.getPassword())==0)
			{
				response = true;
			}
		}
		
		assertThat(response, is(false));
	}
}
