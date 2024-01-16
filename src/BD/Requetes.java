package BD;

import java.sql.*;

public class Requetes {

	
	public static int getIdMembreConnecte(String nomDB, String name, String password) throws SQLException{
		Connection connexion = null;
		try {
            connexion = CreationBD.connexionBD(nomDB);
            // Reste a crypter le mot de passe
            var query = connexion.prepareStatement("SELECT id_membre FROM Auth WHERE Auth.nom = "+ name + " AND Auth.mdp = "+ password);
            ResultSet res = query.executeQuery();
            int id = null;
            try {
                id = res.getInt("id_membre");
            }
            catch(Exception e) {
            	e.printStackTrace();
            }
            return id;
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
	}

/* Exemples
    public static void nosClients(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT DISTINCT * FROM Client");
            ResultSet res = query.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("id") + " " + res.getString("nom") + " " + res.getString("prenom") + " " + res.getInt("age"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static void nosClientsAdultes(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT DISTINCT * FROM Client WHERE age>=18");
            ResultSet res = query.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("id") + " " + res.getString("nom") + " " + res.getString("prenom") + " " + res.getInt("age"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }


    public static void nosEmprunteurs(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT DISTINCT * FROM Client INNER JOIN Pret ON Client.id=Pret.idC");
            ResultSet res = query.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("id") + " " + res.getString("nom") + " " + res.getString("prenom") + " " + res.getInt("age"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static int nombrePrets(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT COUNT(*) AS nb FROM Pret");
            ResultSet res = query.executeQuery();
            while(res.next()){
                return res.getInt("nb");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return 0;
    }

    public static int nombreClients(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT COUNT(*) AS nb FROM Client");
            ResultSet res = query.executeQuery();
            while(res.next()){
                return res.getInt("nb");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return 0;
    }

    public static int nombreProprios(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT COUNT(*) AS nb FROM Proprietaire");
            ResultSet res = query.executeQuery();
            while(res.next()){
                return res.getInt("nb");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return 0;
    }

    public static int ageMax(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT MAX(age) AS ageMax FROM Client");
            ResultSet res = query.executeQuery();
            while(res.next()){
                return res.getInt("ageMax");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return 0;
    }

    public static void clientPlusAges(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT * FROM Client WHERE Client.age = (SELECT MAX(age) FROM Client)");
            ResultSet res = query.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("id") + " " + res.getString("nom") + " " + res.getString("prenom") + " " + res.getInt("age"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static boolean rapportCV(String nomDB) throws SQLException {
        int nbClients = nombreClients(nomDB);
        int nbProprios = nombreProprios(nomDB);

        return nbClients > nbProprios;

    }


    public static boolean augmenteCout(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("UPDATE Article SET cout = cout * 1.10");
            query.executeUpdate();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return false;
    }


    public static void listeArticles(String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT DISTINCT * FROM Article");
            ResultSet res = query.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("id") + " " + res.getString("nomA") + " " + res.getDouble("cout"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
 */
}
