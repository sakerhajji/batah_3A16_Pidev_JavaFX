package Services.locationService;

import DataBaseSource.DataSource;
import Entity.location.Reservation;
import Entity.location.Location;
import Entity.UserAdmin.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private Connection conn;
    private PreparedStatement pst;

    public ReservationService() {
        conn = DataSource.getInstance().getCnx();
    }

    // Method to add a new reservation to the database
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservation_location (dateDebut, dateFin, idUtilisateur, idLocation, notes) VALUES (?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setDate(1, reservation.getDateDebut());
            pst.setDate(2, reservation.getDateFin());
            pst.setInt(3, reservation.getUtilisateur().getIdUtilisateur()); // Assuming getIdUtilisateur() returns the user ID
            pst.setInt(4, reservation.getLocation().getIdLocation()); // Assuming getIdLocation() returns the location ID
            pst.setString(5, reservation.getNotes());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to delete a reservation from the database
    public void deleteReservation(int reservationId) {
        String query = "DELETE FROM reservation_location WHERE id_reservation_location=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, reservationId);
            pst.executeUpdate();
            System.out.println("Reservation with ID " + reservationId + " deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update an existing reservation in the database
    public void updateReservation(Reservation reservation) {
        // Implement update logic here
    }
    public void deleteReservationsByLocationId(int locationId) {
        String query = "DELETE FROM reservation_location WHERE idLocation=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, locationId);
            pst.executeUpdate();
            System.out.println("Reservations for location with ID " + locationId + " deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // Method to retrieve all reservations from the database
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation_location";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setIdReservationLocation(rs.getInt("id_reservation_location"));
                reservation.setDateDebut(rs.getDate("dateDebut"));
                reservation.setDateFin(rs.getDate("dateFin"));

                // Create a new user object and set its attributes
                Admin user = new Admin();
                user.setIdUtilisateur(rs.getInt("idUtilisateur")); // Assuming this is the user ID
                reservation.setUtilisateur(user);

                // Create a new location object and set its attributes
                Location location = new Location();
                location.setIdLocation(rs.getInt("idLocation")); // Assuming this is the location ID
                reservation.setLocation(location);

                reservation.setNotes(rs.getString("notes"));
                reservations.add(reservation);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }
}