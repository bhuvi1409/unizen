package com.example.sproject.repository;

import com.example.sproject.entity.Role;
import com.example.sproject.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    public Role findByName(User user);
}
