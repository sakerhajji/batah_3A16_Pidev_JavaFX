package Services.ServiceApresVentS;

import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesProduits.Achats;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import InterfaceServices.IService;
import Services.ServiceProduit.ProduitsService;
import Services.servicePartenaire.partenaireService;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceApresVentS  {
    private Connection con;
    private PreparedStatement pst;

    public ServiceApresVentS() {
        con = DataSource.getInstance().getCnx();
    }

    public Map<String, Integer> countServicesByType() {
        Map<String, Integer> results = new HashMap<>();
        String query = "SELECT type, COUNT(*) as count FROM service_apres_vente GROUP BY type";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                results.put(rs.getString("type"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching service counts by type: " + e.getMessage());
        }
        return results;
    }

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



    public void delete(int serviceApresVente) {
        String req = "DELETE FROM service_apres_vente WHERE idService=?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setInt(1, serviceApresVente);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting ServiceApresVente: " + e.getMessage());
        }
    }



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
    public void updateS(ServiceApresVente serviceApresVente) {
        String req = "UPDATE service_apres_vente SET description=?, type=?, date=? WHERE idService=?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setString(1, serviceApresVente.getDescription());
            pst.setString(2, serviceApresVente.getType());
            pst.setTimestamp(3, new Timestamp(serviceApresVente.getDate().getTime()));
            pst.setInt(4, serviceApresVente.getIdService());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating ServiceApresVente: " + e.getMessage());
        }
    }



    public List<ServiceApresVente> readAll() {
        List<ServiceApresVente> list = new ArrayList<>();
        String req = "SELECT sav.idService, sav.description, sav.type, sav.date, sav.status, " +
                "a.idAchats, a.idProduits, a.idUtilisateur, a.dateAchats, " +
                "p.idPartenaire, p.nom, p.type AS partenaire_type, p.adresse, p.telephone, p.email " +
                "FROM service_apres_vente sav " +
                "JOIN achats a ON sav.idAchats = a.idAchats " +
                "LEFT JOIN partenaires p ON sav.idPartenaire = p.idPartenaire";

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
                Achats idAchats = new Achats(rs.getInt("idAchats"), rs.getInt("idProduits"), rs.getInt("idUtilisateur"), rs.getDate("dateAchats"));
                ServiceApresVente sav = new ServiceApresVente (idService, description, type, date, status, idPartenaire, idAchats);
                list.add(sav);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(list);
        return list;
    }




    public ServiceApresVente readById(int id) {
        String req = "SELECT sav.idService, sav.description, sav.type, sav.date, sav.status, " +
                "a.idAchats, a.idProduits, a.idUtilisateur, a.dateAchats, " +
                "p.idPartenaire, p.nom, p.type AS partenaire_type, p.adresse, p.telephone, p.email " +
                "FROM service_apres_vente sav " +
                "JOIN achats a ON sav.idAchats = a.idAchats " +
                "LEFT JOIN partenaires p ON sav.idPartenaire = p.idPartenaire " +
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
                Partenaire idPartenaire = new Partenaire(rs.getInt("idPartenaire"), rs.getString("nom"), rs.getString("partenaire_type"), rs.getString("adresse"), rs.getInt("telephone"), rs.getString("email"));
                Achats idAchats = new Achats(rs.getInt("idAchats"), rs.getInt("idProduits"), rs.getInt("idUtilisateur"),date);
                return new ServiceApresVente(idService, description, type, date, status, idPartenaire, idAchats);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<ServiceApresVente> readByIdPartenaire(int id) {
        ResultSet rs = null;
        String req = "SELECT * FROM service_apres_vente WHERE idPartenaire = ?";
        List<ServiceApresVente> list = new ArrayList<>();
        try {
            pst = con.prepareStatement(req);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                ServiceApresVente s = new ServiceApresVente();
                s.setIdService(rs.getInt("idService"));
                s.setDescription(rs.getString("description"));
                s.setType(rs.getString("type"));
                s.setDate(rs.getDate("date"));
                s.setStatus(rs.getBoolean("status"));
                int idPar = rs.getInt("idPartenaire");
                partenaireService ps = new partenaireService();
                Partenaire p = ps.readById(idPar);
                s.setIdPartenaire(p);
                int idAch = rs.getInt("idAchats");
                ProduitsService pro = new ProduitsService();
                Achats A = pro.readbyIdAchat(idAch);
                s.setIdAchats(A);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }




    public void Affecter(ServiceApresVente serviceApresVente,Partenaire p) {
        String req = "UPDATE service_apres_vente SET idPartenaire=?  WHERE idService=?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setInt(1, p.getId());
            pst.setInt(2,serviceApresVente.getIdService());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while NonAffecter ServiceApresVente: " + e.getMessage());
        }
    }

    public void NonAffecter(ServiceApresVente serviceApresVente) {
        String req = "UPDATE service_apres_vente SET idPartenaire=null  WHERE idService=?";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setInt(1, serviceApresVente.getIdService());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while NonAffecter ServiceApresVente: " + e.getMessage());
        }
    }


}