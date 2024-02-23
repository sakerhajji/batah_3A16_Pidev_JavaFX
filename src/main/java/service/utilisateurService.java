
package service;

import utils.DataSource;
import entities.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class utilisateurService {

    private Connection conn;
    private PreparedStatement pst;
    private Statement ste;

    public utilisateurService() {
        conn = DataSource.getInstance().getCnx();

    }
    public List<Utilisateur> readAll() throws SQLException {
        String query = "SELECT * FROM utilisateur";
        List<Utilisateur> userList = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setNomUtilisateur(rs.getString("nomUtilisateur"));
                user.setPrenomUtilisateur(rs.getString("prenomUtilisateur"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw e;
        }
        return userList;
    }

  /*  public Utilisateur getUserByName(String nomUtilisateur, String prenomUtilisateur) throws SQLException {
        // Query to retrieve the Utilisateur by name
        String query = "SELECT * FROM utilisateur WHERE nomUtilisateur = ? AND prenomUtilisateur = ?";

        // Prepare the statement
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, nomUtilisateur);
        preparedStatement.setString(2, prenomUtilisateur);

        // Execute the query
        ResultSet resultSet = preparedStatement.executeQuery();

        // Check if a user was found
        if (resultSet.next()) {
            // Create a new Utilisateur object with the retrieved data
            int idUtilisateur = resultSet.getInt("id");

            // Retrieve other necessary fields similarly

            // Return the Utilisateur object
            return new Utilisateur(idUtilisateur);
        } else {
            // If no user was found, return null
            return null;
        }
    }*/

}