package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
}
