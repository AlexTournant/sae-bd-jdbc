package Model;

import BD.CreationBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Document extends Entite {

    private int id_projet;
    private int id_membre_upload;
    private String chemin_document;
    private byte[] contenu;

    public Document(int id, int id_projet, int id_membre_upload, String chemin_document, byte[] contenu) {
        super(id);
        this.id_projet = id_projet;
        this.id_membre_upload = id_membre_upload;
        this.chemin_document = chemin_document;
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id_projet=" + id_projet +
                ", id_membre_upload=" + id_membre_upload +
                ", chemin_document='" + chemin_document + '\'' +
                ", contenu=" + Arrays.toString(contenu) +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            if (!isInDatabase(nomDB, "Document")) {
                String sql = "INSERT INTO Document (id_document, id_projet, id_membre_upload, chemin_document, contenu) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "Document"));
                    s.setInt(2, id_projet);
                    s.setInt(3, id_membre_upload);
                    s.setString(4, chemin_document);
                    s.setBytes(5, contenu);
                    s.executeUpdate();
                }
            } else {
                System.out.println("Le Document existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}