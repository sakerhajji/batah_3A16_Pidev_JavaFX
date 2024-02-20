package entities;

import java.time.LocalDate;

public class ReservationEnchere {
    private int idReservation;
    private Enchere idEnchere;

    public ReservationEnchere(Enchere idEnchere, Utilisateur idUser, LocalDate dateReservation, boolean confirmation) {
        this.idEnchere = idEnchere;
        this.idUser = idUser;
        this.dateReservation = dateReservation;
        this.confirmation = confirmation;
    }

    private Utilisateur idUser;
    private LocalDate dateReservation;
    private boolean confirmation;

    public ReservationEnchere() {
        // Constructeur par défaut
    }

    public ReservationEnchere(int idReservation, Enchere idEnchere, Utilisateur idUser, LocalDate dateReservation, boolean confirmation) {
        this.idReservation = idReservation;
        this.idEnchere = idEnchere;
        this.idUser = idUser;
        this.dateReservation = dateReservation;
        this.confirmation = confirmation;
    }

    // Getters et setters
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Enchere getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(Enchere idEnchere) {
        this.idEnchere = idEnchere;
    }

    public Utilisateur getIdUser() {
        return idUser;
    }

    public void setIdUser(Utilisateur idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    // Méthode toString() pour afficher les informations de la réservation
    @Override
    public String toString() {
        return "ReservationEnchere{" +
                "idReservation=" + idReservation +
                ", idEnchere=" + idEnchere +
                ", idUser=" + idUser +
                ", dateReservation=" + dateReservation +
                ", confirmation=" + confirmation +
                '}';
    }
}
