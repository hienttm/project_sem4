package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.UserRole;

import java.util.List;

public interface ITeacherRoleService {
    void add(Role role);
    void deleteById(Integer id);
    List<UserRole> getAllTeacher(Role role);
}
