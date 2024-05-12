package Entity.entitiesEncheres;
import Entity.UserAdmin.Admin;

import java.time.LocalDate;

public class ReservationEnchere {
    private int idReservation;
    private Enchere idEnchere;
    private Admin idUser;
    private LocalDate dateReservation;
    private boolean confirmation;

    // Constructors
    public ReservationEnchere() {
    }

    public ReservationEnchere(int idReservation, Enchere idEnchere, Admin idUser, LocalDate dateReservation, boolean confirmation) {
        this.idReservation = idReservation;
        this.idEnchere = idEnchere;
        this.idUser = idUser;
        this.dateReservation = dateReservation;
        this.confirmation = confirmation;
    }

    public ReservationEnchere(Enchere idEnchere, Admin idUser, LocalDate dateReservation, boolean confirmation) {
        this.idEnchere = idEnchere;
        this.idUser = idUser;
        this.dateReservation = dateReservation;
        this.confirmation = confirmation;
    }

    // Getters and setters
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

    public Admin getIdUser() {
        return idUser;
    }

    public void setIdUser(Admin idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
}
