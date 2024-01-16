package BD;

import java.sql.*;

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
            System.out.println("Résultat de la requête : ");
            System.out.println(instruction);
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
                + "id_projet INT NOT NULL,"
                + "sujet VARCHAR(100) NOT NULL,"
                + "technologies_utilisees VARCHAR(100) NOT NULL,"
                + "date_debut DATE NOT NULL,"
                + "date_fin DATE NOT NULL,"
                + "PRIMARY KEY(id_projet))");

        creationTable(NOMDB, "CREATE TABLE Membre(" +
                "id_membre INT NOT NULL," +
                "nom VARCHAR(100)," +
                "prenom VARCHAR(100)," +
                "est_responsable BOOLEAN NOT NULL," +
                "PRIMARY KEY(id_membre))");

        creationTable(NOMDB, "CREATE TABLE ProjetMembre(" +
                "id_projet INT NOT NULL," +
                "id_membre INT NOT NULL," +
                "est_responsable BOOLEAN NOT NULL," +
                "PRIMARY KEY(id_projet, id_membre)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id_projet)," +
                "FOREIGN KEY(id_membre) REFERENCES Membre(id_membre))");

        creationTable(NOMDB, "CREATE TABLE Objectif(" +
                "id_objectif INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "description VARCHAR(1000) NOT NULL," +
                "est_realise BOOLEAN NOT NULL," +
                "PRIMARY KEY(id_objectif)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id_projet))");

        creationTable(NOMDB, "CREATE TABLE AvancementObjectif(" +
                "id_avancement INT NOT NULL," +
                "id_objectif INT NOT NULL," +
                "date_mise_a_jour DATE NOT NULL," +
                "avancement VARCHAR(15) CHECK (avancement = 'En cours' OR avancement = 'Terminé' OR avancement = 'A faire') NOT NULL," +
                "PRIMARY KEY(id_avancement)," +
                "FOREIGN KEY(id_objectif) REFERENCES Objectif(id_objectif))");

        creationTable(NOMDB, "CREATE TABLE RessourcesMaterielles(" +
                "id_ressource_mat INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "nom_ressource_mat VARCHAR(100) NOT NULL," +
                "quantite INT NOT NULL," +
                "PRIMARY KEY(id_ressource_mat)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id_projet))");

        creationTable(NOMDB, "CREATE TABLE RessourcesLogicielles(" +
                "id_ressource_log INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "nom_ressource_log VARCHAR(100)," +
                "version VARCHAR(100) NOT NULL," +
                "PRIMARY KEY(id_ressource_log)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id_projet))");

        creationTable(NOMDB, "CREATE TABLE CommunicationForum(" +
                "id_message INT NOT NULL," +
                "id_projet INT NOT NULL," +
                "id_membre_envoyeur INT NOT NULL," +
                "contenu VARCHAR(5000) NOT NULL," +
                "date_envoi DATE NOT NULL," +
                "PRIMARY KEY(id_message)," +
                "FOREIGN KEY(id_projet) REFERENCES Projet(id_projet)," +
                "FOREIGN KEY(id_membre_envoyeur) REFERENCES Membre(id_membre))");

        creationTable(NOMDB, "CREATE TABLE CommunicationPrivee(" +
                "id_message INT NOT NULL," +
                "id_membre_envoyeur INT NOT NULL," +
                "id_membre_destinataire INT NOT NULL," +
                "contenu VARCHAR(5000) NOT NULL," +
                "date_envoi DATE NOT NULL," +
                "PRIMARY KEY(id_message)," +
                "FOREIGN KEY(id_membre_envoyeur) REFERENCES Membre(id_membre)," +
                "FOREIGN KEY(id_membre_destinataire) REFERENCES Membre(id_membre))");
    }
}
