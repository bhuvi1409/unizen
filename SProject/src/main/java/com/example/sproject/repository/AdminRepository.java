package com.example.sproject.repository;

import com.example.sproject.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT a FROM Admin a WHERE a.email = ?1")
    public Admin findByEmail(String email);

}