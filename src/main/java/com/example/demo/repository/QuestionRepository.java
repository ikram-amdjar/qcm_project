package com.example.demo.repository;

import com.example.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // Remplace getQuestionByMatiere(int matiereId)
    // Spring génère le SQL "SELECT * FROM question WHERE matiere_id = ?" tout seul !
    List<Question> findByMatiereId(int matiereId);
}