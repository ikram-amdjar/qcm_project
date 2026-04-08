package com.example.demo.repository;

import com.example.demo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // Idem pour l'admin : Spring génère le SQL automatiquement
    Admin findByEmailAndPassword(String email, String password);
}