# ProjetJEE2017

Projet de webservices M2 MIAGE NTDP

C’est une application de gestion de projet, dans la même lignée que trello.

Chaque utilisateur peut s’affecter ou être affecté à une tâche ou s’en dissocier. Lors de la validation de la tâche, il doit indiquer le nombre d’heures qu’il a passé dessus.
Chaque utilisateur peut créer une tâche : il renseigne alors un titre et une description. La tâche est créée dans la section “TO DO”. Elle peut être déplacée vers “IN PROGRESS”, vers “DONE” ou vers “VALIDATED”.
Chaque classe a ses opérations de CRUD.

IDE : Netbeans 8.2
Serveur : Apache Tomcat 8.0.27 
Base de données : MySQL


Afin que le projet fonctionne, veuillez vérifier les points suivants :

- Importer le fichier "db_projet.sql" que vous trouverez dans l'architecture du git sur localhost/phpmyadmin dans une base de données nommée "project",

- Dans le fichier persistence.xml : vérifiez si il y a bien ProjetJEEPU en Persistence Unit Name et changez éventuellement les
valeurs du login et du password pour pouvoir vous connecter à la base MySQL.

- Si un login et un mot de passe vous sont demandés lors du lancement du serveur Tomcat, les valeurs sont :

login: ide // 
password: guUhlfe1

Un set de données d'exemple est disponible à la racine du projet "exemples.txt" dont les lignes peuvent être lancées depuis Postman (tout en prenant soin d'adapter le type de requête : GET pour de l'affichage, PUT pour de la création, POST pour de l'update et DELETE pour de la suppression).
