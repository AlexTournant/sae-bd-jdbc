package Model;

import BD.CreationBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ProjetMembre {
    private int idProjet;
    private int idMembre;
    private boolean estResponsable;

    public ProjetMembre(int idProjet, int idMembre, boolean estResponsable) {
        this.idProjet = idProjet;
        this.idMembre = idMembre;
        this.estResponsable = estResponsable;
    }

    @Override
    public String toString() {
        return "ProjetMembre{" +
                "idProjet=" + idProjet +
                ", idMembre=" + idMembre +
                ", estResponsable=" + estResponsable +
                '}';
    }

    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "INSERT INTO ProjetMembre(id_projet, id_membre,est_responsable) VALUES (?, ?, ?)";

            try (PreparedStatement s = connexion.prepareStatement(sql)) {
                s.setInt(1, idProjet);
                s.setInt(2, idMembre);
                s.setBoolean(3, estResponsable);
                s.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}
