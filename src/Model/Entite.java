package Model;

import BD.CreationBD;

import java.sql.*;
import java.util.Map;

public abstract class Entite {

    private int id;

    public Entite(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isInDatabase(String nomDB, String nomTable) throws SQLException {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            var query = connexion.prepareStatement("SELECT DISTINCT * FROM " + nomTable + " WHERE id = ?");
            query.setInt(1, id);
            var resultSet = query.executeQuery();
            return resultSet.next();
        } catch (SQLException e){
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    public abstract void ajoutBD(String nomDB) throws SQLException;

    public static int getId(String nomDB, String nomTable) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "SELECT COUNT(*) FROM " + nomTable;

            try (PreparedStatement statement = connexion.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
        return 0;
    }
}
