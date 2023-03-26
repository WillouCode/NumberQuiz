# NumberQuiz
Mini projet Quiz de calcul mental sous Android/Java

L'application se compose d'une simple Activity servant de page principale et permettant la modification de paramètres
Le Quiz génère des Questions, qui sont des opérations choisies avec la Classe Random entre deux nombres générés par la Classe Random
La Question permet de générer des Propositions, filtrées et réduites au nombre de 3 auquel s'ajoute la Réponse de la Question pour proposer un format de choix multiple à 4 possibilitées

Les Propositions sont générés par diverses manières :
1)Ajout/Retrait de 10 à la valeur de la Réponse
2)Décalage de +-5 rapport à la valeur de la Réponse
3)Calcul d'un membre de la suite de Syracuse
4)Multiplication par un Double entre 0.00 et 1.00
5)Inversion de la position des chiffres de la Réponse
6)Génération Aléatoire entre 0 et Réponse*2

Les Valeurs Négatives où immédiatement identifiables comme fausse sont retirées
Suite à quoi, dans le cas où le total restant ne permettrait pas de remplir 3 autres choix, un remplissage de Réponse-10 avec pas de 2 est effectué

Ces opérations semblent permettrent dans la plupart des Questions de proposer aussi bien quelques réponses assez proches que certaines facilement identifiables comme fausse

Dans la première version, Il est possible de voir la génération des Questions sans limite de Nombre où de Temps
Dans la deuxième version, Les limites sont implémentés, de même que le choix de la Difficuletée, pour permettre une utilisation pratique de l'Application

La Difficuletée modifie :
1)Le Nombre de Questions
2)Le Temps accordé pour chaque Question
3)Les plages de valeurs et opérations générées

Pour lance l'Application, un Utilisateur choisit sa Difficulté et clique sur le Bouton
Il effectue ensuite une série de calculs et est présenté à l'écran des Résultats
Depuis l'écran des Résultats, Il peut appuyer sur la Touche Retour Arrière de son Appareil pour revenir sur l'écran de Départ

Bugs Découverts :
1)Le Timer avance de 2 en 2 lors du lancement du Quiz et lors du Changement d'Orientation de l'appareil.
Le Lancement du Timer doit certainement s'effectuer deux fois dans le Cycle de Création de l'Activité
2)Un cas de doublon dans les Options de Réponse à été observé pour une Question dont la Réponse était 0

Problème d'UI :
1)Le Toast permettant d'afficher une Réaction à la Réponse se supperpose au Bouton de Réponse D, le rendant innaccessible quelques instants
2)L'enchaînement de Toast dans le cadre de Réponses successives accentue le Problème
