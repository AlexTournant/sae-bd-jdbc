package Model;

import BD.CreationBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class AvancementObjectif extends Entite{
    private int idObjectif;
    long millis=System.currentTimeMillis();
    private Date dateMaj;
    private String avancement;

    public AvancementObjectif(int id, int idObjectif, String avancement) {
        super(id);
        this.idObjectif = idObjectif;
        this.dateMaj = dateMaj = new Date(millis);
        this.avancement = avancement;
    }

    @Override
    public String toString() {
        return "AvancementObjectif{" +
                "idObjectif=" + idObjectif +
                ", dateMaj=" + dateMaj +
                ", avancement=" + avancement +
                '}';
    }


    public void ajoutBD(String nomDB) {
        Connection connexion = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            if (!isInDatabase(nomDB, "AvancementObjectif")) {
                String sql = "INSERT INTO AvancementObjectif (id, idObjectif, dateMaj, avancement) VALUES (?, ?, ?, ?)";

                try (PreparedStatement s = connexion.prepareStatement(sql)) {
                    s.setInt(1, getId(nomDB, "AvancementObjectif"));
                    s.setInt(2, idObjectif);
                    s.setDate(3, dateMaj);
                    s.setString(4, avancement);
                    s.executeUpdate();
                }
            } else {
                System.out.println("L'avancement existe déjà dans la base de données.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CreationBD.fermerConnexion(connexion);
        }
    }
}



