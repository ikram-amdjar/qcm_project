package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="matiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Utilisation de l'objet Integer au lieu du primitif int

    private String nom;
}