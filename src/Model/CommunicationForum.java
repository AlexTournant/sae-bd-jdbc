package Model;

import BD.CreationBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CommunicationForum extends Entite {

    private int idProjet;
    private int idMembreEnvoyeur;
    private String contenu;
    private Date dateEnvoi;

    public CommunicationForum(int id, int idProjet, int idMembreEnvoyeur, String contenu, Date dateEnvoi) {
        super(id);
        this.idProjet = idProjet;
        this.idMembreEnvoyeur = idMembreEnvoyeur;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);

            if (!isInDatabase(nomDB, "CommunicationForum")) {
                String sql = "INSERT INTO CommunicationForum (id, id_projet, id_membre_envoyeur, contenu, date_envoi) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "CommunicationForum"));
                    s.setInt(2, idProjet);
                    s.setInt(3, idMembreEnvoyeur);
                    s.setString(4, contenu);
                    s.setDate(5, dateEnvoi);

                    s.executeUpdate();
                }
            } else {
                System.out.println("La communication dans le forum existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}