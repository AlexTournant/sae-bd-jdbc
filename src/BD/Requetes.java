package BD;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Requetes {

    public static List<Objectif> getObjectifsNonRealisesParProjet(String nomDB, int idProjet) {
        List<Objectif> objectifsRealises = new ArrayList<>();

        try (Connection connexion = CreationBD.connexionBD(nomDB)) {
            String sql = "SELECT * FROM Objectif WHERE id_projet = ? AND est_realise = false";
            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int idObjectif = resultSet.getInt("id");
                        String description = resultSet.getString("description");
                        boolean estRealise = resultSet.getBoolean("est_realise");

                        Objectif objectif = new Objectif(idObjectif, idProjet, description, estRealise);
                        objectifsRealises.add(objectif);

                        System.out.println(objectif.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objectifsRealises;
    }

    public static List<Objectif> getObjectifsRealisesParProjet(String nomDB, int idProjet) {
        List<Objectif> objectifsRealises = new ArrayList<>();

        try (Connection connexion = CreationBD.connexionBD(nomDB)) {
            String sql = "SELECT * FROM Objectif WHERE id_projet = ? AND est_realise = true";
            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int idObjectif = resultSet.getInt("id");
                        String description = resultSet.getString("description");
                        boolean estRealise = resultSet.getBoolean("est_realise");

                        Objectif objectif = new Objectif(idObjectif, idProjet, description, estRealise);
                        objectifsRealises.add(objectif);

                        System.out.println(objectif.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objectifsRealises;
    }


    public static void deleteMember(String nomDB, int idMembre, int idProjet) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "DELETE FROM ProjetMembre WHERE id_projet = ? AND id_membre = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                statement.setInt(2, idMembre);

                int lines = statement.executeUpdate();

                if (lines > 0) {
                    System.out.println("Attribution du membre au projet supprimée avec succès.");
                } else {
                    System.out.println("Aucune attribution correspondante trouvée.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static void addMember(String nomDB, int idProjet, int idMembre, boolean estResponsable) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "INSERT INTO ProjetMembre (id_projet, id_membre, est_responsable) VALUES (?, ?, ?)";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                statement.setInt(2, idMembre);
                statement.setBoolean(3, estResponsable);

                int lines = statement.executeUpdate();

                if (lines > 0) {
                    System.out.println("Membre ajouté au projet avec succès.");
                } else {
                    System.out.println("Erreur lors de l'ajout du membre au projet.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static void afficherMembresDuProjet(String nomDB, int idProjet) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "SELECT Membre.id, Membre.nom, Membre.prenom, ProjetMembre.est_responsable " +
                    "FROM Membre " +
                    "JOIN ProjetMembre ON Membre.id = ProjetMembre.id_membre " +
                    "WHERE ProjetMembre.id_projet = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                ResultSet resultSet = statement.executeQuery();

                System.out.println("Membres associés au projet avec ID " + idProjet + " :");
                while (resultSet.next()) {
                    int idMembre = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    boolean estResponsable = resultSet.getBoolean("est_responsable");
                    System.out.println("ID Membre: " + idMembre + ", Nom: " + nom + ", Prénom: " + prenom + ", Responsable: " + estResponsable);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static void passerMembreResponsable(String nomDB, int idProjet, int idMembre) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "UPDATE ProjetMembre SET est_responsable = true " +
                    "WHERE id_projet = ? AND id_membre = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                statement.setInt(2, idMembre);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Le membre avec l'ID " + idMembre + " est désormais responsable du projet avec l'ID " + idProjet);
                } else {
                    System.out.println("Aucune modification effectuée. Vérifiez l'existence du projet et du membre.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

	public static int getIdMembreConnecte(String nomDB, String name, String password) throws SQLException{
		Connection connexion = null;
        int id = -1;
		try {
            connexion = CreationBD.connexionBD(nomDB);
            // Reste a crypter le mot de passe
            String query = "SELECT id_membre FROM Authentification WHERE Authentification.nom_utilisateur = ? AND Authentification.mdp = ?";
            try (PreparedStatement statement = connexion.prepareStatement(query)) {
                statement.setString(1,name);
                statement.setString(2,password);
                ResultSet res = statement.executeQuery();
                try {
                    res.next();
                    id = res.getInt("id_membre");
                    if (id > 0) {
                        String sql = "UPDATE Authentification SET est_connecte = true " +
                                "WHERE id_membre = ?";

                        try (PreparedStatement statement2 = connexion.prepareStatement(sql)) {
                            statement2.setInt(1, id);
                        }
                    }
                } finally {
                    return id;
                }
            }
            catch(Exception e) {
            	e.printStackTrace();
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Nom d'utilisateur ou mot de passe incorrect");
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return -1;
	}

	public static boolean updateAvancementObjectif(String nomBD, String avance, int id_obj) throws SQLException{
		Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomBD);
        	if (avance == "En cours" || avance == "Terminé" || avance == "A faire") {
        		var query = connexion.prepareStatement("UPDATE AvancementObjectif SET AvancementObjectif.avancement = " +avance + " WHERE AvancementObjectif.id_objectif = "+id_obj);
        		query.executeUpdate();
        		return true;
        	}
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return false;
    }
	
	public static void etatActuelProjet(String nomBD, int idProjet) {
		Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomBD);
            var query = connexion.prepareStatement("SELECT DISTINCT id, est_realise FROM Objectif WHERE id_projet = " + idProjet);
            ResultSet res = query.executeQuery();
            while(res.next()){
                System.out.println(res.getInt("id") + " " + res.getBoolean("est_realise"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
	}
		
    public static void afficherContenuTable(String nomDB, String nomTable) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "SELECT * FROM " + nomTable;

            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                int colonneCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    for (int i = 1; i <= colonneCount; i++) {
                        String nomColonne = metaData.getColumnName(i);
                        Object valeurColonne = resultSet.getObject(i);
                        System.out.println(nomColonne + ": " + valeurColonne);
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }

    public static Projet allProjectId(int id, String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "select * from Projet where Projet.id=" + Integer.toString(id);
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve the value of the column "nom_projet" for each row
                int id_projet=resultSet.getInt("id");
                String nomProjet = resultSet.getString("nom_projet");
                String sujet = resultSet.getString("sujet");
                String technologies_utilisees = resultSet.getString("technologies_utilisees");
                Date date_debut = resultSet.getDate("date_debut");
                Date date_fin = resultSet.getDate("date_fin");
                return new Projet(id_projet,nomProjet,sujet,technologies_utilisees,date_debut,date_fin, true);
            }
            return null;
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
    public static List<Projet> allProjectPublic(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            List<Projet> tab = new ArrayList<>();
            String sql = "select * from Projet where Projet.estPublic=true";
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id_projet=resultSet.getInt("id");
                String nomProjet = resultSet.getString("nom_projet");
                String sujet = resultSet.getString("sujet");
                String technologies_utilisees = resultSet.getString("technologies_utilisees");
                Date date_debut = resultSet.getDate("date_debut");
                Date date_fin = resultSet.getDate("date_fin");
                Boolean estPublic = resultSet.getBoolean("estPublic");
                tab.add(new Projet(id_projet,nomProjet,sujet,technologies_utilisees,date_debut,date_fin, estPublic));
            }
            return tab;
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
    public static List<RessourcesLogicielles> allResourceLogicielProjet(int id,String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            List<RessourcesLogicielles> tab = new ArrayList<>();
            String sql = "select * from Projet join RessourcesLogicielles on Projet.id=RessourcesLogicielles.id_projet where Projet.id="+Integer.toString(id);
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve the value of the column "nom_projet" for each row
                int id_r=resultSet.getInt("id");
                int id_p=resultSet.getInt("id_projet");
                String nom_ressource_log = resultSet.getString("nom_ressource_log");
                String version = resultSet.getString("version");
                tab.add (new RessourcesLogicielles(id_r,id_p,nom_ressource_log,version));
            }
            return tab;
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
    public static List<RessourcesMaterielles> allResourceMaterielleProjet(int id,String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            List<RessourcesMaterielles> tab = new ArrayList<>();
            String sql = "select * from Projet join RessourcesMaterielles on Projet.id=RessourcesMaterielles.id_projet where Projet.id="+Integer.toString(id);
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve the value of the column "nom_projet" for each row
                int id_r=resultSet.getInt("id");
                int id_p=resultSet.getInt("id_projet");
                String nom_ressource_log = resultSet.getString("nom_ressource_mat");
                int quantite = resultSet.getInt("quantite");
                tab.add (new RessourcesMaterielles(id_r,id_p,nom_ressource_log,quantite));
            }
            return tab;
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    public static List<String> allNameProjectMembre(int id, String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            List<String> tab = new ArrayList<>();
            String sql = "select nom_projet from Membre join ProjetMembre on Membre.id=ProjetMembre.id_membre join Projet on ProjetMembre.id_projet=Projet.id where Projet.id=" + Integer.toString(id);
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                    while(resultSet.next()) {
                        // Retrieve the value of the column "nom_projet" for each row
                        String nomProjet = resultSet.getString("nom_projet");

                        // Do something with the retrieved value
                        tab.add(nomProjet);
                    }
                    return tab;
        } finally {

        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean EnvoieMessagePrive(int idExpediteur,String message ,int idDestinataire,String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            List<String> tab = new ArrayList<>();
            String sql = "insert value into CommunicationPrivee where Projet.id=" + Integer.toString(idProjet) + "";
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve the value of the column "nom_projet" for each row
                    String nomProjet = resultSet.getString("nom_projet");

                    // Do something with the retrieved value
                    tab.add(nomProjet);
                }
                System.out.println("le message a été envoyer");
                return true;
            }
        } finally {

            }
    }

    public static boolean EnvoieMessageForum(int idProjet,int idExpediteur,String message,String nomDB) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            List<String> tab = new ArrayList<>();
            String sql = "insert value into CommunicationPrivee where Projet.id=" + Integer.toString(idProjet) + "";
            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Retrieve the value of the column "nom_projet" for each row
                    String nomProjet = resultSet.getString("nom_projet");

                    // Do something with the retrieved value
                    tab.add(nomProjet);
                }
                System.out.println("le message a été envoyer");
                return true;
            }
        } finally {

        }
    }
}
