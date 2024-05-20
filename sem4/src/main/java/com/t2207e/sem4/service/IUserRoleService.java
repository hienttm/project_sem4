package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.UserRole;

public interface IUserRoleService {
    void add(UserRole userRole);
    void deleteById(Integer id);
}
