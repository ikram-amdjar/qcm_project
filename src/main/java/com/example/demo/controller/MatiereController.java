package com.example.demo.controller; // 1. Déclaration du package

// 2. Les imports nécessaires
import com.example.demo.service.MatiereService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MatiereController {

    @Autowired
    private MatiereService matiereService;

    @GetMapping("/choix-matiere")
    public String liste(Model model, HttpSession session) {
        // Sécurité : on vérifie si l'étudiant est bien passé par le login
        if (session.getAttribute("etudiant") == null) {
            return "redirect:/";
        }

        // On récupère les matières via le service et on les envoie à la vue (Model)
        model.addAttribute("matieres", matiereService.getAllMatieres());

        return "choix_matiere"; // Nom du fichier HTML dans src/main/resources/templates
    }
}