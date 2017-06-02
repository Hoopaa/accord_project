
# Parsing

## Sources des données

Toutes les données proviennent du site http://www.boiteachansons.net/Partitions/index.php

## Quantité de données

Le sites boite à chansons contient environs 2800 artistes et 14500 titres. Ce qui représente un peu moins de 4 Mo de données stockées une fois le parsing effectué. Cependant, la quantité de donnée peu évoluer si des chansons sont ajoutées au site boite à chanson.

## Stockage des données

Les données sont stockées dans des fichiers ".txt". Il y a un total de 27 fichier. Chaque fichier contient les artistes commencant par la lettre du nom du fichier. Ainse le fichier "A.txt" va contenir tous les artistes commencant par la lettre A comme Axelle Red par exemple. Le 27ème fichier contient les artistes qui ne commence pas par une lettre mais généralement par un chiffre ou autre comme 1789 les amants de la Bastille par exemple. Ce fichier se nomme "9.txt"

## Format des données stockées

Une chanson représente une ligne dans un fichier ".txt" (se terminant par "\n").
Une ligne se présente sous la forme suivante :

_nom_de_l'artiste_,_titre_de_la_chanson_,_url_de_la_chanson_,[_accord_1_,_accord_2_,...,_accord_n_]

Tous les champs sont séparté par des "," et les accord sont encadré de "[" "]"

## Prétraitement

Il y a pas vraiment de prétraitement qui ont été effectué sur les données ormis de l'extration.
Cependant, certain titres (paroles et accords) ont été retiré du site boite à chanson en raison des droits d'auteurs. Mais le titre est toujours présent sur le site boite à chanson. Donc si le titre est présent mais que les accords et les paroles ne sont pas présent, le titre en question n'est pas stocké.

## Implémentation

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

## Mise à jour des données

Un script python permet de mettre à jour les données. Il fonctionne sur le même principe que pour la collecte des données. Cependants, il vérifie si une chanson est présente ou pas. Si elle est déjà présente, il ne fait rien et si elle ne l'est pas, il l'ajoute dans le fichier correspondant exemple "B.txt" si l'artiste commence par "B". De plus, il ajoute le titre dans le fichier newSong.txt pour que l'indexeur puisse ajouter les nouveaux titres sans devoir tout réindexer.

L'avantage de cette méthode de mise à jour plutot que de tout reparser est que cela prend beaucoup moins de temps et aussi que si certain titre ont été supprimé entre temps, ils seront toujours enregistré dans nos données.

## Remarques

- La lettre J pose problème avec Jordana Camelia (il y a 2x l'artiste qui pointe sur les mêmes chansons). Du coup, les titres de cette artiste sont présent 2x dans les données.

- La lettre S pose le même problème avec Joan Manuel Serrat.

## Améliorations


