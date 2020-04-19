package com.lionmint.jwt.auth.poc.services;

import java.util.List;

import com.lionmint.jwt.auth.poc.entities.UserEntity;

public interface UserService {

    UserEntity save(UserEntity user);
    List<UserEntity> findAll();
    void delete(long id);
    UserEntity findOne(String username);

    UserEntity findById(Long id);
}
