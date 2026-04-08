// Il gère simplement le catalogue des matières disponibles pour le QCM.

package com.example.demo.service;

import com.example.demo.model.Matiere;
import com.example.demo.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MatiereService {

    @Autowired
    private MatiereRepository matiereRepo;

    public List<Matiere> getAllMatieres() {
        return matiereRepo.findAll();
    }
}