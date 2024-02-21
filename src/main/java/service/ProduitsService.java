package service;

import entities.Produits;

import entities.Utilisateur;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProduitsService {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    public ProduitsService() {
        conn= DataSource.getInstance().getCnx();

    }



    public void add(Produits produits) {
        String query = "INSERT INTO produits (type, description, prix, labelle, photo,status,periodeGarantie ,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, produits.getType());
            pst.setString(2, produits.getDescription());
            pst.setFloat(3, produits.getPrix());
            pst.setString(4, produits.getLabelle());
            pst.setString(5, produits.getPhoto());
            pst.setInt(6, produits.getStatus());
            pst.setInt(7, produits.getPeriodeGarentie());
            pst.setInt(8, produits.getId().getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("1");
            System.out.println(ex.getMessage());
        }
    }

    //



    public void delete(int idProduit) {
        String requete = "DELETE FROM produits WHERE `id`=?";
        try {
            PreparedStatement ps = conn.prepareStatement(requete);
            ps.setInt(1,idProduit);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("1");
            System.out.println(e.getMessage());
        }
    }


    public void update(Produits produits) {
        String requete = "UPDATE produits SET type=?, description=?, prix=?, labelle=?, photo=?, status=?, periodeGarantie=? WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(requete);
            ps.setString(1, produits.getType());
            ps.setString(2, produits.getDescription());
            ps.setFloat(3, produits.getPrix());
            ps.setString(4, produits.getLabelle());
            ps.setString(5, produits.getPhoto());
            ps.setInt(6, produits.getStatus());
            ps.setInt(7, produits.getPeriodeGarentie());
            //ps.setInt(8, produits.getIdProduit());  // Assuming getId() returns the ID of the product

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Produits> readAll() {
        String sql = "SELECT p.*, u.* FROM produits p \n" +
                "JOIN utilisateur u ON p.id = u.id \n";

        List<Produits> list = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                list.add(new Produits(
                        rs.getInt(1),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getString("labelle"),
                        rs.getString("photo"),
                        rs.getInt("status"),
                        rs.getInt("periodeGarantie"),
                        utilisateur
                ));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }

    /*public Produits readById(int id) {
        String sql = "\"SELECT p.*, u.* FROM produits p \" +\n" +
                "                         \"JOIN utilisateur u ON p.id = u.id \" +\n"+
                "                         \"WHERE p.idProduit = ?\"";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt(8));
                    Produits produit = new Produits(type, description, prix, labelle, photo, status, periodeGarantie, utilisateur);
                    produit.setIdProduit(rs.getInt(9));
                    return extractProduitFromResultSet(rs, utilisateur);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }*/

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


    public List<Integer> getAllUsers() {
        List<Integer> utilisateurs = new ArrayList<>();
        String query = "SELECT id FROM utilisateur";

        try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                utilisateurs.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return utilisateurs;
    }
   /*public List<Utilisateur> getAllUsers() {
       List<Utilisateur> utilisateurs = new ArrayList<>();
       String query = "SELECT * FROM utilisateur";

       try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
           while (rs.next()) {
               Utilisateur utilisateur = new Utilisateur();
               utilisateur.setId(rs.getInt("id"));
               utilisateur.setNomUtilisateur(rs.getString("nomUtilisateur"));
               utilisateur.setPrenomUtilisateur(rs.getString("prenomUtilisateur"));
               utilisateur.setSexe(rs.getString("sexe").charAt(0));
               utilisateur.setDateDeNaissance(rs.getDate("dateDeNaissance").toLocalDate());
               utilisateur.setAdresseEmail(rs.getString("adresseEmail"));
               utilisateur.setMotDePasse(rs.getString("motDePasse"));
               utilisateur.setAdressePostale(rs.getString("adressePostale"));
               utilisateur.setNumeroTelephone(rs.getString("numeroTelephone"));
               utilisateur.setNumeroCin(rs.getString("numeroCin"));
               utilisateur.setPays(rs.getString("pays"));
               utilisateur.setNbrProduitAchat(rs.getInt("nbrProduitAchat"));
               utilisateur.setNbrProduitVendu(rs.getInt("nbrProduitVendu"));
               utilisateur.setNbrProduit(rs.getInt("nbrProduit"));
               utilisateur.setNbrPoint(rs.getInt("nbrPoint"));
               utilisateur.setLanguePreferree(rs.getString("languePreferree"));
               utilisateur.setEvaluationUtilisateur(rs.getFloat("evaluationUtilisateur"));
               utilisateur.setStatutVerificationCompte(rs.getBoolean("statutVerificationCompte"));
               utilisateur.setAvatar(rs.getString("avatar"));
               utilisateur.setDateInscription(rs.getDate("dateInscription").toLocalDate());
               utilisateur.setRole(rs.getBoolean("role"));

               utilisateurs.add(utilisateur);
           }
       } catch (SQLException ex) {
           throw new RuntimeException(ex);
       }

       return utilisateurs;
   }*/

    private Produits extractProduitFromResultSet(ResultSet rs, Utilisateur utilisateur) throws SQLException {
       Produits p =new Produits(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getFloat(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7),
                rs.getInt(8),
                utilisateur// L'utilisateur associé au produit

        );
        return p;
    }


    /*public Utlisateur readById(int id) {
        Utlisateur utilisateur = null;
        String requete = "SELECT * FROM utilisateur WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                utilisateur = new Utlisateur();
                utilisateur.setIdUtlisateur(resultSet.getInt("id"));
                utilisateur.setNomUtlisateur(resultSet.getString("nomUtilisateur"));
                utilisateur.setPrenomUtlisateur(resultSet.getString("prenomUtilisateur"));
                utilisateur.setMailUtlisateur(resultSet.getString("adresseEmail"));
                utilisateur.setMotDePassUtlisateur(resultSet.getString("motDePasse"));
                utilisateur.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                utilisateur.setSexeUtlisateur(resultSet.getString("sexe").charAt(0));
                utilisateur.setCinUtlisateur(resultSet.getString("numeroCin"));
                utilisateur.setRoleUtlisateur(resultSet.getString("role"));
                utilisateur.setNumUtlisateur(resultSet.getString("numeroTelephone"));
                utilisateur.setPays(resultSet.getString("pays"));
                utilisateur.setAvatar(resultSet.getString("avatar"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilisateur;
    }
*/

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




}


















