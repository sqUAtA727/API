package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.repo.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role addRole(Role role) {
        return roleRepo.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
