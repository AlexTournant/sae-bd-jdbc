package Model;

import BD.CreationBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CommunicationPrivee extends Entite {

    private int idMembreEnvoyeur;
    private int idMembreDestinataire;
    private String contenu;
    private Date dateEnvoi;

    public CommunicationPrivee(int id, int idMembreEnvoyeur, int idMembreDestinataire, String contenu, Date dateEnvoi) {
        super(id);
        this.idMembreEnvoyeur = idMembreEnvoyeur;
        this.idMembreDestinataire = idMembreDestinataire;
        this.contenu = contenu;
        this.dateEnvoi = dateEnvoi;
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);

            if (!isInDatabase(nomDB, "CommunicationPrivee")) {

                String sql = "INSERT INTO CommunicationPrivee (id_message, id_membre_envoyeur, id_membre_destinataire, contenu, date_envoi) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "CommunicationPrivee"));
                    s.setInt(2, idMembreEnvoyeur);
                    s.setInt(3, idMembreDestinataire);
                    s.setString(4, contenu);
                    s.setDate(5, dateEnvoi);

                    s.executeUpdate();
                }
            } else {
                System.out.println("La communication privée existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}