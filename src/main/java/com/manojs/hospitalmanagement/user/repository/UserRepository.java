package com.manojs.hospitalmanagement.user.repository;

import com.manojs.hospitalmanagement.user.entity.User;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<UserDetails> findByUsername(String username);
}