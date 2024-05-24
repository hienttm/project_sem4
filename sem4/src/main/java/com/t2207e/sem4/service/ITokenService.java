package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Token;

import java.util.Optional;

public interface ITokenService {
    void add(Token token);
    void deleteById(Integer id);
    Optional<Token> findById(Integer id);
    Optional<Token> findByToken(String token);
}
