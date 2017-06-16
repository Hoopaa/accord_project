# accord_project

Un moteur de recherche où l'on peut indiquer un ou plusieurs accords, comme par exemple : "G C Em7 Dm", et il nous retourne une liste de chansons utilisant cette suite d'accords.

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

## Données

### Sources des données

Toutes les données proviennent du site http://www.boiteachansons.net/Partitions/index.php

### Quantité de données

Le sites boite à chansons contient environs 2800 artistes et 14500 titres. Ce qui représente un peu moins de 4 Mo de données stockées une fois le parsing effectué. Cependant, la quantité de donnée peu évoluer si des chansons sont ajoutées au site boite à chanson.

### Stockage des données

Les données sont stockées dans des fichiers ".txt". Il y a un total de 27 fichier. Chaque fichier contient les artistes commencant par la lettre du nom du fichier. Ainse le fichier "A.txt" va contenir tous les artistes commencant par la lettre A comme Axelle Red par exemple. Le 27ème fichier contient les artistes qui ne commence pas par une lettre mais généralement par un chiffre ou autre comme 1789 les amants de la Bastille par exemple. Ce fichier se nomme "9.txt"

### Format des données stockées

Une chanson représente une ligne dans un fichier ".txt" (se terminant par "\n").
Une ligne se présente sous la forme suivante :

_nom_de_l'artiste_,_titre_de_la_chanson_,_url_de_la_chanson_,[_accord_1_,_accord_2_,...,_accord_n_]

Tous les champs sont séparté par des "," et les accord sont encadré de "[" "]"

### Prétraitement

Il y a pas vraiment de prétraitement qui ont été effectué sur les données ormis de l'extration.
Cependant, certain titres (paroles et accords) ont été retiré du site boite à chanson en raison des droits d'auteurs. Mais le titre est toujours présent sur le site boite à chanson. Donc si le titre est présent mais que les accords et les paroles ne sont pas présent, le titre en question n'est pas stocké.

## Conception

### Serveur Python

Un serveur python à été réalisé avec flask. Il permet à l'interface de communiquer avec l'indexeur et d'obtenir plus de détails comme l'url d'une video youtube et les paroles avec les accords d'un titre.

Les routes disponibles sont les suivantes :
- /getInfo/\<artiste>/\<titre>
- /search/\<query>
- /update

#### /getInfo/\<artiste>/\<titre>

La route /getInfo/\<artiste>/\<titre> retourne au format JSON l'url d'une video YouTube correspondant à la l'artiste et au titre passé en paramêtre. Elle retourne aussi les paroles et les accords du titre en question. Une version ".txt" est disponible dirrectement depuis le site boite à chanson. Exemple d'url pour l'artiste Aaron avec le titre Blow : http://www.boiteachansons.net/Txt/Aaron/Blow.txt. Un simple prétraitement permet d'enlever l'en-tête et le pied de page du fichier ".txt".

Pour la recherche de la video YouTube, nous n'utilisons pas l'api YouTube mais Beautiful Soup pour parser le résultat d'une recherche par mot clé sur YouTube. Les mots clé etant le nom de l'artiste et le titre comme l'exemple suivant pour Blow de Aaron : https://www.youtube.com/results?search_query=Aaron+blow.

#### /search/\<query>
Cette route exécute l'indexer avec le paramètre *query* :
```Python
@app.route('/search/<query>', strict_slashes=False)
def get_search(query):
    return os.popen('java -jar ./Indexer.jar 1 "' + str(query) + '"').read()
```

#### /update
Cette route exécute la routine de mis à jour.

D'abbord la récupération des potentiels nouveaux morceaux, *updateData.py*, puis la mis à jour de l'indexer *./Indexer.jar 0 newSong.txt*.

### Parsing

#### Implémentation

Le parsing a été implémentée en Python.
Les librairies utilisées sont :
- urllib2 pour charger les pages html.
- Beautiful Soup pour le parsing html.

Le parsing se fait de la façon suivante :
- Les artistes sont accessible par url comme par exemple : http://www.boiteachansons.net/Partitions/index.php?artiste=A contient tous les artistes commencant par la lettre "A".
- Les pages contenant les artistes d'une même lettre sont ensuite parsées pour obtenir l'url contenant la liste des titres des artiste.
Exemple d'url pour l'artiste Aaron : http://www.boiteachansons.net/Artistes/Aaron.php
- La page d'un artiste est ensuite parsée pour obtenir l'url de chacun des titres.
Exemple d'url pour l'artiste Aaron pour le titre Blow : http://www.boiteachansons.net/Partitions/Aaron/Blow.php
- Les accords sont ensuite extrait et stocké dans le fichier correspondant à l'artiste en question. (fichier "A.txt" pour l'artiste Aaron par exemple.

#### Mise à jour des données

Un script python permet de mettre à jour les données. Il fonctionne sur le même principe que pour la collecte des données. Cependants, il vérifie si une chanson est présente ou pas. Si elle est déjà présente, il ne fait rien et si elle ne l'est pas, il l'ajoute dans le fichier correspondant exemple "B.txt" si l'artiste commence par "B". De plus, il ajoute le titre dans le fichier newSong.txt pour que l'indexeur puisse ajouter les nouveaux titres sans devoir tout réindexer.

L'avantage de cette méthode de mise à jour plutot que de tout reparser est que cela prend beaucoup moins de temps et aussi que si certain titre ont été supprimé entre temps, ils seront toujours enregistré dans nos données.

#### Remarques

- La lettre J pose problème avec Jordana Camelia (il y a 2x l'artiste qui pointe sur les mêmes chansons). Du coup, les titres de cette artiste sont présent 2x dans les données.

- La lettre S pose le même problème avec Joan Manuel Serrat.

#### Améliorations

- Regrouper les 2 codes python (parsing et mise à jour des données) en un seul code.

### Indexer

L'indexer a été implementé en Java en utilisant la libraire Lucene. Il faut savoir que le code Java est compilé en un .jar qui est executé par le serveur de différentes manières. Il y a 3 manières différentes d'appeler l'indexer.

#### Indexing complet

La première manière est l'indexing complet. Utilisée uniquement pour construire l'index de A à Z, elle s'appelle de la manière suivante : 
```shell
java -jar ./Indexer.jar 0 full_indexing
```
En mode construction de l'index, le programme va lire tous les fichiers contenus dans le dossier : parsing/Lettre_parsee qui a été créé par le script de parsing. Il va donc lire, ligne par ligne, toutes les chansons mise à sa disposition qu'il va stocker dans un ArrayList de chansons (classe Song). 
L'index va être ouvert en mode "Create" pour justement montrer que l'on veut faire un index complet. 
L'index utilise une classe du nom de AccordAnalyzer qui est, comme son nom l'indique, l'analyzer utilisé.

Cet analyzer contient un tokenizer "WhitespaceTokenizer" qui sert à séparer tous les accords avec des espaces (les virgules entre les accords qui ont été mises par le parser ont été remplacées par des espaces). Cela permet de récupérer tous les accords un à un. Ce tokenizer permet ensuite de pouvoir générer un TokenStream avec un ShingleFilter. Le ShingleFilter permet de récupérer tous les accords par groupe d'un certain nombre. Nous avons choisi de générer des shingles qui vont d'une taille de 2 à une taille de 8. Nous avons rajouté un autre Filter que nous avons faits par nos soins nommé AccordFilter. Ce filter s'occupe d'enlever les accords qui sont d'une taille de 1.

Ensuite, il va créer un nouveau document pour chaque chanson qui a été stockée, en mettant des champs tels que son nom, l'auteur et l'url pour y accéder. Grâce au TokenStream créé plus haut, il va ajouter un champ qui contient tous les accords de la chanson.

#### Ajout de nouvelles chansons

Pour ajouter de nouvelles chansons, la commande suivante doit être effectuée : 

```shell
java -jar ./Indexer.jar 0 <filename.txt>
```
Cela fonctionne presque de la même façon que ci-dessus, si ce n'est qu'au lieu de lire tous les fichiers, le programme se contente de lire uniquement celui passé en argument. Il suit la même logique si ce n'est qu'il ouvre l'index en mode "Append".
Le fichier passé en argument est supprimé une fois la mise à jour effectuée.

#### Recherche dans l'index

La commande pour rechercher dans l'index est la suivante : 
```shell
java -jar ./Indexer.jar 1 <query>
```
Le programme Java va effectuer une requête sur l'index grâce à un IndexSearcher qui est créé grâce à un IndexReader. La requête est simplement considérée comme une "TermQuery" et va simplement chercher dans le champ qui contient tous les accords passé en paramètre. Le résultat est une liste complète ligne par ligne de toutes les chansons dans lesquelles la suite d'accords passée en paramètre a été trouvée.

### Client

Le client est Web est une simple page HTML et JavaScript. La bibliothèque JQuery est utilisé pour nous faciliter la syntaxe des scripts.

#### Contrôle utilisateur

Un champ de type Input Text permet à l'utilisateur de taper les accords. Il peut aussi utiliser les boutons qui modifie des variables globales pour décrire l'accord courant :

```JavaScript
var note = "C";
var alteration = "";
var formation = "";
var supp = "";
var basse = "";
var basse_alteration = "";
```

L'accord actuel sélectionné est affiché comme suit :

```JavaScript
var basse_temp = basse == "" ? "" : "/" + basse + basse_alteration;
$("#chord-display").html(note + alteration + formation + supp + basse_temp);
```

#### Lecture

Pour l'écoute d'un accord, la bibliothèque est [AudioSynth](https://github.com/keithwhor/audiosynth) utilisé. Cet outil nous permet de jouer une note comme ceci :

```JavaScript
Synth.play(sound, note, octave, duration);
```
Un accord est composé de plusieurs notes, il faut donc le construire.
Si par exemple pour une note n, avec une tierce majeure, il faut jouer n et n + 4. Une tierce mineure aurait été n + 3.

Si l'utilisateur souhaite jouer les accords de sa recherche un par un, il faut alors parser cette entrée séparée par des espace.

Voici le début du parsing, ou *n* est la note et *a* l'altération (vide, # ou b).

```JavaScript
var chords = search.split(" ");
for (var i = 0; i < chords.length; i++) {
  var index_str = 0;
  var n = chords[i].charAt(index_str++);
  var a = "";
  if (chords[i].charAt(index_str) == "#" || chords[i].charAt(index_str) == "b")
    a = chords[i].charAt(index_str++);
```

Une fois chaque accord traité et décortiqué, ils sont joués à la suite avec une durée d'une seconde.

#### Recherche

Une effectuer une requête, l'objet Ajax est utilisé. Lorsqu'il reçoit des données, il les ajoute à la suite dans la table prévu à cet effet.

Chaque entrée dans la table contient un bouton pour développer des détails. Il réeffectue une requête avec le nom de la chanson comme paramètre.

Lorsque les paroles sont affichées, on indique en rouge les accords qui se trouve dans la recherche :
```JavaScript
var chords = current_search.split(" ");
for (var i = 0; i < chords.length; i++) {
  var rgxp = new RegExp(chords[i] + " ", 'g');
  var repl = '<span class="highlight">' + chords[i] + ' </span>';
  data['paroleAndAccord'] = data['paroleAndAccord'].replace(rgxp, repl);
}
```

## Contributeurs

  * Ludovic Pfeiffer
  * Brian Nydegger
  * Gérôme Pasquier


## Autres

 * Problèmes avec les caractères "/" et "#" dans l'url, la fonction javascript `encodeURIComponent` nous les converti en '%'
