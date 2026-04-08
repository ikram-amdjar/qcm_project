-- 1. CRÉATION DE LA BASE DE DONNÉES
CREATE DATABASE IF NOT EXISTS db_qcm;
USE db_qcm;

-- 2. NETTOYAGE COMPLET (Ordre spécifique pour respecter les contraintes)
SET FOREIGN_KEY_CHECKS = 0; -- Désactive les contraintes pour vider les tables sans erreur
DROP TABLE IF EXISTS reponse_etudiant;
DROP TABLE IF EXISTS score;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS matiere;
DROP TABLE IF EXISTS etudiant;
DROP TABLE IF EXISTS admin;
SET FOREIGN_KEY_CHECKS = 1; -- Réactive les contraintes

-- 3. CRÉATION DES TABLES

CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(100)
);

CREATE TABLE etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(100)
);

CREATE TABLE matiere (
    id INT PRIMARY KEY, -- ID fixe pour correspondre aux questions
    nom VARCHAR(50)
);

CREATE TABLE question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matiere_id INT,
    question TEXT,
    option1 VARCHAR(100),
    option2 VARCHAR(100),
    option3 VARCHAR(100),
    option4 VARCHAR(100),
    reponse_correcte VARCHAR(100),
    CONSTRAINT fk_qst_matiere FOREIGN KEY (matiere_id) REFERENCES matiere(id)
);

CREATE TABLE score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    etudiant_id INT,
    matiere_id INT,
    score INT,
    date_test DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_score_etudiant FOREIGN KEY (etudiant_id) REFERENCES etudiant(id),
    CONSTRAINT fk_score_matiere FOREIGN KEY (matiere_id) REFERENCES matiere(id)
);

CREATE TABLE reponse_etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    etudiant_id INT,
    question_id INT,
    reponse_choisie VARCHAR(100),
    est_correcte BOOLEAN,
    CONSTRAINT fk_rep_etudiant FOREIGN KEY (etudiant_id) REFERENCES etudiant(id),
    CONSTRAINT fk_rep_question FOREIGN KEY (question_id) REFERENCES question(id)
);

-- 4. INSERTION DES UTILISATEURS
INSERT INTO admin (nom, email, password) VALUES ('Admin', 'admin@test.com', '1234');
INSERT INTO etudiant (nom, email, password) VALUES ('Etudiant1', 'etudiant@test.com', '1234');

-- 5. INSERTION DES MATIÈRES (IDs Fixes indispensables pour ton Java)
INSERT INTO matiere (id, nom) VALUES 
(1, 'Mathématiques'), 
(2, 'Espagnol'), 
(3, 'Culture Générale Informatique'), 
(4, 'Anglais'), 
(5, 'Géographie'), 
(6, 'Histoire'), 
(7, 'Sport');

-- 6. INSERTION DES QUESTIONS (10 par matière)

-- 1. MATHÉMATIQUES
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(1, 'Quelle est la racine carrée de 144 ?', '10', '12', '14', '16', '12'),
(1, 'Quelle est la valeur de 5 au cube (5³) ?', '25', '75', '125', '150', '125'),
(1, 'Quel est le résultat de 7 x 8 ?', '54', '56', '58', '62', '56'),
(1, 'Combien de côtés possède un hexagone ?', '5', '6', '7', '8', '6'),
(1, 'Quelle est la somme des angles d''un triangle ?', '90°', '180°', '270°', '360°', '180°'),
(1, 'Si x + 5 = 12, quelle est la valeur de x ?', '5', '7', '8', '17', '7'),
(1, 'Quel est le plus petit nombre premier ?', '0', '1', '2', '3', '2'),
(1, 'Quelle est l''aire d''un carré de 4 cm de côté ?', '8 cm²', '12 cm²', '16 cm²', '20 cm²', '16 cm²'),
(1, 'Quel est le périmètre d''un cercle de rayon r ?', '2πr', 'πr²', '2r', 'πd²', '2πr'),
(1, 'Comment appelle-t-on un angle de 90 degrés ?', 'Aigu', 'Obtus', 'Droit', 'Plat', 'Droit');

-- 2. ESPAGNOL
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(2, 'Comment appelle-t-on une voiture en espagnol ?', 'Noche', 'Coche', 'Casa', 'Escuela', 'Coche'),
(2, 'Comment dit-on "Bonjour" le matin ?', 'Buenas noches', 'Hola', 'Buenos días', 'Hasta luego', 'Buenos días'),
(2, 'Traduisez "Le chien" :', 'El gato', 'El perro', 'El caballo', 'El pájaro', 'El perro'),
(2, 'Comment dit-on "Lundi" en espagnol ?', 'Martes', 'Lunes', 'Jueves', 'Sábado', 'Lunes'),
(2, 'Comment dit-on "Pomme" ?', 'Pera', 'Naranja', 'Manzana', 'Limón', 'Manzana'),
(2, 'Que signifie "La calle" ?', 'Le château', 'La rue', 'Le cahier', 'La chaussure', 'La rue'),
(2, 'Comment dit-on "10" en espagnol ?', 'Cinco', 'Siete', 'Diez', 'Doce', 'Diez'),
(2, 'Comment traduit-on "Je m''appelle..." ?', 'Me gusta', 'Me llamo', 'Estoy', 'Soy de', 'Me llamo'),
(2, 'Que signifie "El hermano" ?', 'Le père', 'Le cousin', 'Le frère', 'L''oncle', 'Le frère'),
(2, 'Que signifie "Mañana" ?', 'Hier', 'Maintenant', 'Demain', 'Toujours', 'Demain');

-- 3. CULTURE GÉNÉRALE INFORMATIQUE
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(3, 'Que signifie CPU ?', 'Central Processing Unit', 'Computer Personal Unit', 'Control Process Utility', 'Core Program Unit', 'Central Processing Unit'),
(3, 'Quel est le principal composant d''un ordinateur ?', 'La souris', 'La carte mère', 'L''écran', 'Le clavier', 'La carte mère'),
(3, 'Qui est le fondateur de Microsoft ?', 'Steve Jobs', 'Mark Zuckerberg', 'Bill Gates', 'Elon Musk', 'Bill Gates'),
(3, 'Quel protocole est utilisé pour naviguer sur le web ?', 'FTP', 'SMTP', 'HTTP', 'SSH', 'HTTP'),
(3, 'Que signifie RAM ?', 'Read Access Memory', 'Random Access Memory', 'Rapid Action Module', 'Remote Audio Memory', 'Random Access Memory'),
(3, 'Quel est l''ancêtre d''Internet ?', 'Arpanet', 'Intranet', 'Ethernet', 'Google', 'Arpanet'),
(3, 'En informatique, qu''est-ce qu''un "Bug" ?', 'Un virus', 'Une erreur de programme', 'Un insecte', 'Un fichier caché', 'Une erreur de programme'),
(3, 'Quelle entreprise a créé l''iPhone ?', 'Samsung', 'Sony', 'Apple', 'Nokia', 'Apple'),
(3, 'Quel langage est utilisé pour le style d''une page web ?', 'HTML', 'Java', 'CSS', 'Python', 'CSS'),
(3, 'Quelle est l''extension d''un fichier exécutable sous Windows ?', '.txt', '.exe', '.jpg', '.pdf', '.exe');

-- 4. ANGLAIS
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(4, 'Traduisez "Une maison" :', 'A mouse', 'A horse', 'A house', 'A car', 'A house'),
(4, 'Comment dit-on "Pomme" en anglais ?', 'Pineapple', 'Apple', 'Orange', 'Grape', 'Apple'),
(4, 'Quel est le pluriel de "Child" ?', 'Childs', 'Childrens', 'Children', 'Childes', 'Children'),
(4, 'Comment dit-on "Jaune" ?', 'Red', 'Blue', 'Green', 'Yellow', 'Yellow'),
(4, 'Traduisez "I am eating" :', 'Je dors', 'Je mange', 'Je cours', 'Je parle', 'Je mange'),
(4, 'Quel est le contraire de "Big" ?', 'Large', 'Small', 'Tall', 'High', 'Small'),
(4, 'Comment dit-on "Merci" ?', 'Please', 'Hello', 'Thank you', 'Goodbye', 'Thank you'),
(4, 'Traduisez "The cat is on the table" :', 'Le chat est sous la table', 'Le chat est sur la table', 'Le chat est devant la table', 'Le chat est derrière la table', 'Le chat est sur la table'),
(4, 'Comment dit-on "Vendredi" ?', 'Tuesday', 'Thursday', 'Friday', 'Saturday', 'Friday'),
(4, 'Traduisez "Water" :', 'Lait', 'Vin', 'Eau', 'Jus', 'Eau');

-- 5. GÉOGRAPHIE
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(5, 'Quelle est la capitale de la France ?', 'Lyon', 'Marseille', 'Paris', 'Bordeaux', 'Paris'),
(5, 'Quel est le plus grand pays du monde ?', 'Chine', 'USA', 'Russie', 'Canada', 'Russie'),
(5, 'Sur quel continent se trouve le Maroc ?', 'Europe', 'Asie', 'Afrique', 'Océanie', 'Afrique'),
(5, 'Quel est le plus long fleuve du monde ?', 'Le Rhin', 'Le Nil', 'L''Amazone', 'Le Mississippi', 'L''Amazone'),
(5, 'Quelle est la capitale de l''Espagne ?', 'Barcelone', 'Séville', 'Valence', 'Madrid', 'Madrid'),
(5, 'Quel océan se trouve entre l''Europe et l''Amérique ?', 'Indien', 'Pacifique', 'Atlantique', 'Arctique', 'Atlantique'),
(5, 'Dans quel pays se trouve la Tour de Pise ?', 'Espagne', 'Italie', 'Grèce', 'France', 'Italie'),
(5, 'Quel est le plus haut sommet du monde ?', 'Mont Blanc', 'Kilimandjaro', 'Everest', 'K2', 'Everest'),
(5, 'Quelle est la capitale du Japon ?', 'Séoul', 'Pékin', 'Tokyo', 'Bangkok', 'Tokyo'),
(5, 'Quel pays a la forme d''une botte ?', 'France', 'Espagne', 'Italie', 'Grèce', 'Italie');

-- 6. HISTOIRE
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(6, 'En quelle année a commencé la Révolution Française ?', '1776', '1789', '1804', '1815', '1789'),
(6, 'Qui était l''empereur des Français sacré en 1804 ?', 'Louis XIV', 'Napoléon Bonaparte', 'Charlemagne', 'Charles de Gaulle', 'Napoléon Bonaparte'),
(6, 'Quelle période historique suit le Moyen Âge ?', 'L''Antiquité', 'La Renaissance', 'La Préhistoire', 'Le 20ème siècle', 'La Renaissance'),
(6, 'Qui a découvert l''Amérique en 1492 ?', 'Vasco de Gama', 'Christophe Colomb', 'Magellan', 'Marco Polo', 'Christophe Colomb'),
(6, 'Dans quel pays se trouvaient les Pharaons ?', 'Grèce', 'Rome', 'Égypte', 'Mésopotamie', 'Égypte'),
(6, 'En quelle année s''est terminée la Seconde Guerre Mondiale ?', '1918', '1939', '1945', '1950', '1945'),
(6, 'Quel roi était surnommé le "Roi Soleil" ?', 'Louis XVI', 'Louis XIV', 'François 1er', 'Henri IV', 'Louis XIV'),
(6, 'Qui a peint la Joconde ?', 'Picasso', 'Van Gogh', 'Léonard de Vinci', 'Claude Monet', 'Léonard de Vinci'),
(6, 'Quel mur est tombé en 1989 ?', 'Le mur de Chine', 'Le mur de Berlin', 'Le mur de Londres', 'Le mur de Paris', 'Le mur de Berlin'),
(6, 'Quelle civilisation a construit le Colisée ?', 'Les Grecs', 'Les Égyptiens', 'Les Romains', 'Les Mayas', 'Les Romains');

-- 7. SPORT
INSERT INTO question (matiere_id, question, option1, option2, option3, option4, reponse_correcte) VALUES 
(7, 'Combien de joueurs y a-t-il dans une équipe de football ?', '7', '9', '11', '13', '11'),
(7, 'Tous les combien d''ans ont lieu les Jeux Olympiques ?', '2 ans', '3 ans', '4 ans', '5 ans', '4 ans'),
(7, 'Dans quel sport utilise-t-on une raquette et un volant ?', 'Tennis', 'Ping-pong', 'Badminton', 'Squash', 'Badminton'),
(7, 'Quelle est la distance d''un marathon ?', '21 km', '42.195 km', '10 km', '100 km', '42.195 km'),
(7, 'Quel pays a gagné la Coupe du Monde de Foot 2022 ?', 'France', 'Maroc', 'Argentine', 'Brésil', 'Argentine'),
(7, 'Qui est surnommé "The GOAT" au basket-ball ?', 'LeBron James', 'Michael Jordan', 'Kobe Bryant', 'Stephen Curry', 'Michael Jordan'),
(7, 'Combien de points vaut un panier classique au basket ?', '1', '2', '3', '4', '2'),
(7, 'Dans quel sport peut-on faire un "Home Run" ?', 'Cricket', 'Baseball', 'Golf', 'Rugby', 'Baseball'),
(7, 'Quel est le nom du terrain au tennis ?', 'Le ring', 'Le court', 'La piste', 'Le stade', 'Le court'),
(7, 'Quel sport se pratique dans une piscine ?', 'Judo', 'Natation', 'Escrime', 'Boxe', 'Natation');

-- 7. VÉRIFICATION FINALE
SELECT m.nom AS Matiere, COUNT(q.id) AS Total_Questions 
FROM matiere m 
LEFT JOIN question q ON m.id = q.matiere_id 
GROUP BY m.nom;
