package com.example.demo.repository;

import com.example.demo.model.ReponseEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReponseEtudiantRepository extends JpaRepository<ReponseEtudiant, Integer> {

    // On récupère les réponses de l'étudiant
    List<ReponseEtudiant> findByEtudiantId(Integer etudiantId);

    // Version plus précise pour n'avoir que les réponses liées à une liste de questions
    List<ReponseEtudiant> findByEtudiantIdAndQuestionIdIn(Integer etudiantId, List<Integer> questionIds);
}