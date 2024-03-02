package Entity.location;

import Entity.UserAdmin.Admin;

import java.sql.Date;

public class Reservation {
    private int idReservationLocation;
    private Date dateDebut;
    private Date dateFin;
    private Admin utilisateur;
    private Location location;
    private String notes;

    public Reservation() {
        // Default constructor
    }

    public Reservation(int idReservationLocation, Date dateDebut, Date dateFin, Admin utilisateur, Location location, String notes) {
        this.idReservationLocation = idReservationLocation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.utilisateur = utilisateur;
        this.location = location;
        this.notes = notes;
    }

    public int getIdReservationLocation() {
        return idReservationLocation;
    }

    public void setIdReservationLocation(int idReservationLocation) {
        this.idReservationLocation = idReservationLocation;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Admin getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Admin utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservationLocation=" + idReservationLocation +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", utilisateur=" + utilisateur +
                ", location=" + location +
                ", notes='" + notes + '\'' +
                '}';
    }
}