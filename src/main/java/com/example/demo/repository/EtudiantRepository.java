package com.example.demo.repository;

import com.example.demo.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {

    // Cette ligne remplace tout ton SQL "SELECT * FROM etudiant WHERE email=? AND password=?"
    Etudiant findByEmailAndPassword(String email, String password);
}