package Services.EnchereService;

import DataBaseSource.DataSource;
import Entity.UserAdmin.Membre;
import Entity.UserAdmin.Utilisateur;
import Entity.entitiesEncheres.Enchere;
import Entity.entitiesEncheres.ReservationEnchere;
import InterfaceServices.IService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationEnchereService implements IService<ReservationEnchere> {


    private final Connection conn;
    private PreparedStatement pst;
    private Statement ste;
    public ReservationEnchereService() {
        conn = DataSource.getInstance().getCnx();
    }


    @Override
    public void add(ReservationEnchere reservationEnchere) {
        String query = "INSERT INTO reservation_enchere (idEnchere,idUser, dateReservation,confirmation )"+"VALUES(?,?,?,?)";
        try(PreparedStatement pst =conn.prepareStatement(query)) {
            pst.setObject(1,reservationEnchere.getIdEnchere().getIdEnchere());
            pst.setObject(2,reservationEnchere.getIdUser().getIdUtilisateur());
            pst.setObject(3,reservationEnchere.getDateReservation());
            pst.setObject(4,reservationEnchere.getConfirmation());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("erreue lors de l'ajout d'une reservation",e);
        }

    }

    @Override
    public void delete(ReservationEnchere reservationEnchere) {
        String query = "DELETE FROM reservation_enchere WHERE idReservation = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, reservationEnchere.getIdReservation());
            pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("erreur lors de la suppression d'une reservation", e);
        }


    }

    @Override
    public void update(ReservationEnchere reservationEnchere) {
        String query= "UPDATE reservation_enchere SET idEnchere=?, idUser=?, dateReservation=?,confirmation=? WHERE idReservation=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setObject(1,reservationEnchere.getIdEnchere().getIdEnchere());
            pst.setObject(2,reservationEnchere.getIdUser().getIdUtilisateur());
            pst.setObject(3,reservationEnchere.getDateReservation());
            pst.setObject(4,reservationEnchere.getConfirmation());
            pst.setObject(5,reservationEnchere.getIdReservation());
            pst.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException("erreur lors de l update d'une reservation", e);
        }
    }

    public List<ReservationEnchere> readAll() {
        String query = "SELECT re.idReservation, re.dateReservation, re.confirmation, " +
                "e.idEnchere AS enchere_idEnchere, " + // Alias pour éviter les conflits de noms de colonnes
                "u.id AS utilisateur_idUtilisateur " + // Alias pour éviter les conflits de noms de colonnes
                "FROM reservation_enchere re " +
                "INNER JOIN encheres e ON re.idEnchere = e.idEnchere " +
                "INNER JOIN utilisateur u ON re.idUser = u.id";
        List<ReservationEnchere> reservations = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                ReservationEnchere reservation = new ReservationEnchere();
                // Set ReservationEnchere details
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setDateReservation(rs.getObject("dateReservation", LocalDate.class));
                reservation.setConfirmation(rs.getBoolean("confirmation"));

                // Create Enchere object and set its details
                Enchere enchere = new Enchere();
                enchere.setIdEnchere(rs.getInt("enchere_idEnchere")); // Utilisation de l'alias
                // Set other attributes of Enchere object from ResultSet if available

                // Create Utilisateur object and set its details
                Membre utilisateur = new Membre(); // Utilisation de la classe Membre
                utilisateur.setIdUtilisateur(rs.getInt("utilisateur_idUtilisateur")); // Utilisation de l'alias
                // Set other attributes of Utilisateur object from ResultSet if available

                // Set Enchere and Utilisateur objects to ReservationEnchere
                reservation.setIdEnchere(enchere);
                reservation.setIdUser(utilisateur);

                // Add ReservationEnchere object to list
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de toutes les réservations", e);
        }
        return reservations;
    }

    @Override
    public ReservationEnchere readById(int id) {
        String query = "SELECT re.*, e.*, u.* " +
                "FROM reservation_enchere re " +
                "INNER JOIN encheres e ON re.idEnchere = e.idEnchere " +
                "INNER JOIN utilisateur u ON re.idUser = u.idUtilisateur " +
                "WHERE re.idReservation = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    ReservationEnchere reservation = new ReservationEnchere();
                    // Set ReservationEnchere details
                    reservation.setIdReservation(rs.getInt("idReservation"));
                    reservation.setDateReservation(rs.getObject("dateReservation", LocalDate.class));
                    reservation.setConfirmation(rs.getBoolean("confirmation"));

                    // Create Enchere object and set its details
                    Enchere enchere = new Enchere();
                    enchere.setIdEnchere(rs.getInt("idEnchere"));
                    // Set other attributes of Enchere object from ResultSet if available

                    // Create Utilisateur object and set its details
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setIdUtilisateur(rs.getInt("idUser"));
                    // Set other attributes of Utilisateur object from ResultSet if available

                    // Set Enchere and Utilisateur objects to ReservationEnchere
                    reservation.setIdEnchere(enchere);
                    reservation.setIdUser((Membre) utilisateur);

                    return reservation;
                } else {
                    // Handle the case where no reservation with the given id was found
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture de la réservation par ID", e);
        }
    }

}
