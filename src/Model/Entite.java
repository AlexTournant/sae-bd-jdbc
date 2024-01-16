package Model;

import BD.CreationBD;

import java.sql.*;

public abstract class Entite {

    private int id;

    public Entite(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isInDatabase(String nomDB, String nomTable) throws SQLException {
        try (Connection connexion = CreationBD.connexionBD(nomDB);
             PreparedStatement query = connexion.prepareStatement("SELECT DISTINCT * FROM " + nomTable + " WHERE ID = ?")) {

            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            return resultSet.next();

        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la vérification de l'existence dans la base de données.", e);
        }
    }

    public abstract void ajoutBD(String nomDB);

    void ajoutEntiteCourant(Statement s, String nomTable) throws SQLException {
        try {
            ResultSet rs = s.executeQuery("SELECT MAX(id) FROM " + nomTable);
            rs.next();
            int i = rs.getInt(1) + 1;
            s.executeUpdate("INSERT INTO " + nomTable + "(id) VALUES " + Integer.toString(i));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
