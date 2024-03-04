package Services.ServiceProduit;

import DataBaseSource.DataSource;
import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Achats;
import Entity.entitiesProduits.Produits;
import Services.UserAdmineServices.MembreService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitsService {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;

    ServiceBasket sb=new ServiceBasket();
    RatingService rs=new RatingService();
    public ProduitsService() {
        conn= DataSource.getInstance().getCnx();

    }



    public void add(Produits produits) {
        String query = "INSERT INTO produits (type, description, prix, labelle,status,periodeGarantie,photo ,idUtilisateur) VALUES (?, ?,?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, produits.getType());
            pst.setString(2, produits.getDescription());
            pst.setFloat(3, produits.getPrix());
            pst.setString(4, produits.getLabelle());
            pst.setString(5, produits.getStatus());
            pst.setInt(6, produits.getPeriodeGarentie());
            pst.setString(7, produits.getPhoto());
            pst.setInt(8, produits.getId().getIdUtilisateur());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("1");
            System.out.println(ex.getMessage());
        }
    }

    //



    public void delete(int idProduit) {

        sb.RemoveProdFromBasket(idProduit);
        rs.RemoveRatingFromProduct(idProduit);

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


   public void update(Produits produits) {
       String query = "UPDATE produits SET type=?, description=?, prix=?, labelle=?, status=?, periodeGarantie=?,photo=?, idUtilisateur=? WHERE idProduit=?";
       try (PreparedStatement pst = conn.prepareStatement(query)) {
           pst.setString(1,produits.getType());
           pst.setString(2,produits.getDescription());
           pst.setFloat(3,produits.getPrix());
           pst.setString(4,produits.getLabelle());
           pst.setString(5,produits.getStatus());
           pst.setInt(6,produits.getPeriodeGarentie());
           pst.setString(7,produits.getPhoto());
           pst.setInt(8,produits.getId().getIdUtilisateur());
           pst.setInt(9,produits.getIdProduit());
           pst.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException("Erreur lors de la mise à jour du produit", e);
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
                produits.setType(rs.getString(2));
                produits.setDescription(rs.getString(3));
                produits.setPrix(rs.getFloat(4));
                produits.setLabelle(rs.getString(5));
                produits.setStatus(rs.getString(6));
                produits.setPeriodeGarentie(rs.getInt(7));
                produits.setPhoto(rs.getString(8));
                Membre user=new Membre();
                user.setIdUtilisateur(rs.getInt(9));
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
                    String status = rs.getString("status");
                    int periodeGarantie = rs.getInt("periodeGarantie");
                    String photo = rs.getString("photo");
                    int idUser =rs.getInt(9);
                    MembreService ms = new MembreService();
                    Membre selectedUser = ms.readById(idUser);



                    // Create and return Produits object
                    return new Produits(idProduitResult, type, description, prix, labelle,status, periodeGarantie, photo,selectedUser);
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
                rs.getString(6),
                rs.getInt(7),
               rs.getString(8),
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
                produits.setStatus(rs.getString("status"));
                produits.setPeriodeGarentie(rs.getInt(7));
                produits.setPhoto(rs.getString(8));
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
    public List<Achats> readAllAchat() {
        String requte="select * from achats";
        List<Achats> list=new ArrayList<>();
        try {
            ste=conn.createStatement();
            ResultSet rs =ste.executeQuery(requte);
            while(rs.next()){
                list.add(new Achats(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDate(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public Achats readbyIdAchat(int id)
    {
        String requete = "SELECT * FROM achats WHERE idAchats=?";
        Achats par = null;
        try {
            PreparedStatement ps = conn.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                par = new Achats(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return par;
    }


}

