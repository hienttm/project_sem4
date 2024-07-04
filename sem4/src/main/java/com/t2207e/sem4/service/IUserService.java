package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.TeacherSalaryDTO;
import com.t2207e.sem4.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();
    Optional<User> getUserById(Integer id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByPhone(String phone);

    Optional<User> getUserByEmail(String email);
    public List<User> getUsersByPageNumber(Integer pageNumber, Integer pageSize, String tableName);
    void add(User user);
    void deleteById(Integer id);
    Optional<User> findFirstByEmail(String email);
    List<TeacherSalaryDTO> GetAllSalaryTeacher();
}
