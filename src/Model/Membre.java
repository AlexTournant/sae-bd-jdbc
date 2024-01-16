package Model;

import BD.CreationBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Membre extends Entite {

    private String nom;
    private String prenom;
    private boolean estResponsable;

    public Membre(int id, String nom, String prenom, boolean estResponsable) {
        super(id);
        this.nom = nom;
        this.prenom = prenom;
        this.estResponsable = estResponsable;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", estResponsable=" + estResponsable +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);

            if (!isInDatabase(nomDB, "Membre")) {
                String sql = "INSERT INTO Membre (id_membre, nom, prenom, est_responsable) VALUES (?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId());
                    s.setString(2, nom);
                    s.setString(3, prenom);
                    s.setBoolean(4, estResponsable);

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