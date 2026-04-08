package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.model.Etudiant;
import com.example.demo.service.AuthentificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AuthentificationService authService;

    @GetMapping("/")
    public String index(HttpSession session) {
        // Si déjà connecté, on redirige vers le bon espace
        if (session.getAttribute("etudiant") != null) return "redirect:/choix-matiere";
        if (session.getAttribute("admin") != null) return "redirect:/admin/dashboard";
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Object user = authService.authentifier(email, password);

        if (user instanceof Etudiant) {
            session.setAttribute("etudiant", user);
            return "redirect:/choix-matiere";
        } else if (user instanceof Admin) {
            session.setAttribute("admin", user);
            return "redirect:/admin/dashboard";
        }
        return "redirect:/?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}