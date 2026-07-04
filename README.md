# SAE BD JDBC

Application console Java permettant de gérer des projets (membres, objectifs, ressources, communications) via une base de données **Apache Derby** embarquée, accédée en **JDBC**.

Projet réalisé dans le cadre d'une SAE (BUT Informatique).

## Stack technique

- Java (JDK)
- JDBC
- Apache Derby (base de données embarquée, mode `embedded`)

## Structure du projet

```
src/
├── main/
│   └── Main.java          # Point d'entrée : menu console interactif
├── BD/
│   ├── CreationBD.java    # Connexion, création de la base et des tables
│   ├── RemplissageBD.java # Insertion de données de démonstration
│   └── Requetes.java      # Requêtes SQL (CRUD, listings, authentification...)
└── Model/
    ├── Entite.java              # Classe abstraite commune aux entités
    ├── Membre.java
    ├── Projet.java
    ├── ProjetMembre.java
    ├── Objectif.java
    ├── AvancementObjectif.java
    ├── RessourcesMaterielles.java
    ├── RessourcesLogicielles.java
    ├── CommunicationForum.java
    ├── CommunicationPrivee.java
    ├── Document.java
    └── Authentification.java
```

## Modèle de données

La base (`DB-SAE`) est créée automatiquement au premier lancement (`CreationBD.createDB`) avec les tables suivantes :

- **Projet** : nom, sujet, technologies utilisées, dates, visibilité publique
- **Membre** : nom, prénom
- **ProjetMembre** : association membre/projet, avec indicateur de responsable
- **Objectif** : objectifs liés à un projet, réalisés ou non
- **AvancementObjectif** : historique d'avancement d'un objectif (`En cours`, `Terminé`, `A faire`)
- **RessourcesMaterielles** / **RessourcesLogicielles** : ressources allouées à un projet
- **CommunicationForum** / **CommunicationPrivee** : messages liés à un projet ou entre membres
- **Document** : fichiers liés à un projet (BLOB)
- **Authentification** : identifiants de connexion d'un membre

## Lancer le projet

Le projet est configuré comme un module IntelliJ IDEA (`SAE BD JDBC.iml`), sans gestionnaire de build (Maven/Gradle).

1. Ouvrir le dossier dans IntelliJ IDEA.
2. Vérifier que le driver Derby (fourni dans `db-derby-10.16.1.1-bin/lib`) est bien dans le classpath du module.
3. Exécuter `src/main/Main.java`.

Au lancement, la base `DB-SAE` est créée si elle n'existe pas encore (fichiers générés localement, non versionnés).

## Fonctionnalités du menu principal

- Connexion / inscription d'un membre
- Consultation des projets publics ou de ses propres projets
- Création d'un projet
- Pour un projet sélectionné : liste des membres, des objectifs, des ressources matérielles/logicielles, envoi de messages (forum / privé)

## Auteurs

- DEMORY Maël
- TOURNANT Alex
- BALLET Dylan
- FOURNIER Corentin
