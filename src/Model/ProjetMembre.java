package Model;

import BD.CreationBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ProjetMembre extends Entite {
    private int idProjet;
    private int idMembre;
    private boolean estResponsable;

    public ProjetMembre(int id, int idProjet, int idMembre, boolean estResponsable) {
        super(id);
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
            if (!isInDatabase(nomDB, "ProjetMembre")) {
                String sql = "INSERT INTO ProjetMembre(idProjet, idMembre,estResponsable) VALUES (?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, idProjet);
                    s.setInt(2, idMembre);
                    s.setBoolean(3, estResponsable);
                    s.executeUpdate();
                }
            } else {
                System.out.println("L'association Projet - Membre existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}
