# TERD-project
Projet de création d'un Rogue-like en Java dans le cadre de l'U.E TERD.


## Membres du projet : 
Quentin GARNIER - GitHub : QuentinGarnier  
Davide FISSORE - GitHub : Fissore23  
Antoine VENTURELLI - GitHub : AntoineVen  
Adam BEN AMARA - GitHub : AdamBenAmara  


## Exécution de l'application :
Il suffit d'exécuter la fonction main de la classe Main (fonctionnel dans IntelliJ).  
Particularité en jeu : pour jouer, l'application ouvre une fenêtre graphique avec laquelle on interagit avec la souris et les touches du clavier (que l'on peut changer si on le souhaite à partir du menu de départ ou en jeu avec la touche **ESC** / **P** (par défaut)). Par défaut, les touches sont :  
- Dans le menu de départ, on interagit avec la souris  
- **WASD** : touches de **déplacement**  
- **Q** : touche d'**action** (appuyer une fois pour commencer une attaque, puis déplacer le curseur avec les touches de déplacement jusqu'à une cible et appuyer à nouveau sur la touche d'action ; idem pour interagir avec le Marchand)  
- **I** : touche pour ouvrir/fermer l'**inventaire**  
- **ESC** ou **P** : touche pour ouvrir le **menu en jeu** (interactif avec la souris) pour couper/remettre le son ou revenir au menu de départ  
- **R** : touche pour relancer la partie avec la même spécialité   
- **Touches fléchées** : permettent de visualiser la carte en dehors de l'écran sans avoir à se déplacer  
<br /> 

<ins>**Vous pouvez également retrouver toutes les informations nécessaires à la compréhension (touches, entités, objectifs, scénario...) directement en jeu dans le menu "AIDE".**</ins>  

<br /> 

Les options enregistrées sont sauvegardées pour chaque exécution de l'application (langue, sons et difficulté).  

<br />

L'affichage de la carte est en fenêtre graphique (JFrame), toutefois il est possible d'afficher la carte sur le Terminal (utile pour voir la carte sans les éléments graphiques).


## Documentation :
Toute la documentation relative au projet peut être consultée sur notre [page wiki](https://github.com/QuentinGarnier/TERD-project/wiki) (pour les informations détaillées sur la dernière version, cliquez [ici](https://github.com/QuentinGarnier/TERD-project/wiki/Version-5.0)).  


## Notes de mise à jour ([v5.0](https://github.com/QuentinGarnier/TERD-project/wiki/Version-5.0)) :
- Position de ciblage lors de l'attaque retenue  
- Correctif de l'enregistrement des _settings_ dans le .jar  
- Amélioration du menu options en jeu (touche ESC)  
- Ajout d'un popup de fin de jeu (Victoire ou Défaite)  
- Déblocage progressif des difficultés (en cas de Victoire dans la difficulté la plus haute disponible)  
- Ajout du système de classement, et demande de nom avant d'entrer en jeu  
- Ajout de la résolution d'écran dans les options  
- Ajout du changement de raccourcis claviers dans les options  
- Ajout d'un popup au premier lancement du jeu  
- Ajout d'une "zone de repérage" pour les monstres (+ visualisation au survol)  
- Ajout de rareté sur les items influençant leur apparition et leur prix  
- Ajout de nouveaux items  
- Dernières traductions implémentées  
- Correctifs et équilibrages divers  


## Astuces pour tester le jeu  
Afin de pouvoir plus rapidement atteindre la salle du boss, il est possible de manuellement modifier le fichier "settings" (dossier à la racine "$HOME/ThatTimeTheHeroSavedTheVillage" sur Mac et Linux, dans "AppData/Roaming/ThatTimeTheHeroSavedTheVillage" sur Windows) en remplaçant la valeur de "sDifficulty 1" par 0 : ceci met le jeu dans une difficulté "Tutorial" de 2 niveaux uniquement.  
Il est également possible de déverouiller les difficultés supérieurs en modifiant la valeur de "sUnlocked 1" par 5.  


## ATTENTION !  

Lors de la phase de tests durant la compilation en .jar, il est possible (sous Mac OS uniquement ?) que les sons des items soient joués lors de leur utilisation. Il est donc <ins>vivement recommandé</ins> de **couper ou réduire le son** lors de cette phase.
