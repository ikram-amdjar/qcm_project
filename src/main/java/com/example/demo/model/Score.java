package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "score")
@Data
@NoArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // On remplace Integer etudiantId par l'objet Etudiant
    @ManyToOne
    @JoinColumn(name = "etudiant_id", insertable = false, updatable = false)
    private Etudiant etudiant;

    @Column(name = "etudiant_id")
    private Integer etudiantId; // On garde l'ID pour faciliter les insertions simples

    // On remplace Integer matiereId par l'objet Matiere
    @ManyToOne
    @JoinColumn(name = "matiere_id", insertable = false, updatable = false)
    private Matiere matiere;

    @Column(name = "matiere_id")
    private Integer matiereId; // On garde l'ID pour les jointures manuelles

    private Integer score;

    private LocalDateTime dateTest = LocalDateTime.now();
}


/*

J'ai ajouté une relation @ManyToOne. C'est ce qui permet à ton application de dire : "Prends ce score et va chercher le nom de la matière qui lui correspond".
Avant : Tu avais juste matiereId.
Après : Tu as l'objet Matiere, ce qui permet de faire des jointures automatiques.

 */