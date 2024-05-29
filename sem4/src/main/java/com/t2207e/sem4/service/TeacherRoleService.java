package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.repository.IUserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherRoleService implements ITeacherRoleService {
    private final IUserRoleRepository userRoleRepository;

    public TeacherRoleService(IUserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public void add(Role role) {
       userRoleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRole> getAllTeacher(Role role) {
        return userRoleRepository.getUserRoleByRole(role);
    }
}
