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
        String query = "INSERT INTO produits (type, description, prix, labelle,status,periodeGarantie ,idUtilisateur) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, produits.getType());
            pst.setString(2, produits.getDescription());
            pst.setFloat(3, produits.getPrix());
            pst.setString(4, produits.getLabelle());
            pst.setInt(5, produits.getStatus());
            pst.setInt(6, produits.getPeriodeGarentie());
            pst.setInt(7, produits.getId().getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("1");
            System.out.println(ex.getMessage());
        }
    }

    //



    public void delete(int idProduit) {
        String requete = "DELETE FROM produits WHERE `idProduit`=?";
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


   /* public void update(Produits produits) {
        String requete = "UPDATE produits SET type=?, description=?, prix=?, labelle=?, status=?, periodeGarantie=? ,idUtilisateur=? WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(requete);
            ps.setString(1, produits.getType());
            ps.setString(2, produits.getDescription());
            ps.setFloat(3, produits.getPrix());
            ps.setString(4, produits.getLabelle());
            ps.setInt(5, produits.getStatus());
            ps.setInt(6, produits.getPeriodeGarentie());
            ps.setInt(7, produits.getId().getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
   public void update(Produits p) {
       String requete = "UPDATE produits SET type='" + p.getType() + "', description='" + p.getDescription() + "', prix='" + p.getPrix() + "', labelle='" + p.getLabelle() + "', status='" + p.getStatus() + "', periodeGarantie=" + p.getPeriodeGarentie() + " WHERE idProduit=" + p.getIdProduit();
       try {
           ste=conn.createStatement();
           ste.executeUpdate(requete);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }


    public List<Produits> readAll() {
        String sql = "SELECT p.*, u.nomUtilisateur , u.prenomUtilisateur FROM produits as p \n" +
                "JOIN utilisateur as u ON p.idUtilisateur = u.id \n";


        List<Produits> list = new ArrayList<>();

        try (
                Statement pst = conn.createStatement();
                ResultSet rs = pst.executeQuery(sql)) {
            while (rs.next()) {
                Produits produits=new Produits();
                produits.setIdProduit(rs.getInt(1));
                produits.setType(rs.getString("type"));
                produits.setDescription(rs.getString("description"));
                produits.setPrix(rs.getFloat("prix"));
                produits.setLabelle(rs.getString("labelle"));
                produits.setStatus(rs.getInt("status"));
                produits.setPeriodeGarentie(rs.getInt(7));
                Utilisateur user=new Utilisateur();
                user.setId(rs.getInt("idUtilisateur"));
                produits.setId(user);




                list.add(produits);
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

        Utilisateur u = new Utilisateur(id, nomUtilisateur, prenomUtilisateur);
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
                produit.setPeriodeGarentie(rs.getInt("periodeGarantie"));
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

    public List<Produits> rechercheParNom(String nom) {
        ResultSet rs = null;
        List<Produits> list2 = new ArrayList<>();
        try {
            String query = "SELECT * FROM produits WHERE type LIKE ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, "%" + nom + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
                Produits produits = new Produits();

                produits.setIdProduit(rs.getInt(1));
                produits.setType(rs.getString("type"));
                produits.setDescription(rs.getString("description"));
                produits.setPrix(rs.getFloat("prix"));
                produits.setLabelle(rs.getString("labelle"));
                produits.setStatus(rs.getInt("status"));
                produits.setPeriodeGarentie(rs.getInt(7));
                Utilisateur user=new Utilisateur();
                user.setId(rs.getInt("idUtilisateur"));
                produits.setId(user);

                list2.add(produits);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list2;
    }


}





















