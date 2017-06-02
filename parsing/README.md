
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

## Implémentation

Le parsing a été implémentée en Python. 
Les librairies utilisées sont :
 - urllib2 pour charger les pages html.
 - Beautiful Soup pour le parsing html.



## remarque
lettre J problème avec Jordana Camelia (il y a 2x l'artiste qui pointe sur les mêmes chansons)

lettre s problème avec Joan Manuel Serrat
