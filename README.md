# ProjetJEE2017

Projet de webservices M2 MIAGE NTDP

C’est une application de gestion de projet, dans la même lignée que trello.

Description détaillée des fonctionnalités
Chaque utilisateur peut s’affecter ou être affecté à une tâche ou s’en dissocier. Lors de la validation de la tâche, il doit indiquer le nombre d’heures qu’il a passé dessus.
Chaque utilisateur peut créer une tâche : il renseigne alors un titre et une description. La tâche est créée dans la section “TO DO”. Elle peut être déplacée vers “IN PROGRESS”, vers “DONE” ou vers “VALIDATED”.
Chaque classe a ses opérations de CRUD.

IDE : Eclipse
Base de données : MySQL


Afin que le projet fonctionne, veuillez vérifier les points suivants :

- Importer le fichier "db_projet.sql" que vous trouverez dans l'architecture du git sur localhost/phpmyadmin dans une base de données nommée "project",

- Dans le fichier persistence.xml : vérifiez si il y a bien ProjetJEEPU en Persistence Unit Name et changez éventuellement les
valeurs du login et du password pour pouvoir vous connecter à la base MySQL.

- Si un login et un mot de passe vous sont demandés lors du lancement du serveur Tomcat, les valeurs sont :

login: ide // 
password: guUhlfe1
