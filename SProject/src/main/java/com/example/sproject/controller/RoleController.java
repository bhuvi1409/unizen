package com.example.sproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sproject.entity.Role;
import com.example.sproject.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
@PostMapping("/addRole")
public Role addRole(@RequestBody Role role) {
	return roleService.addRole(role);
}

@GetMapping("/getRoles")
public List<Role>getAllRoles(){
	return roleService.getAllRoles();
}
	
}
