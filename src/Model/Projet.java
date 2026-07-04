package Model;

import BD.CreationBD;

import java.sql.*;

public class Projet extends Entite {
    private String nom_projet;
    private String sujet;
    private String technologiesUtilisees;
    private Date dateDebut;
    private Date dateFin;
    private boolean estPublic;

    public Projet(int id, String nom_projet, String sujet, String technologiesUtilisees, Date dateDebut, Date dateFin, boolean estPublic) {
        super(id);
        this.nom_projet = nom_projet;
        this.sujet = sujet;
        this.technologiesUtilisees = technologiesUtilisees;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.estPublic = estPublic;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "nom_projet=" + nom_projet + '\'' +
                "sujet='" + sujet + '\'' +
                ", technologies_utilisees='" + technologiesUtilisees + '\'' +
                ", date_debut=" + dateDebut +
                ", date_fin=" + dateFin +
                ", est_public=" + estPublic +
                '}';
    }


    @Override
    public void ajoutBD(String nomDB) throws SQLException {

        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            String sql = "INSERT INTO Projet (id, nom_projet, sujet, technologies_utilisees, date_debut, date_fin,estPublic) VALUES (?, ?, ?, ?, ?, ?, ?)";
            System.out.println(sql);
            try (PreparedStatement s = connexion.prepareStatement(sql)) {
                s.setInt(1, getId(nomDB, "Projet"));
                s.setString(2, this.nom_projet);
                s.setString(3, this.sujet);
                s.setString(4, this.technologiesUtilisees);
                s.setDate(5, this.dateDebut);
                s.setDate(6, this.dateFin);
                s.setBoolean(7, this.estPublic);
                s.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }

    }

    public String getNom_projet() {
        return nom_projet;
    }
}
