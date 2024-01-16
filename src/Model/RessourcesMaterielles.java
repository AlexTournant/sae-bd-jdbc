package Model;

import BD.CreationBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RessourcesMaterielles extends Entite {

    private int idProjet;
    private String nomRessourceMat;
    private int quantite;

    public RessourcesMaterielles(int id, int idProjet, String nomRessourceMat, int quantite) {
        super(id);
        this.idProjet = idProjet;
        this.nomRessourceMat = nomRessourceMat;
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "RessourcesMaterielles{" +
                "idProjet=" + idProjet +
                ", nomRessourceMat='" + nomRessourceMat + '\'' +
                ", quantite=" + quantite +
                '}';
    }

    @Override
    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);

            if (!isInDatabase(nomDB, "RessourcesMaterielles")) {

                String sql = "INSERT INTO RessourcesMaterielles (id_ressource_mat, id_projet, nom_ressource_mat, quantite) VALUES (?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId());
                    s.setInt(2, idProjet);
                    s.setString(3, nomRessourceMat);
                    s.setInt(4, quantite);

                    s.executeUpdate();
                }
            } else {
                System.out.println("La ressource matérielle existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}