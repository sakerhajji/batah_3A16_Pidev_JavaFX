package Services.ServiceProduit;

import Entity.entitiesProduits.Produits;
import Entity.UserAdmin.Membre;
import DataBaseSource.DataSource;
import Services.UserAdmineServices.MembreService;

import java.sql.*;
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
            pst.setInt(7, produits.getId().getIdUtilisateur());
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


   public void update(Produits p) {
       String requete = "UPDATE produits SET type='" + p.getType() + "', description='" + p.getDescription() + "', prix='" + p.getPrix() + "', labelle='" + p.getLabelle() + "', status='" + p.getStatus() + "', periodeGarantie=" + p.getPeriodeGarentie() + ", idUtilisateur=" + p.getId().getIdUtilisateur() + " WHERE idProduit=" + p.getIdProduit();
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
                produits.setPeriodeGarentie(rs.getInt("periodeGarantie"));
                Membre user=new Membre();
                user.setIdUtilisateur(rs.getInt("idUtilisateur"));
                produits.setId(user);




                list.add(produits);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }


    private Membre extractUtilisateurFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String nomUtilisateur = rs.getString(2);
        String prenomUtilisateur = rs.getString(3);

        Membre u = new Membre(id, nomUtilisateur, prenomUtilisateur);
        return u;
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
                    int idUser =rs.getInt(8);
                    MembreService ms = new MembreService();
                    Membre selectedUser = ms.readById(idUser);



                    // Create and return Produits object
                    return new Produits(idProduitResult, type, description, prix, labelle, status, periodeGarantie, selectedUser); // Assuming Utilisateur id is not needed here
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du produit par ID", e);
        }
        return null; // Return null if no Produits object found for the given ID
    }



    private Produits extractProduitFromResultSet(ResultSet rs, Membre utilisateur) throws SQLException {
       Produits p =new Produits(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getFloat(4),
                rs.getString(5),
                rs.getInt(6),
                rs.getInt(7),
                utilisateur// L'utilisateur associé au produit

        );
        return p;
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
                Membre user=new Membre();
                user.setIdUtilisateur(rs.getInt("idUtilisateur"));
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




















