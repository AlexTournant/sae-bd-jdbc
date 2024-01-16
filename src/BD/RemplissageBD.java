package BD;

import java.sql.*;

public class RemplissageBD {


    public static int remplir(String nomDB, String nomTable, String[] informations) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = CreationBD.connexionBD(nomDB);
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(nomTable).append(" VALUES (");
            for (int i = 0; i < informations.length - 1; i++) {
                queryBuilder.append("?, ");
            }
            queryBuilder.append("?)");
            preparedStatement = connexion.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < informations.length; i++) {
                preparedStatement.setString(i + 1, informations[i]);
            }

            System.out.println(preparedStatement.toString()); // Affichage de la requête (à des fins de débogage)

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problème lors de l'exécution de la requête");
            return -1;
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            CreationBD.fermerConnexion(connexion);
        }
    }
}
