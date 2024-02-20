package service;

import entities.Enchere;
import entities.Produit;
import entities.Utilisateur;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EncheresService implements IService<Enchere> {

    private final Connection conn;

    public EncheresService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Enchere enchere) {
        String query = "INSERT INTO encheres (DateDebut, DateFin, Status, PrixMin, PrixMax, PrixActuelle, id, idProduit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setDate(1, enchere.getDateDebut());
            pst.setDate(2, enchere.getDateFin());
            pst.setBoolean(3, enchere.isStatus());
            pst.setDouble(4, enchere.getPrixMin());
            pst.setDouble(5, enchere.getPrixMax());
            pst.setDouble(6, enchere.getPrixActuelle());
            pst.setInt(7, enchere.getId().getId());
            pst.setInt(8, enchere.getIdProduit().getIdProduit());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Enchere enchere) {
        String query = "DELETE FROM encheres WHERE idEnchere = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, enchere.getIdEnchere());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Enchere enchere) {
        String query = "UPDATE encheres SET DateDebut=?, DateFin=?, Status=?, PrixMin=?, PrixMax=?, PrixActuelle=?, id=?, idProduit=? WHERE idEnchere=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setDate(1, enchere.getDateDebut());
            pst.setDate(2, enchere.getDateFin());
            pst.setBoolean(3, enchere.isStatus());
            pst.setDouble(4, enchere.getPrixMin());
            pst.setDouble(5, enchere.getPrixMax());
            pst.setDouble(6, enchere.getPrixActuelle());
            pst.setInt(7, enchere.getId().getId());
            pst.setInt(8, enchere.getIdProduit().getIdProduit());
            pst.setInt(9, enchere.getIdEnchere());
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Enchere> readAll() {
        String sql = "SELECT e.*, u.*, p.* FROM encheres e \n" +
                "JOIN utilisateur u ON e.id = u.id \n" +
                "JOIN produits p ON e.idProduit = p.idProduit LIMIT 0, 25;\n";

        List<Enchere> list = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt(8));
                Produit produit = new Produit();
                produit.setIdProduit(rs.getInt(9));
                list.add(new Enchere(
                        rs.getInt(1),
                        rs.getDate(2),
                        rs.getDate(3),
                        rs.getBoolean(4),
                        rs.getFloat(5),
                        rs.getFloat(6),
                        rs.getFloat(7),
                        utilisateur,
                        produit
                ));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }

    @Override
    public Enchere readById(int id) {
        String sql = "\"SELECT e.*, u.*, p.* FROM encheres e \" +\n" +
                "                         \"JOIN utilisateur u ON e.id = u.id \" +\n" +
                "                         \"JOIN produits p ON e.idProduit = p.idProduit \" +\n" +
                "                         \"WHERE e.idEnchere = ?\"";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt(8));
                    Produit produit = new Produit();
                    produit.setIdProduit(rs.getInt(9));
                    return extractEnchereFromResultSet(rs, utilisateur, produit);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private Utilisateur extractUtilisateurFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String nomUtilisateur = rs.getString(2);
        String prenomUtilisateur = rs.getString(3);
        char sexe = rs.getString(4) != null && !rs.getString(4).isEmpty() ? rs.getString(4).charAt(0) : '\0';
        LocalDate dateDeNaissance = rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null;
        String adresseEmail = rs.getString(6);
        String motDePasse = rs.getString(7);
        String adressePostale = rs.getString(8);
        String numeroTelephone = rs.getString(9);
        String numeroCin = rs.getString(10);
        String pays = rs.getString(11);
        int nbrProduitAchat = rs.getInt(12);
        int nbrProduitVendu = rs.getInt(13);
        int nbrProduit = rs.getInt(14);
        int nbrPoint = rs.getInt(15);
        String languePreferree = rs.getString(16);
        float evaluationUtilisateur = rs.getFloat(17);
        boolean statutVerificationCompte = rs.getBoolean(18);
        String avatar = rs.getString(19);
        LocalDate dateInscription = rs.getDate(20) != null ? rs.getDate(20).toLocalDate() : null;
        boolean role = rs.getBoolean(21);
        Utilisateur u = new Utilisateur(id, nomUtilisateur, prenomUtilisateur, sexe, dateDeNaissance, adresseEmail, motDePasse, adressePostale,
                numeroTelephone, numeroCin, pays, nbrProduitAchat, nbrProduitVendu, nbrProduit, nbrPoint, languePreferree,
                evaluationUtilisateur, statutVerificationCompte, avatar, dateInscription, role);
        return u;
    }


    private Produit extractProduitFromResultSet(ResultSet rs, Utilisateur utilisateur) throws SQLException {
        return new Produit(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getFloat(4),
                rs.getString(5),
                rs.getString(6),
                rs.getBoolean(7),
                rs.getInt(8),
                utilisateur // L'utilisateur associé au produit
        );
    }

    private Enchere extractEnchereFromResultSet(ResultSet rs, Utilisateur utilisateur, Produit produit) throws SQLException {
        Enchere e = new Enchere(
                rs.getInt(1),
                rs.getDate(2),
                rs.getDate(3),
                rs.getBoolean(4),
                rs.getFloat(5),
                rs.getFloat(6),
                rs.getFloat(7),
                utilisateur,
                produit
        );
        return e;
    }

    public Utilisateur getUserById(int userId) {
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractUtilisateurFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    public Produit getProduitById(int produitId) {
        String query = "SELECT * FROM produits WHERE idProduit = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, produitId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractProduitFromResultSet(rs, null); // Vous pouvez passer null pour l'utilisateur car il n'est pas nécessaire ici
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
