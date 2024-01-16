package Model;

import BD.CreationBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Membre extends Entite {

    private String nom;
    private String prenom;

    public Membre(int id, String nom, String prenom) {
        super(id);
        this.nom = nom;
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);

            if (!isInDatabase(nomDB, "Membre")) {
                String sql = "INSERT INTO Membre (id_membre, nom, prenom) VALUES (?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "Membre"));
                    s.setString(2, nom);
                    s.setString(3, prenom);

                    s.executeUpdate();
                }
            } else {
                System.out.println("Le membre existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}