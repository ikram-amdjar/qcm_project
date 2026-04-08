package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "etudiant")
@Data // Génère Getters, Setters, toString, etc.
@NoArgsConstructor // Constructeur vide (obligatoire pour JPA)
@AllArgsConstructor // Constructeur avec tous les champs
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    @Column(unique = true) // Optionnel : empêche d'avoir deux fois le même email
    private String email;

    private String password;
}