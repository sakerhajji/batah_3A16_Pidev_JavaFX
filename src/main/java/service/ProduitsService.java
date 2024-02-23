package service;


import entities.Enchere;
import entities.Produits;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProduitsService {

    private Connection conn;
    private PreparedStatement pst;
    private Statement ste;

    public ProduitsService() {
        conn = DataSource.getInstance().getCnx();
    }

    /*public List<Produits> readAll() throws SQLException {
        String query = "SELECT * FROM produits";
        List<Produits> produitsList = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(query);
            while (rs.next()) {
                Produits produit = new Produits();
                produit.setIdProduit(rs.getInt("idProduit"));
                produit.setType(rs.getString("type"));
                produit.setDescription(rs.getString("description"));
                produit.setPrix(rs.getFloat("prix"));
                produit.setLabelle(rs.getString("labelle"));
                produit.setStatus(rs.getInt("status"));
                produit.setPeriodeGarantie(rs.getInt("periodeGarantie"));
                // Assuming Utilisateur is a class, you need to handle setting it here
                // For the sake of demonstration, let's assume you have a method to set Utilisateur
                // produit.setId(rs.getInt("utilisateur_id"));
                produitsList.add(produit);
            }
        } catch (SQLException e) {
            throw e;
        }
        return produitsList;
    }*/

    public Produits getProduitById(int id) throws SQLException {
        Produits produit = null;
        String query = "SELECT * FROM produits WHERE idProduit = ?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                produit = new Produits();
                produit.setIdProduit(rs.getInt("idProduit"));
                produit.setType(rs.getString("type"));
                produit.setDescription(rs.getString("description"));
                produit.setPrix(rs.getFloat("prix"));
                produit.setLabelle(rs.getString("labelle"));
                produit.setStatus(rs.getInt("status"));
                produit.setPeriodeGarantie(rs.getInt("periodeGarantie"));
                // Assuming Utilisateur is a class, you need to handle setting it here
                // For the sake of demonstration, let's assume you have a method to set Utilisateur
                // produit.setId(rs.getInt("utilisateur_id"));
            }
        } catch (SQLException e) {
            throw e;
        }
        return produit;
    }
    public Produits fetchProduitById(int idProduit) {
        String query = "SELECT * FROM produits WHERE idProduit = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, idProduit);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int idProduitResult = rs.getInt("idProduit");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    float prix = rs.getFloat("prix");
                    String labelle = rs.getString("labelle");
                    int status = rs.getInt("status");
                    int periodeGarantie = rs.getInt("periodeGarantie");

                    // Create and return Produits object
                    return new Produits(idProduitResult, type, description, prix, labelle, status, periodeGarantie, null); // Assuming Utilisateur id is not needed here
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du produit par ID", e);
        }
        return null; // Return null if no Produits object found for the given ID
    }

    public List<Enchere> readAll() {
        String query = "SELECT * FROM encheres";
        List<Enchere> enchereList = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int idEnchere = rs.getInt("idEnchere");
                LocalDate dateDebut = rs.getObject("dateDebut", LocalDate.class);
                LocalDate dateFin = rs.getObject("dateFin", LocalDate.class);
                boolean status = rs.getBoolean("status");
                float prixMin = rs.getFloat("prixMin");
                float prixMax = rs.getFloat("prixMax");
                float prixActuelle = rs.getFloat("prixActuelle");
                int idProduit = rs.getInt("idProduit");
                // Fetch the associated product description using separate query
                String produitDescription = getProductDescription(idProduit);
                Produits produit = new Produits(idProduit);
                produit.setDescription(produitDescription);
                enchereList.add(new Enchere(idEnchere, dateDebut, dateFin, status, prixMin, prixMax, prixActuelle, produit));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de toutes les enchères", e);
        }
        return enchereList;
    }

    // Method to fetch the product description based on the product ID
    private String getProductDescription(int productId) {
        String query = "SELECT description FROM produit WHERE idProduit = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, productId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("description");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de la description du produit", e);
        }
        return null;
    }

}
