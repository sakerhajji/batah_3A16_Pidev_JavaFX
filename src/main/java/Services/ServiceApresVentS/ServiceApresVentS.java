
package Services.ServiceApresVentS;

import DataBaseSource.DataSource;
import Entity.UserAdmin.Membre;
import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesProduits.Achats;
import Entity.entitiesProduits.Produits;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import InterfaceServices.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceApresVentS implements IService<ServiceApresVente> {
    private Connection con;
    private PreparedStatement pst;

    public ServiceApresVentS() {
        con = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(ServiceApresVente serviceApresVente) {
        String req = "INSERT INTO service_apres_vente (description, type, date, status, idAchats) VALUES (?,?,?,?,?)";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setString(1, serviceApresVente.getDescription());
            pst.setString(2, serviceApresVente.getType());
            pst.setTimestamp(3, new Timestamp(serviceApresVente.getDate().getTime()));
            pst.setBoolean(4, serviceApresVente.isStatus());
            pst.setInt(5, serviceApresVente.getIdAchats().getIdAchats());
            pst.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception properly (e.g., log it)
            System.err.println("Error while adding ServiceApresVente: " + e.getMessage());
        }
    }


    @Override
    public void delete(ServiceApresVente serviceApresVente) {
        String req = "DELETE FROM service_apres_vente WHERE idService=?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setInt(1, serviceApresVente.getIdService());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting ServiceApresVente: " + e.getMessage());
        }
    }


    @Override
    public void update(ServiceApresVente serviceApresVente) {
        String req = "UPDATE service_apres_vente SET description=?, type=?, date=?, status=?, idPartenaire=?, idAchats=? WHERE idService=?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setString(1, serviceApresVente.getDescription());
            pst.setString(2, serviceApresVente.getType());
            pst.setTimestamp(3, new Timestamp(serviceApresVente.getDate().getTime()));
            pst.setBoolean(4, serviceApresVente.isStatus());
            pst.setInt(5, serviceApresVente.getIdPartenaire().getId());
            pst.setInt(6, serviceApresVente.getIdAchats().getIdAchats());
            pst.setInt(7, serviceApresVente.getIdService());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating ServiceApresVente: " + e.getMessage());
        }
    }


    @Override
    public List<ServiceApresVente> readAll() {
        List<ServiceApresVente> list = new ArrayList<>();
        String req = "SELECT sav.idService, sav.description, sav.type, sav.date, sav.status, " +
                "a.idAchats, a.idProduits, a.idUtilisateur, a.dateAchats, " +
                "p.idPartenaire, p.nom, p.type AS partenaire_type, p.adresse, p.telephone, p.email " +
                "FROM service_apres_vente sav " +
                "JOIN achats a ON sav.idAchats = a.idAchats " +
                "JOIN partenaires p ON sav.idPartenaire = p.idPartenaire";
        try {
            PreparedStatement pst = con.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idService = rs.getInt("idService");
                String description = rs.getString("description");
                String type = rs.getString("type");
                Date date=rs.getDate("date");
                boolean status = rs.getBoolean("status");
                // Fetching Partenaire and Achats objects
                Partenaire idPartenaire = new Partenaire(rs.getInt("idPartenaire"), rs.getString("nom"), rs.getString("partenaire_type"), rs.getString("adresse"), rs.getInt("telephone"), rs.getString("email"));
                Produits produits = new Produits(
                        rs.getInt("idProduit"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getString("labelle"),
                        rs.getString("status"),
                        rs.getInt("periodeGarantie"),
                        rs.getString("photo"),
                        new Membre(rs.getInt(9), rs.getString("nomUtilisateur"), rs.getString("prenomUtilisateur"))
                );
                Membre membre = new Membre(rs.getInt("id"), rs.getString("membre_nom"), rs.getString("membre_email"));
                Achats idAchats = new Achats(rs.getInt("idAchats"), produits, membre, rs.getDate("dateAchats"));
                ServiceApresVente sav = new ServiceApresVente(idService, description, type, date, status, idPartenaire, idAchats);
                list.add(sav);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }



    @Override
    public ServiceApresVente readById(int id) {
        String req = "SELECT sav.idService, sav.description, sav.type, sav.date, sav.status, " +
                "a.idAchats, a.idProduits, a.idUtilisateur, a.dateAchats, " +
                "p.idPartenaire, p.nom, p.type AS partenaire_type, p.adresse, p.telephone, p.email " +
                "FROM service_apres_vente sav " +
                "JOIN achats a ON sav.idAchats = a.idAchats " +
                "JOIN partenaires p ON sav.idPartenaire = p.idPartenaire " +
                "WHERE sav.idService = ?";
        try {
            PreparedStatement pst = con.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int idService = rs.getInt("idService");
                String description = rs.getString("description");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                boolean status = rs.getBoolean("status");

                Membre user=new Membre() ;
                //Partenaire idPartenaire = new Partenaire(rs.getInt("idPartenaire"), rs.getString("nom"), rs.getString("partenaire_type"), rs.getString("adresse"), rs.getInt("telephone"), rs.getString("email"));
                Produits produits = new Produits(
                        rs.getInt("idProduits"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getFloat("prix"),
                        rs.getString("labelle"),
                        rs.getString("status"),
                        rs.getInt("periodeGarantie"),
                        rs.getString("photo"),
                        user

                );

                Membre membre = new Membre(
                        rs.getInt("idUtilisateur"),
                        rs.getString("nomUtilisateur"),
                        rs.getString("prenomUtilisateur"),
                        rs.getString("mailUtilisateur"),
                        // Add other fields based on your Membre entity
                        rs.getString("motDePassUtilisateur"),  // Assuming there's a field for password
                        rs.getDate("dateDeNaissance"),         // Assuming there's a field for date of birth
                        rs.getString("sexeUtilisateur").charAt(0),  // Assuming 'sexeUtilisateur' is a char
                        rs.getString("cinUtilisateur"),
                        rs.getString("roleUtilisateur").charAt(0),  // Assuming 'roleUtilisateur' is a char
                        rs.getString("numUtilisateur"),
                        rs.getString("pays"),
                        rs.getString("avatar"),
                        rs.getInt("nbrProuduitAchat"),          // Assuming there's a field for number of products bought
                        rs.getInt("nbrProduitVendu"),           // Assuming there's a field for number of products sold
                        rs.getString("languePreferree"),
                        rs.getInt("nbrProduit"),                // Assuming there's a field for total number of products
                        rs.getInt("nbrPoint"),
                        rs.getDate("dateInscription")
                );

                Achats idAchats = new Achats(
                        rs.getInt("idAchats"),
                        produits,
                        membre,
                        date
                );

                Partenaire idPartenaire = new Partenaire(
                        rs.getInt("idPartenaire"),
                        rs.getString("nom"),
                        rs.getString("partenaire_type"),
                        rs.getString("adresse"),
                        rs.getInt("telephone"),
                        rs.getString("email")
                );

                return new ServiceApresVente(idService, description, type, date, status, idPartenaire, idAchats);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



}
