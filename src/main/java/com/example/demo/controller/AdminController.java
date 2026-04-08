package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.repository.MatiereRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ScoreRepository; // 1. Ajout de l'import
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private MatiereRepository matiereRepo;

    @Autowired
    private ScoreRepository scoreRepo; // 2. Injection du repository des scores

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        // --- 3. LOGIQUE DES STATISTIQUES ---
        List<Object[]> stats = scoreRepo.findAverageScoreByMatiere();
        List<String> labels = new ArrayList<>();
        List<Double> dataPoints = new ArrayList<>();

        for (Object[] row : stats) {
            labels.add((String) row[0]);     // Nom de la matière
            dataPoints.add((Double) row[1]);  // Moyenne (AVG)
        }

        model.addAttribute("labels", labels);
        model.addAttribute("dataPoints", dataPoints);
        // ----------------------------------

        model.addAttribute("questions", questionRepo.findAll());
        model.addAttribute("matieres", matiereRepo.findAll());
        model.addAttribute("nouvelleQuestion", new Question());
        return "admin_dashboard";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Question q) {
        questionRepo.save(q);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        questionRepo.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Question q = questionRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalide Id:" + id));
        model.addAttribute("question", q);
        model.addAttribute("matieres", matiereRepo.findAll());
        return "edit_question";
    }
}

/*

Dans AdminController.java (Le Contrôleur)
J'ai préparé les deux listes dont le graphique a besoin :
Labels : La liste des noms des matières.
DataPoints : La liste des moyennes correspondantes.

 */