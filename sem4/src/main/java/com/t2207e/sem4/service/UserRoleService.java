package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService implements IUserRoleService{
    private final IUserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(IUserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void add(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public void deleteById(Integer id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRole> getUserRolesByUser(User user) {
        return userRoleRepository.getUserRolesByUser(user);
    }

    @Override
    public Optional<UserRole> getUserRoleByUserAndRole_RoleName(User user, String roleName) {
        return userRoleRepository.getUserRoleByUserAndRole_RoleName(user, roleName);
    }

    @Override
    public int countUserRoleByRole_RoleName(String roleName) {
        return userRoleRepository.countUserRoleByUser_StatusAndRole_RoleName(1 ,roleName);
    }

}
