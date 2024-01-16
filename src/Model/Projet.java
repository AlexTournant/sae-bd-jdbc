package Model;

import BD.CreationBD;

import java.sql.*;

public class Projet extends Entite {
    String sujet;
    String technologiesUtilisees;
    Date dateDebut;
    Date dateFin;

    public Projet(int id, String sujet, String technologiesUtilisees, Date dateDebut, Date dateFin) {
        super(id);
        this.sujet = sujet;
        this.technologiesUtilisees = technologiesUtilisees;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "sujet='" + sujet + '\'' +
                ", technologies_utilisees='" + technologiesUtilisees + '\'' +
                ", date_debut=" + dateDebut +
                ", date_fin=" + dateFin +
                '}';
    }


    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            if (!isInDatabase(nomDB, "Projet")) {
                String sql = "INSERT INTO Projet (id_projet, sujet, technologies_utilisees, date_debut, date_fin) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId());
                    s.setString(2, sujet);
                    s.setString(3, technologiesUtilisees);
                    s.setDate(4, dateDebut);
                    s.setDate(5, dateFin);

                    s.executeUpdate();
                }
            } else {
                System.out.println("Le projet existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }


}