package BD;

import java.sql.*;

public class Requetes {

	
	public static int getIdMembreConnecte(String nomDB, String name, String password) throws SQLException{
		Connection connexion = null;
		try {
            connexion = CreationBD.connexionBD(nomDB);
            // Reste a crypter le mot de passe
            var query = connexion.prepareStatement("SELECT id_membre FROM Auth WHERE Auth.nom_utilisateur = "+ name + " AND Auth.mdp = "+ password);
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

	public static boolean updateAvancementObjectif(String nomBD, String avance, int id_obj) throws SQLException{
		Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
        	if (avance == 'En cours' || avance == 'Terminé' || avance == 'A faire') {
        		var query = connexion.prepareStatement("UPDATE AvancementObjectif SET AvancementObjectif.avancement = "avance + " WHERE AvancementObjectif.id_objectif = "+id_obj);
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
            connexion = CreationBD.connexionBD(nomDB);
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
