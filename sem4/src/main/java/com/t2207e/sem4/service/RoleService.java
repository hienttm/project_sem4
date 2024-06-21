package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.repository.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Boolean existsByRoleName(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    @Override
    public Optional<Role> getRoleByRoleName(String roleName) {
        return roleRepository.getRoleByRoleName(roleName);
    }

    @Override
    public List<Role> getRolesByStatus(Integer status) {
        return roleRepository.getRolesByStatus(status);
    }
}
