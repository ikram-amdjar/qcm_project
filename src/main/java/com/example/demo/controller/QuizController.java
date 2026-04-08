package com.example.demo.controller;

import com.example.demo.model.Etudiant;
import com.example.demo.model.Question;
import com.example.demo.model.ReponseEtudiant;
import com.example.demo.repository.ReponseEtudiantRepository;
import com.example.demo.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private ReponseEtudiantRepository reponseEtudiantRepo;

    @GetMapping("/start")
    public String start(@RequestParam Integer matiereId, HttpSession session) {
        if (session.getAttribute("etudiant") == null) return "redirect:/";

        List<Question> list = quizService.preparerExamen(matiereId);

        if (list == null || list.isEmpty()) {
            return "redirect:/choix-matiere?error=noquestions";
        }

        session.setAttribute("questions", list);
        session.setAttribute("index", 0);
        session.setAttribute("score", 0);
        session.setAttribute("currentMatiereId", matiereId);

        return "redirect:/quiz/jouer";
    }

    @GetMapping("/jouer")
    public String jouer(Model model, HttpSession session) {
        List<Question> qs = (List<Question>) session.getAttribute("questions");
        Integer idx = (Integer) session.getAttribute("index");

        if (qs == null || idx == null) return "redirect:/choix-matiere";
        if (idx >= qs.size()) return "redirect:/quiz/resultat";

        model.addAttribute("q", qs.get(idx));
        model.addAttribute("numQuestion", idx + 1);
        model.addAttribute("totalQuestions", qs.size());

        return "question";
    }

    @PostMapping("/valider")
    public String valider(@RequestParam(required = false) String choix, HttpSession session) {
        List<Question> qs = (List<Question>) session.getAttribute("questions");
        Integer idx = (Integer) session.getAttribute("index");
        Etudiant e = (Etudiant) session.getAttribute("etudiant");

        if (qs == null || idx == null || e == null) return "redirect:/";
        if (idx >= qs.size()) return "redirect:/quiz/resultat";

        if (choix == null) choix = "";
        Question q = qs.get(idx);

        quizService.enregistrerReponse(e.getId(), q.getId(), choix, q.getReponseCorrecte());

        if (choix.equalsIgnoreCase(q.getReponseCorrecte())) {
            Integer currentScore = (Integer) session.getAttribute("score");
            session.setAttribute("score", (currentScore != null ? currentScore : 0) + 1);
        }

        session.setAttribute("index", idx + 1);
        return "redirect:/quiz/jouer";
    }

    @GetMapping("/resultat")
    public String resultat(Model model, HttpSession session) {
        Integer s = (Integer) session.getAttribute("score");
        List<Question> qs = (List<Question>) session.getAttribute("questions");
        Etudiant e = (Etudiant) session.getAttribute("etudiant");
        Integer mId = (Integer) session.getAttribute("currentMatiereId");

        if (s == null || qs == null || e == null || mId == null) {
            return "redirect:/choix-matiere";
        }

        int totalQuestions = qs.size();
        int noteSur20 = (totalQuestions > 0) ? (s * 20) / totalQuestions : 0;

        // Sauvegarde du score final (Statistiques globales)
        quizService.sauvegarderResultat(e.getId(), mId, noteSur20);

        // --- LOGIQUE DE FILTRAGE DU RÉCAPITULATIF ---
        // 1. Extraire uniquement les IDs des questions du quiz actuel
        List<Integer> questionIds = qs.stream()
                .map(Question::getId)
                .collect(Collectors.toList());

        // 2. Récupérer tout l'historique de ces questions pour cet étudiant
        List<ReponseEtudiant> historique = reponseEtudiantRepo.findByEtudiantIdAndQuestionIdIn(e.getId(), questionIds);

        // 3. NE GARDER QUE LA DERNIÈRE RÉPONSE (le test actuel)
        // On utilise une Map pour "écraser" les anciens doublons par les nouveaux
        java.util.Collection<ReponseEtudiant> recapUnique = historique.stream()
                .collect(Collectors.toMap(
                        ReponseEtudiant::getQuestionId, // Clé : l'ID de la question
                        r -> r,                         // Valeur : l'objet réponse
                        (existant, nouveau) -> nouveau  // En cas de doublon, on garde le plus récent
                )).values();

        model.addAttribute("recapitulatif", recapUnique);
        // --------------------------------------------

        model.addAttribute("note", noteSur20);
        model.addAttribute("points", s);
        model.addAttribute("total", totalQuestions);

        return "resultat";
    }
}