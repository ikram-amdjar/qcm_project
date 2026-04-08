package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="question")
@Data // Génère automatiquement Getters, Setters, toString, equals
@NoArgsConstructor // Génère le constructeur vide obligatoire pour JPA
@AllArgsConstructor // Génère un constructeur avec tous les champs
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    // Correction : 'reponse_correcte' pour correspondre à votre SQL
    @Column(name = "reponse_correcte")
    private String reponseCorrecte;

    @Column(name = "matiere_id")
    private Integer matiereId;
}