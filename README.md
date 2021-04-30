# TERD-project
Projet de création d'un Rogue-like en Java dans le cadre de l'U.E TERD.


## Membres du projet : 
Quentin GARNIER - GitHub : QuentinGarnier  
Davide FISSORE - GitHub : Fissore23  
Antoine VENTURELLI - GitHub : AntoineVen  
Adam BEN AMARA - GitHub : AdamBenAmara  


## Exécution de l'application :
Il suffit d'exécuter la fonction main de la classe Main (fonctionnel dans IntelliJ).  
Particularité en jeu : pour jouer, l'application ouvre une fenêtre graphique avec laquelle on interagit avec la souris et les touches du clavier (que l'on peut changer si on le souhaite). Par défaut, les touches sont :  
- Dans le menu de départ, on interagit avec la souris  
- **WASD** : touches de **déplacement**  
- **Q** : touche d'**action** (appuyer une fois pour commencer une attaque, puis déplacer le curseur avec les touches de déplacement jusqu'à une cible et appuyer à nouveau sur la touche d'action ; idem pour interagir avec le Marchand)  
- **I** : touche pour ouvrir/fermer l'**inventaire** (naviguer dans l'inventaire avec **W**)  
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
Toute la documentation relative au projet peut être consultée sur notre [page wiki](https://github.com/QuentinGarnier/TERD-project/wiki).  


## Notes de mise à jour ([v4.0](https://github.com/QuentinGarnier/TERD-project/wiki/Version-4.0)) :

- Optimisation du brouillard de guerre (avec exploration progressive)
- Ajout des musiques du jeu
- Niveau et expérience du joueur effectifs
- Traductions du jeu en 4 langues
- Ajout du menu de départ
- Ajout du marchand et des prix sur les items
- Ajout du système d'étages
- Ajout des thèmes par étages (+ thème Marchand et Boss Final)
- Ajout des obstacles dans les salles
- Finition de l'inventaire (consommer/jeter/équiper… un item)
- Ajout d'un décrément de la famine tous les 5 tours et de la mort par famine
- Ajout du Boss Final et de sa stratégie
- Ajout des effets sonores à l'utilisation des items + à l'activation d'un piège
- Ajout des animations (Threads) pour les états
- Ajout de la touche ESC / P pour accéder à un petit "menu en jeu"
- Divers équilibrages et ajouts d'items
- Correctifs divers


## ATTENTION !  

Lors de la phase de tests durant la compilation en .jar, il est possible (sous Mac OS uniquement ?) que les sons des items soient joués lors de leur utilisation. Il est donc <ins>vivement recommandé</ins> de **couper ou réduire le son** lors de cette phase.
