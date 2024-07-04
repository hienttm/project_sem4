package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.TeacherRegister;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> getUserRoleByRole(Role role);
    void save(Role role);
    List<UserRole> getUserRolesByUser(User user);
    Optional<UserRole> getUserRoleByUserAndRole_RoleName(User user, String roleName);
    int countUserRoleByUser_StatusAndRole_RoleName(Integer status ,String roleName);
}
