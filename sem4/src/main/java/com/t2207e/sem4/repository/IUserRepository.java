package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    public Optional<User> getUserByUsername(String username);
    public Optional<User> getUserByPhoneNumber(String phoneNumber);
    public Boolean existsByUsername(String username);
}
