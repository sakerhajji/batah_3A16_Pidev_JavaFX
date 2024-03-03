package Services.EnchereService;

import DataBaseSource.DataSource;
import Entity.entitiesEncheres.Enchere;
import Entity.entitiesProduits.Produits;
import InterfaceServices.IService;
import Services.ServiceProduit.ProduitsService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnchereService implements IService<Enchere> {

    private final Connection conn;
    private PreparedStatement pst;
    private Statement ste;

    public EnchereService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Enchere enchere) {
        String query = "INSERT INTO encheres (dateDebut, dateFin, status, prixMin, prixMax, prixActuelle,nbrParticipants, idProduit) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setObject(1, enchere.getDateDebut());
            pst.setObject(2, enchere.getDateFin());
            pst.setBoolean(3, enchere.isStatus());
            pst.setFloat(4, enchere.getPrixMin());
            pst.setFloat(5, enchere.getPrixMax());
            pst.setFloat(6, enchere.getPrixActuelle());
            pst.setInt(7, enchere.getNbrParticipants());
            pst.setInt(8, enchere.getIdProduit().getIdProduit());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'enchère", e);
        }
    }

    @Override
    public void delete(Enchere enchere) {
        String query = "DELETE FROM encheres WHERE idEnchere = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, enchere.getIdEnchere());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'enchère", e);
        }
    }

    @Override
    public void update(Enchere enchere) {
        String query = "UPDATE encheres SET dateDebut=?, dateFin=?, status=?, prixMin=?, prixMax=?, prixActuelle=?,nbrParticipants=?, idProduit=? WHERE idEnchere=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setObject(1, enchere.getDateDebut());
            pst.setObject(2, enchere.getDateFin());
            pst.setBoolean(3, enchere.isStatus());
            pst.setFloat(4, enchere.getPrixMin());
            pst.setFloat(5, enchere.getPrixMax());
            pst.setFloat(6, enchere.getPrixActuelle());
            pst.setInt(7, enchere.getNbrParticipants());
            pst.setInt(8, enchere.getIdProduit().getIdProduit());
            pst.setInt(9, enchere.getIdEnchere());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'enchère", e);
        }
    }

    @Override

    public List<Enchere> readAll() {
        String query = "SELECT e.*, p.description AS productDescription " +
                "FROM encheres e " +
                "JOIN produits p ON e.idProduit = p.idProduit";  // Fixed the alias for produits table
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
                int nbrParticipants = rs.getInt("nbrParticipants");
                int idProduit = rs.getInt("idProduit");
                String productDescription = rs.getString("productDescription");

                ProduitsService produitsService = new ProduitsService();
                // Fetch Produits object by idProduit
                Produits produit = produitsService.fetchProduitById(idProduit);

                // Create Enchere object using the fetched Produits object
                enchereList.add(new Enchere(idEnchere, dateDebut, dateFin, status, prixMin, prixMax, prixActuelle, nbrParticipants, produit));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de toutes les enchères", e);
        }
        return enchereList;
    }


    @Override
    public Enchere readById(int id) {
        String query = "SELECT e.*, p.description AS productDescription " +
                "FROM encheres e " +
                "JOIN produits p ON e.idProduit = p.idProduit " +
                "WHERE e.idEnchere = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    LocalDate dateDebut = rs.getObject("dateDebut", LocalDate.class);
                    LocalDate dateFin = rs.getObject("dateFin", LocalDate.class);
                    boolean status = rs.getBoolean("status");
                    float prixMin = rs.getFloat("prixMin");
                    float prixMax = rs.getFloat("prixMax");
                    float prixActuelle = rs.getFloat("prixActuelle");
                    int nbrParticipants = rs.getInt("nbrParticipants");
                    int idProduit = rs.getInt("idProduit");
                    String productDescription = rs.getString("productDescription");

                    ProduitsService produitsService = new ProduitsService();
                    // Fetch Produits object by idProduit
                    Produits produit = produitsService.fetchProduitById(idProduit);

                    // Create Enchere object using the fetched Produits object
                    return new Enchere(id, dateDebut, dateFin, status, prixMin, prixMax, prixActuelle, nbrParticipants, produit);
                } else {
                    // No enchere found with the given id
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de l'enchère avec l'identifiant : " + id, e);
        }
    }
}
