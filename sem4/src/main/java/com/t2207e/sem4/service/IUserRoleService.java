package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface IUserRoleService {
    void add(UserRole userRole);
    void deleteById(Integer id);
    List<UserRole> getUserRolesByUser(User user);
    Optional<UserRole> getUserRoleByUserAndRole_RoleName(User user, String roleName);
    int countUserRoleByRole_RoleName(String roleName);
}
