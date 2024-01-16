package BD;

import java.sql.*;

public class CreationBD {
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

        createDB("DB-SAE");
        creationTable("DB-SAE","CREATE TABLE Test("
                + "id INT NOT NULL,"
                + "nom VARCHAR(100) NOT NULL,"
                + "prenom VARCHAR(100) NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "PRIMARY KEY(id))");



    }
}
