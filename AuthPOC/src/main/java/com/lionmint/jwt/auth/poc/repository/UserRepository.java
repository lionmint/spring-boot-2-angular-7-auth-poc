package com.lionmint.jwt.auth.poc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lionmint.jwt.auth.poc.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findOneByEmailIgnoreCase(String email);
}
