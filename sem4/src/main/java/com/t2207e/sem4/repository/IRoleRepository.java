package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    public Boolean existsByRoleName(String roleName);
    public Optional<Role> getRoleByRoleName(String roleName);
}
