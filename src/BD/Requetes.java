package BD;

import java.sql.*;

public class Requetes {

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
            String sql = "SELECT Membre.id_membre, Membre.nom, Membre.prenom, ProjetMembre.est_responsable " +
                    "FROM Membre " +
                    "JOIN ProjetMembre ON Membre.id_membre = ProjetMembre.id_membre " +
                    "WHERE ProjetMembre.id_projet = ?";

            try (PreparedStatement statement = connexion.prepareStatement(sql)) {
                statement.setInt(1, idProjet);
                ResultSet resultSet = statement.executeQuery();

                System.out.println("Membres associés au projet avec ID " + idProjet + " :");
                while (resultSet.next()) {
                    int idMembre = resultSet.getInt("id_membre");
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
		try {
            connexion = CreationBD.connexionBD(nomDB);
            // Reste a crypter le mot de passe
            var query = connexion.prepareStatement("SELECT id_membre FROM Auth WHERE Auth.nom_utilisateur = "+ name + " AND Auth.mdp = "+ password);
            ResultSet res = query.executeQuery();
            int id = 0;
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
            var query = connexion.prepareStatement("SELECT DISTINCT id_objectif, est_realise FROM Objectif WHERE id_projet = " + idProjet);
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
}
