package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Boolean existsByRoleName(String roleName);
    Optional<Role> getRoleByRoleName(String roleName);
    List<Role> getRolesByStatus(Integer status);
}
