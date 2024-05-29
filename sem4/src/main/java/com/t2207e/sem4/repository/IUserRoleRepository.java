package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> getUserRoleByRole(Role role);

    void save(Role role);
}
