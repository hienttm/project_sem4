package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> getAllRole();
    Optional<Role> getRoleById(Integer id);
    void add(Role role);
    void deleteById(Integer id);
    Boolean existsByRoleName(String roleName);
    Optional<Role> getRoleByRoleName(String roleName);
    List<Role> getRolesByStatus(Integer status);
}
