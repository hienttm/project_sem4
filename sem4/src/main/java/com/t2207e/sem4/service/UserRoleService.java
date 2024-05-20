package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
