package Services.ServiceProduit;

import DataBaseSource.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingService {
    Connection cnx = DataSource.getInstance().getCnx();


    public void addRating(int userId, int prodId, double rating) {
        try {
            String query = "INSERT INTO ratings (id_user, id_produit, rating) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, prodId);
                preparedStatement.setDouble(3, rating);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateRating(int userId, int prodId, int newRating) {
        try {
            String query = "UPDATE ratings SET rating = ? WHERE id_user = ? AND id_produit = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, newRating);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, prodId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public double getAverageRating(int prodId) {
        double averageRating = 0;
        try {
            String query = "SELECT AVG(rating) AS average_rating FROM ratings WHERE id_produit = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, prodId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                averageRating = resultSet.getDouble("average_rating");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return averageRating;
    }

    public int getNumberOfVotes(int prodId) {
        int numberOfVotes = 0;
        try {
            String query = "SELECT COUNT(*) AS vote_count FROM ratings WHERE id_produit = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, prodId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfVotes = resultSet.getInt("vote_count");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfVotes;
    }

    public boolean hasUserSubmittedFeedback(int userId, int prodId) {
        try {
            String query = "SELECT COUNT(*) AS count FROM ratings WHERE id_user = ? AND id_produit = ?";
            try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, prodId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void RemoveRatingFromProduct(int idProduit) {
        try {

            String req = "DELETE FROM `ratings` WHERE id_produit = ?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, idProduit);
            st.executeUpdate();
            System.out.println("Rating removed from this product!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
