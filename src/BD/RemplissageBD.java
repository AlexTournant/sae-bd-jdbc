package BD;

import java.sql.*;

public class RemplissageBD {


    public static int remplir(String nomDB, String nomTable, String[] informations) {
        Connection connexion = null;
        Statement requete = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            StringBuilder chaine = new StringBuilder("INSERT INTO "+ nomTable + " VALUES(");
            for (int i = 0;i < informations.length-1;i++) {
                chaine.append(informations[i] + " ,");
            }
            chaine.append(informations[informations.length - 1] + ")");
            System.out.println(chaine.toString());
            requete = connexion.createStatement();
            return requete.executeUpdate(chaine.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problème lors de l'exécution de la requête");
            return -1;
        } finally {
            try {
                if (requete != null) requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            CreationBD.fermerConnexion(connexion);
        }

    }
}
