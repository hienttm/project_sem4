package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Token;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.getUserByPhoneNumber(phone);
    }

    @Override
    @Procedure("CALL GetPaginatedData(?,?,?)")
    public List<User> getUsersByPageNumber(Integer pageNumber, Integer pageSize, String tableName) {
        return null;
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }




}
