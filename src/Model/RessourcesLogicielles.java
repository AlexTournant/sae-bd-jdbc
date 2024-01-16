package Model;

import BD.CreationBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RessourcesLogicielles extends Entite {

    private int idProjet;
    private String nomRessourceLog;
    private String version;

    public RessourcesLogicielles(int id, int idProjet, String nomRessourceLog, String version) {
        super(id);
        this.idProjet = idProjet;
        this.nomRessourceLog = nomRessourceLog;
        this.version = version;
    }

    @Override
    public String toString() {
        return "RessourcesLogicielles{" +
                "idProjet=" + idProjet +
                ", nomRessourceLog='" + nomRessourceLog + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);

            if (!isInDatabase(nomDB, "RessourcesLogicielles")) {
                String sql = "INSERT INTO RessourcesLogicielles (id, id_projet, nom_ressource_log, version) VALUES (?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "RessourcesLogicielles"));
                    s.setInt(2, idProjet);
                    s.setString(3, nomRessourceLog);
                    s.setString(4, version);

                    s.executeUpdate();
                }
            } else {
                System.out.println("La ressource logicielle existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}