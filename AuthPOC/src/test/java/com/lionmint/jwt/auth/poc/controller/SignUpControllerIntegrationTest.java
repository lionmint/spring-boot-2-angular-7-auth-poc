package com.lionmint.jwt.auth.poc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lionmint.jwt.auth.poc.entities.UserEntity;
import com.lionmint.jwt.auth.poc.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SignUpControllerIntegrationTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testMySQLDatabaseConnection()
	{
		assertThat(userRepository.count(), is(greaterThanOrEqualTo(0l)));
	}
	
	@Test
	public void testSuccessfulSignUp()
	{
		UserEntity user = new UserEntity();
		user.setEmail("Firstname.Lastname@lionmint.com");
		user.setPassword("1234567890");
		user.setFirstName("Firstname");
		user.setLastName("Lastname");

		userRepository.deleteAll();
		
		userRepository.save(user);

		assertThat(userRepository.count(), is(greaterThanOrEqualTo(1l)));
	}
}
