package Model;

import BD.CreationBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Authentification extends Entite {

    private int id_membre;
    private String nom_utilisateur;
    private String mdp;
    private boolean est_connecte;
    private boolean est_admin;

    public Authentification(int id, int id_membre, String nom_utilisateur, String mdp, boolean est_connecte, boolean est_admin) {
        super(id);
        this.id_membre = id_membre;
        this.nom_utilisateur = nom_utilisateur;
        this.mdp = mdp;
        this.est_connecte = est_connecte;
        this.est_admin = est_admin;
    }

    @Override
    public String toString() {
        return "Authentification{" +
                "id_membre=" + id_membre +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", mdp='" + mdp + '\'' +
                ", est_connecte=" + est_connecte +
                ", est_admin=" + est_admin +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            if (!isInDatabase(nomDB, "Authentification")) {
                String sql = "INSERT INTO Authentification (id, id_membre, mdp, est_connecte, est_admin) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "Authentification"));
                    s.setInt(2, id_membre);
                    s.setString(3, mdp);
                    s.setBoolean(4, est_connecte);
                    s.setBoolean(4, est_admin);
                    s.executeUpdate();
                }
            } else {
                System.out.println("La table Authentification existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}