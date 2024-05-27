package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Token;
import com.t2207e.sem4.repository.ITokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService implements ITokenService {

    private ITokenRepository tokenReponsitory;

    public TokenService(ITokenRepository tokenReponsitory) {
        this.tokenReponsitory = tokenReponsitory;
    }

    @Override
    public void add(Token token) {
        tokenReponsitory.save(token);
    }

    @Override
    public void deleteById(Integer id) {
        tokenReponsitory.deleteById(id);
    }

    @Override
    public Optional<Token> findById(Integer id) {
        return tokenReponsitory.findById(id);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenReponsitory.findByToken(token);
    }


}
