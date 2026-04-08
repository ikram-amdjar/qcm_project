// C'est ici que se trouve la logique complexe : mélange (shuffle), enregistrement des détails et calcul du score.


package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    @Autowired
    private ReponseEtudiantRepository reponseRepo;

    // 1. Récupération et mélange des questions
    public List<Question> preparerExamen(Integer matiereId) {
        List<Question> questions = questionRepo.findByMatiereId(matiereId);
        // Mélange (Shuffle) pour que l'ordre change à chaque test
        Collections.shuffle(questions);
        // On limite à 10 questions pour le test
        return questions.subList(0, Math.min(questions.size(), 10));
    }

    // 2.Enregistrement de chaque réponse (Détails)
    public void enregistrerReponse(Integer etudiantId, Integer questionId, String choix, String bonneReponse) {
        ReponseEtudiant reponse = new ReponseEtudiant();
        reponse.setEtudiantId(etudiantId); // Dans le Modèle (ReponseEtudiant.java) : On a déja définit la méthode set  avec Lombok @Data , donc on l'utilise direcetement ici .
        reponse.setQuestionId(questionId);
        reponse.setReponseChoisie(choix);
        // Comparaison directe pour le flag "est_correcte"
        reponse.setEstCorrecte(choix.equals(bonneReponse));
        reponseRepo.save(reponse);
    }

    // 3. Calcul du score final sur 20
    public double calculerScoreSur20(List<ReponseEtudiant> reponses) {
        if (reponses.isEmpty()) return 0;

        long bonnes = reponses.stream().filter(ReponseEtudiant::isEstCorrecte).count();
        // Exemple : (bonnes réponses / total questions) * 20       // Cette ligne transforme ta liste de réponses en une note sur 20 en filtrant uniquement les bonnes réponses (isEstCorrecte)
        return (double) bonnes / reponses.size() * 20 ;             // t en calculant leur proportion par rapport au total, tout en utilisant (double) pour garantir la précision mathématique avant de convertir le résultat final en entier (int).
    }

    // 4.sauvgarde du score final dans la table score
    public void sauvegarderResultat (Integer etudiantId,Integer matiereId, int note){
        Score s = new Score();
        s.setEtudiantId(etudiantId);
        s.setMatiereId(matiereId);
        s.setScore(note);
        scoreRepo.save(s);
    }





}






























/*
on regroupe tout dans cette classe , pas besoin de créer une classe service pour les question s les réponses , on a pas fait ça ,
en effet :
1. La logique est "liée"
Dans ton projet, une réponse n'existe pas sans question, et le score dépend des deux. Si tu sépares tout, ton code va devenir un puzzle complexe.

Exemple : Pour savoir si une réponse est bonne, tu as besoin de comparer le choix de l'étudiant avec la reponse_correcte située dans la table Question.

En mettant cela dans un seul QuizService, le service peut manipuler les deux Repositories (QuestionRepo et ReponseRepo) au même endroit. C'est beaucoup plus simple.
 */


/*
1. Le rôle de subList(début, fin)
La méthode subList permet d'extraire une portion d'une liste.
0 : On commence au tout début (la première question).
10 : On s'arrête juste avant la 10ème position.
C'est comme si tu avais un paquet de 50 photos et que tu ne gardais que les 10 premières sur le dessus de la pile.

2. Pourquoi utiliser Math.min(questions.size(), 10) ?
C'est la partie la plus intelligente de la ligne. Elle sert à gérer les cas où tu as moins de 10 questions en base de données.

Scénario A (Cas normal) : Tu as 50 questions.
Math.min(50, 10) renvoie 10.
Le code fait subList(0, 10). Tu as tes 10 questions.

Scénario B (Cas critique) : Tu n'as que 6 questions en base de données pour cette matière.
Si tu écrivais subList(0, 10), Java ferait une erreur (IndexOutOfBoundsException) car il ne peut pas aller jusqu'à 10 s'il n'y en a que 6.
Math.min(6, 10) renvoie 6.
Le code fait subList(0, 6). Le programme ne plante pas et renvoie les 6 questions disponibles.
 */



/*

Voici exactement comment Lombok transforme ton attribut en méthode :

1. La règle de transformation

Quand tu mets @Data, Lombok scanne tes variables privées. Pour la variable etudiantId, il suit cette logique :
Il prend le mot "set".
Il prend le nom de ta variable : "etudiantId".
Il met la première lettre en majuscule pour respecter les normes Java : "EtudiantId".

Résultat final : setEtudiantId.
 */



/*

.stream() : Transforme ta liste de réponses en un flux que l'on peut traiter étape par étape.
.filter(ReponseEtudiant::isEstCorrecte) : C'est comme un tamis. On ne garde que les objets ReponseEtudiant où la méthode isEstCorrecte() renvoie true.

 */