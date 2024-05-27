package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token,Integer> {
    Optional<Token> findByToken(String token);
}
