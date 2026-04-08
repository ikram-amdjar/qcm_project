package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="admin")
@Data // Génère Getters, Setters, toString, etc.
@NoArgsConstructor // Constructeur vide (obligatoire pour JPA)
@AllArgsConstructor // Constructeur avec tous les champs
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Utilisation de Integer pour la gestion des IDs null

    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}