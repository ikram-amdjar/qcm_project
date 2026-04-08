package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reponse_etudiant") // Correspond exactement au nom dans ton SQL
@Data
@NoArgsConstructor
public class ReponseEtudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "etudiant_id")
    private Integer etudiantId;

    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "reponse_choisie")
    private String reponseChoisie;

    @Column(name = "est_correcte")
    private boolean estCorrecte;
}