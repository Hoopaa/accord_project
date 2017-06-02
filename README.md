# accord_project

Un moteur de recherche où l'on peut indiquer un ou plusieurs accords, comme par exemeple : "G C Em7 Dm", et il nous retourne une liste de chansons utilisant cette suite d'accords.

Projet réalisé dans le cadre du cours Web Mining de la HES-SO MSE.

## Fonctionnalités / cas d’utilisation

Durant la composition d'une musique, lors d'un choix d'une suite harmonique (série d'accords qui forme la structure global du morceau) une question peut surgir : est-ce qu'une autre chanson utilise cette même série d'accords ?

L'idée de ce moteur de recherche est de récupérer une liste de chanson en indiquant la série d'accord en utilisant le format englais standard ( [Wiki : Chord names and symbols ](https://en.wikipedia.org/wiki/Chord_names_and_symbols_(popular_music))).

Pour effectuer la recherche, une interface est fournie pour aider l'utilisateur à choissir les accords.

* Il choisit la fondamentale, puis :
* La formation,
* Une couleur
* Et une note de basse optionelle

Il peut aussi entendre les accords à la suite.

## Répartition

| Catégorie  | Responsable |
| ------------- | ------------- |
| Data  | Pasquier & Nydegger |
| Parsing  | Pasquier  |
| Indexing  | Pfeiffer  |
| Retrieving  | Pfeiffer  |
| Interface  | Nydegger  |

## Plannings

| Date  | Objectifs |
| ------------- | ------------- |
| 07.04.2017 | Validation du projet  |
| 28.04.2017 | Description du projet et des données  |
| 05.05.2017 | Création git et du readme, commencer le parser pour déjà avoir quelques données |
| 05.05.2017 | Commencement des parties distinctes, finalisation du parser |
| 12.05.2017 | Avancement, finalisation de l'indexing |
| 19.05.2017 | Avancement, finalisation du retrieving |
| 26.05.2017 | Interface, documentation suivant les directives du projet, finalisation |
| 02.06.2017 | Rendu du projet |


## Contributeurs

  * Ludovic Pfeiffer
  * Brian Nydegger
  * Gérôme Pasquier


## Autres

 * Pouvons-nous créer notre propre indexeur? => utiliser Lucen
 * Nous avons 2800 artistes et 16000 titres => OK
 * Sources des données : http://www.boiteachansons.net/Partitions/index.php
 * Problèmes avec les caractères "/" et "#" dans l'url, on les a remplacer par un code et le python les converti
