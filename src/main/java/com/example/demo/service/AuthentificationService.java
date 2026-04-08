package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.model.Etudiant;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.EtudiantRepository;

// Importation des outils Spring pour l'injection et la reconnaissance du service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// L'annotation @Service indique à Spring que cette classe contient  , la logique métier (le "cerveau") de l'authentification.
@Service
public class AuthentificationService {


    // @Autowired demande à Spring d'injecter automatiquement l'instance , du repository Etudiant , @Autowired sert à demander à Spring de te donner automatiquement l'objet dont tu as besoin.

    @Autowired
    private EtudiantRepository etudiantRepo;

   // Injection automatique de l'outil permettant de manipuler la table Admin.
   @Autowired
    private AdminRepository adminRepo;


    // Méthode principale qui vérifie si l'utilisateur existe , On utilise 'Object' car on ne sait pas encore si on va retourner un Etudiant ou un Admin
    public Object authentifier (String email , String password){
        // Logique : on cherche d'abord l'étudiant, puis l'admin
        // // 1. On interroge la table 'etudiant' avec l'email et le mot de passe fournis
        Etudiant e = etudiantRepo.findByEmailAndPassword(email,password);
        // Si l'objet 'e' n'est pas vide, cela signifie qu'un étudiant a été trouvé
        if (e!=null) return e;  // // On arrête la méthode ici et on renvoie l'étudiant trouvé

        // 2. Si on arrive ici, c'est qu'aucun étudiant n'a été trouvé.
        // On cherche alors dans la table 'admin'.
        Admin a = adminRepo.findByEmailAndPassword(email,password);

        // Si l'objet 'a' n'est pas vide, on renvoie l'administrateur trouvé
        if(a!=null) return a;

        // 3. Si aucun étudiant ET aucun admin ne correspondent, on renvoie null
        // Cela idiquera au Controller que l'email ou le mot de passe est faux
        return null;  // Retourne null si aucun utilisateur n'est trouvé
    }
}
