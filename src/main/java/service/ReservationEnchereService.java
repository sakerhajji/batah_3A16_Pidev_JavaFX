//package service;
//
//import entities.Enchere;
//import entities.ReservationEnchere;
//import entities.Utilisateur;
//import utils.DataSource;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReservationEnchereService {
//    private Connection conn;
//    private PreparedStatement pst;
//    private Statement ste;
//    public ReservationEnchereService() {
//        conn = DataSource.getInstance().getCnx();
//    }
//
//    public void add(ReservationEnchere reservationEnchere) {
//        String query = "INSERT INTO reservation_enchere (idEnchere, idUser, dateReservation, confirmation) " +
//                "VALUES (?, ?, ?, ?)";
//        try (PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setInt(1, reservationEnchere.getIdEnchere().getIdEnchere());
//            pst.setInt(2, reservationEnchere.getIdUser().getId());
//            pst.setObject(3, reservationEnchere.getDateReservation());
//            pst.setBoolean(4, reservationEnchere.isConfirmation());
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Erreur lors de l'ajout de la réservation d'enchère", e);
//        }
//    }
//
//    public void delete(ReservationEnchere reservationEnchere) {
//        String query = "DELETE FROM reservation_enchere WHERE idReservation = ?";
//        try (PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setInt(1, reservationEnchere.getIdReservation());
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Erreur lors de la suppression de la réservation d'enchère", e);
//        }
//    }
//
//    public void update(ReservationEnchere reservationEnchere) {
//        String query = "UPDATE reservation_enchere SET idEnchere=?, idUser=?, dateReservation=?, confirmation=? WHERE idReservation=?";
//        try (PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setInt(1, reservationEnchere.getIdEnchere().getIdEnchere());
//            pst.setInt(2, reservationEnchere.getIdUser().getId());
//            pst.setObject(3, reservationEnchere.getDateReservation());
//            pst.setBoolean(4, reservationEnchere.isConfirmation());
//            pst.setInt(5, reservationEnchere.getIdReservation());
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Erreur lors de la mise à jour de la réservation d'enchère", e);
//        }
//    }
//
//
//    public List<ReservationEnchere> readAll() {
//        String query = "SELECT re.*, e.*, u.* " +
//                "FROM reservation_enchere re " +
//                "JOIN enchere e ON re.idEnchere = e.idEnchere " +
//                "JOIN utilisateur u ON re.idUser = u.idUser";
//        List<ReservationEnchere> reservationEnchereList = new ArrayList<>();
//        try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
//            while (rs.next()) {
//                int idReservation = rs.getInt("idReservation");
//                int idEnchere = rs.getInt("idEnchere");
//                LocalDate dateReservation = rs.getObject("dateReservation", LocalDate.class);
//                boolean confirmation = rs.getBoolean("confirmation");
//
//                // Fetch Enchere object by idEnchere
//                Enchere enchere = new EnchereService().readById(idEnchere);
//
//                // Fetch Utilisateur object by idUser
//                Utilisateur utilisateur = new UtilisateurService().readById(rs.getInt("idUser"));
//
//                // Create ReservationEnchere object
//                ReservationEnchere reservationEnchere = new ReservationEnchere(
//                        idReservation,
//                        enchere,
//                        utilisateur,
//                        dateReservation,
//                        confirmation
//                );
//
//                reservationEnchereList.add(reservationEnchere);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Erreur lors de la lecture de toutes les réservations d'enchères", e);
//        }
//        return reservationEnchereList;
//    }
//
//
//    public ReservationEnchere readById(int id) {
//        String query = "SELECT * FROM reservation_enchere WHERE idReservation=?";
//        try (PreparedStatement pst = conn.prepareStatement(query)) {
//            pst.setInt(1, id);
//            try (ResultSet rs = pst.executeQuery()) {
//                if (rs.next()) {
//                    int idReservation = rs.getInt("idReservation");
//                    int idEnchere = rs.getInt("idEnchere");
//                    int idUser = rs.getInt("idUser");
//                    LocalDate dateReservation = rs.getObject("dateReservation", LocalDate.class);
//                    boolean confirmation = rs.getBoolean("confirmation");
//
//                    // Assuming appropriate constructors for Enchere and Utilisateur classes
//                    Enchere enchere = new EnchereService().readById(idEnchere);
//                    Utilisateur utilisateur = new UtilisateurService().readById(idUser);
//
//                    // Assuming appropriate constructor for ReservationEnchere class
//                    return new ReservationEnchere(idReservation, enchere, utilisateur, dateReservation, confirmation);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Erreur lors de la lecture de la réservation d'enchère par ID", e);
//        }
//        return null;
//    }
//
//}
