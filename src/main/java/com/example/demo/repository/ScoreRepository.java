package com.example.demo.repository;

import com.example.demo.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Import important
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    // Cette méthode permet de récupérer le nom de la matière et la moyenne des scores
    @Query("SELECT s.matiere.nom, AVG(s.score) FROM Score s GROUP BY s.matiere.nom")      //  Elle demande à la base de données de calculer la moyenne de chaque matière toute seule. C'est beaucoup plus rapide que de le faire à la main en Java.
    List<Object[]> findAverageScoreByMatiere();
}