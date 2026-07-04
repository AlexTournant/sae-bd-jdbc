package Model;

import BD.CreationBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Objectif extends Entite {

    private int idProjet;
    private String description;
    private boolean estRealise;

    public Objectif(int id, int idProjet, String description, boolean estRealise) {
        super(id);
        this.idProjet = idProjet;
        this.description = description;
        this.estRealise = estRealise;
    }

    @Override
    public String toString() {
        return "Objectif{" +
                "idProjet=" + idProjet +
                ", description='" + description + '\'' +
                ", estRealise=" + estRealise +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "INSERT INTO Objectif (id, id_projet, description, est_realise) VALUES (?, ?, ?, ?)";

            try (PreparedStatement s = connexion.prepareStatement(sql)) {
                s.setInt(1, getId(nomDB, "Objectif"));
                s.setInt(2, idProjet);
                s.setString(3, description);
                s.setBoolean(4, estRealise);

                s.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}