package com.example.sproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sproject.entity.Role;
import com.example.sproject.repository.RoleRepository;

@Service
public class RoleService {
	
	
	@Autowired
	RoleRepository roleRepository;

	public Role addRole(Role role) {
		return roleRepository.save(role);
	}

	public List<Role> getAllRoles() {
	
		return roleRepository.findAll();
	}

}
