package BD;

import java.sql.*;
import Model.*;
public class CreationBD {

    public static final String NOMDB = "DB-SAE";

    public static void createDB(String nom) {
        String dbURL = "jdbc:derby:" + nom +";create=true";
        Connection c = null;
        try {
            c = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problème lors de la création de la base de données");
        }
        finally {
            fermerConnexion(c);
        }
    }

    public static Connection connexionBD(String nom) throws SQLException{
        String dbURL = "jdbc:derby:" + nom;
        return DriverManager.getConnection(dbURL);
    }


    public static void fermerConnexion(Connection c) {
        try {
            if (c != null) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problème lors de la fermeture de la base de données");
        }
    }


    public static void creationTable(String nom, String instruction) {
        Connection connexion = null;
        Statement requete = null;
        try {

            connexion = connexionBD(nom);
            requete = connexion.createStatement();
            int res = requete.executeUpdate(instruction);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problème lors de l'exécution de la requête");
        } finally {
            fermerConnexion(connexion);
        }
    }




    public static void main(String[] args) throws SQLException {

        createDB(NOMDB);



        creationTable(NOMDB,"CREATE TABLE Projet("
                + "id INT NOT NULL," +
                "nom_projet VARCHAR(50) ,"
                + "sujet VARCHAR(100) ,"
                + "technologies_utilisees VARCHAR(50) ,"
                + "date_debut DATE ,"
                + "date_fin DATE,"
                + "PRIMARY KEY(id))");

        creationTable(NOMDB, "CREATE TABLE Membre(" +
                "id INT NOT NULL," +
                "nom VARCHAR(100)," +
                "prenom VARCHAR(100)," +
                "PRIMARY KEY(id))");

        creationTable(NOMDB, "CREATE TABLE ProjetMembre(" +
                "id_projet INT NOT NULL," +
                "id_membre INT NOT NULL," +
                "est_responsable BOOLEAN ," +
                "PRIMARY KEY(id_projet, id_membre)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id)," +
                "FOREIGN KEY(id_membre) REFERENCES Membre(id))");

        creationTable(NOMDB, "CREATE TABLE Objectif(" +
                "id INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "description VARCHAR(1000) ," +
                "est_realise BOOLEAN ," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id))");

        creationTable(NOMDB, "CREATE TABLE AvancementObjectif(" +
                "id INT NOT NULL," +
                "id_objectif INT NOT NULL," +
                "date_mise_a_jour DATE ," +
                "avancement VARCHAR(15) CHECK (avancement = 'En cours' OR avancement = 'Terminé' OR avancement = 'A faire') ," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_objectif) REFERENCES Objectif(id))");

        creationTable(NOMDB, "CREATE TABLE RessourcesMaterielles(" +
                "id INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "nom_ressource_mat VARCHAR(100) ," +
                "quantite INT ," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id))");

        creationTable(NOMDB, "CREATE TABLE RessourcesLogicielles(" +
                "id INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "nom_ressource_log VARCHAR(100)," +
                "version VARCHAR(100) ," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id))");

        creationTable(NOMDB, "CREATE TABLE CommunicationForum(" +
                "id INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "id_membre_envoyeur INT NOT NULL," +
                "contenu VARCHAR(5000) ," +
                "date_envoi DATE ," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id)," +
                "FOREIGN KEY(id_membre_envoyeur) REFERENCES Membre(id))");

        creationTable(NOMDB, "CREATE TABLE CommunicationPrivee(" +
                "id INT NOT NULL," +
                "id_membre_envoyeur INT NOT NULL," +
                "id_membre_destinataire INT NOT NULL," +
                "contenu VARCHAR(5000) ," +
                "date_envoi DATE ," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_membre_envoyeur) REFERENCES Membre(id)," +
                "FOREIGN KEY(id_membre_destinataire) REFERENCES Membre(id))");

        creationTable(NOMDB, "CREATE TABLE Document(" +
                "id INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "id_membre_upload INT NOT NULL," +
                "chemin_document VARCHAR(150)," +
                "contenu BLOB," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY (id_projet) REFERENCES Projet(id)," +
                "FOREIGN KEY (id_membre_upload) REFERENCES Membre(id))");

        creationTable(NOMDB, "CREATE TABLE Authentification(" +
                "id INT NOT NULL," +
                "id_membre INT NOT NULL," +
                "nom_utilisateur VARCHAR(50) DEFAULT 'Anonyme' ," +
                "mdp VARCHAR(64) ," +
                "est_connecte BOOLEAN ," +
                "est_admin BOOLEAN  DEFAULT false," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(id_membre) REFERENCES Membre(id))");



        Projet projet1 = new Projet(1, "SAE de BD","Faire une BD avec Derby", "SQL, Java", new Date(2024-1900, 1, 16), new Date(2025-1900,7,7));
        Projet projet2 = new Projet(1, "SAE de BD2","Faire une BD avec Derby", "SQL, Java", new Date(2024-1900, 1, 16), new Date(2025-1900,7,7));
        Projet projet3 = new Projet(1, "SAE de BD3","Faire une BD avec Derby", "SQL, Java", new Date(2024-1900, 1, 16), new Date(2025-1900,7,7));

        Membre membre1 = new Membre(1, "DEMORY", "Maël");
        Membre membre2 = new Membre(2, "TOURNANT", "Alex");
        Membre membre3 = new Membre(3, "BALLET", "Dylan");
        Membre membre4 = new Membre(4, "FOURNIER", "Corentin");

        ProjetMembre pm1 = new ProjetMembre(1, 1, false);
        ProjetMembre pm2 = new ProjetMembre(1, 2, false);
        ProjetMembre pm3 = new ProjetMembre(1, 3, true);
        ProjetMembre pm4 = new ProjetMembre(1, 4, false);

        Objectif o1 = new Objectif(1, 1, "Test 1 pour l'objectif 1", false);
        Objectif o2 = new Objectif(1, 1, "Test 2 pour l'objectif 1", true);
        Authentification auth=new Authentification(1,1,"alex","tournant",false,false);


        projet1.ajoutBD(NOMDB);
        projet2.ajoutBD(NOMDB);
        projet3.ajoutBD(NOMDB);
        membre1.ajoutBD(NOMDB);
        membre2.ajoutBD(NOMDB);
        membre3.ajoutBD(NOMDB);
        membre4.ajoutBD(NOMDB);
        pm1.ajoutBD(NOMDB);
        pm2.ajoutBD(NOMDB);
        pm3.ajoutBD(NOMDB);
        pm4.ajoutBD(NOMDB);

        o1.ajoutBD(NOMDB);
        o2.ajoutBD(NOMDB);

        Requetes.deleteMember(NOMDB,3, 1);
        Requetes.passerMembreResponsable(NOMDB, 1, 1);

        Requetes.afficherContenuTable(NOMDB, "Projet");
        Requetes.afficherContenuTable(NOMDB, "Membre");
        Requetes.afficherContenuTable(NOMDB, "ProjetMembre");
        Requetes.afficherContenuTable(NOMDB, "Objectif");

        Requetes.getObjectifsNonRealisesParProjet(NOMDB, 1);
        Requetes.getObjectifsRealisesParProjet(NOMDB, 1);
        auth.ajoutBD(NOMDB);

    }
}
