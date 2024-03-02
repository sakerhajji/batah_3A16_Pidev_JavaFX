package Services.locationService;

import DataBaseSource.DataSource;
import Entity.UserAdmin.Admin;
import Entity.location.Location;
import InterfaceServices.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationService implements IService<Location> {

    private Connection conn;
    private PreparedStatement pst;

    public LocationService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Location location) {
        String query = "INSERT INTO location (prix, type, description, adresse, disponibilite, id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setDouble(1, location.getPrix());
            pst.setString(2, location.getType());
            pst.setString(3, location.getDescription());
            pst.setString(4, location.getAdresse());
            pst.setBoolean(5, location.getDisponibilite());
            pst.setInt(6, location.getUtilisateur().getIdUtilisateur()); // Set the user ID
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Location location) {
        String query = "DELETE FROM location WHERE idLocation=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, location.getIdLocation());
            pst.executeUpdate();
            System.out.println("Location with ID " + location.getIdLocation() + " deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Location location) {
        String query = "UPDATE location SET prix=?, type=?, description=?, adresse=?, disponibilite=? WHERE idLocation=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setDouble(1, location.getPrix());
            pst.setString(2, location.getType());
            pst.setString(3, location.getDescription());
            pst.setString(4, location.getAdresse());
            pst.setBoolean(5, location.getDisponibilite());
            pst.setInt(6, location.getIdLocation()); // Set the location ID
            pst.executeUpdate();
            System.out.println("Location updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }







    @Override
    public List<Location> readAll() {
        String query = "SELECT l.*, u.nomUtilisateur, u.prenomUtilisateur " +
                "FROM location l " +
                "JOIN utilisateur u ON l.id = u.id";
        List<Location> locations = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Location location = new Location();
                location.setIdLocation(rs.getInt("idLocation"));
                location.setPrix(rs.getDouble("prix"));
                location.setType(rs.getString("type"));
                location.setDescription(rs.getString("description"));
                location.setAdresse(rs.getString("adresse"));
                location.setDisponibilite(rs.getBoolean("disponibilite"));

                // Create a new utilisateur object and set its attributes
                Admin user = new Admin();
                user.setNomUtilisateur(rs.getString("nomUtilisateur"));
                user.setPrenomUtilisateur(rs.getString("prenomUtilisateur"));

                // Set the utilisateur for the location
                location.setUtilisateur(user);

                locations.add(location);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }




    @Override
    public Location readById(int id) {
        String query = "SELECT * FROM location WHERE idLocation=?";
        Location location = null;
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                location = new Location();
                location.setIdLocation(rs.getInt("idLocation"));
                location.setPrix(rs.getDouble("prix"));
                location.setType(rs.getString("type"));
                location.setDescription(rs.getString("description"));
                location.setAdresse(rs.getString("adresse"));
                location.setDisponibilite(rs.getBoolean("disponibilite"));
            }
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return location;
    }
    public List<Location> readAll2() {
        String query = "SELECT * FROM location";
        List<Location> locations = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Location location = new Location();
                location.setIdLocation(rs.getInt("idLocation"));
                location.setPrix(rs.getDouble("prix"));
                location.setType(rs.getString("type"));
                location.setDescription(rs.getString("description"));
                location.setAdresse(rs.getString("adresse"));
                location.setDisponibilite(rs.getBoolean("disponibilite"));
                locations.add(location);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }
    public List<Location> fetchLocationsByType(String type) {
        String query = "SELECT * FROM location WHERE type=?";
        List<Location> locations = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, type);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Location location = new Location();
                location.setIdLocation(rs.getInt("idLocation"));
                location.setPrix(rs.getDouble("prix"));
                location.setType(rs.getString("type"));
                location.setDescription(rs.getString("description"));
                location.setAdresse(rs.getString("adresse"));
                location.setDisponibilite(rs.getBoolean("disponibilite"));
                locations.add(location);
            }
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }

    public void updateAvailability(int locationId, boolean availability) {
        String query = "UPDATE location SET disponibilite = ? WHERE idLocation = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setBoolean(1, availability);
            pst.setInt(2, locationId);
            pst.executeUpdate();
            System.out.println("Location availability updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}