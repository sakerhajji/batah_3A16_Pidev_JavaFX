package Services.ServiceApresVentS;

import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.partenaire;
import Entity.entitiesProduits.Achats;
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
        String req = "INSERT INTO service_apres_vente (description, type, date, status, idPartenaire, idAchats) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pst = con.prepareStatement(req)) {
            pst.setString(1, serviceApresVente.getDescription());
            pst.setString(2, serviceApresVente.getType());
            pst.setTimestamp(3, new Timestamp(serviceApresVente.getDate().getTime()));
            pst.setBoolean(4, serviceApresVente.isStatus());
            pst.setInt(5, serviceApresVente.getIdPartenaire().getId());
            pst.setInt(6, serviceApresVente.getIdAchats().getIdAchats());
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
                partenaire idPartenaire = new partenaire(rs.getInt("idPartenaire"), rs.getString("nom"), rs.getString("partenaire_type"), rs.getString("adresse"), rs.getInt("telephone"), rs.getString("email"));
                Achats idAchats = new Achats(rs.getInt("idAchats"), rs.getInt("idProduits"), rs.getInt("idUtilisateur"), rs.getDate("dateAchats"));
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
                partenaire idPartenaire = new partenaire(rs.getInt("idPartenaire"), rs.getString("nom"), rs.getString("partenaire_type"), rs.getString("adresse"), rs.getInt("telephone"), rs.getString("email"));
                Achats idAchats = new Achats(rs.getInt("idAchats"), rs.getInt("idProduits"), rs.getInt("idUtilisateur"),date);
                return new ServiceApresVente(idService, description, type, date, status, idPartenaire, idAchats);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



}
