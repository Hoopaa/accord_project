
 * Sources des données : http://www.boiteachansons.net/Partitions/index.php
 * Nous avons 2800 artistes et 16000 titres
 * Stockage des données : Les données sont stockée dans des fichiers ".txt". Il y a un total de 27 fichier. Chaque fichier contient les
 artistes commencant par la lettre du nom du fichier. Ainse le fichier "A.txt" va contenir tous les artistes commencant par la 
 lettre A comme Axelle Red par exemple. Le 27ème fichier contient les artistes qui ne commence pas par une lettre mais généralement 
 par un chiffre ou autre comme 1789 les amants de la Bastille par exemple. Ce fichier se nomme "9.txt"
 * Format des données : Une chanson représente une ligne dans un fichier ".txt" (se terminant par "\n").
 une ligne se présente sous la forme suivante :
 "nom_de_l'artiste",titre_de_la_chanson","url_de_la_chanson",["accord_1","accord_2",...,"accord_n"]
 tous les champs sont séparté par des "," et les accord sont encadré de "[" "]"
