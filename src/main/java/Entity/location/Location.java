package Entity.location;

import Entity.UserAdmin.Admin;

public class Location {
    private int idLocation;
    private double prix;
    private String type;
    private String description;
    private String adresse;
    private boolean disponibilite;
    private Admin utilisateur; // Reference to the utilisateur object


    // Constructors
    public Location() {
    }




    @Override
    public String toString() {
        return "Location{" +
                "idLocation=" + idLocation +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", adresse='" + adresse + '\'' +
                ", disponibilite=" + disponibilite +
                ", utilisateur=" + utilisateur +
                '}';
    }

    public Location(String adresse, String description, boolean disponibilite, double prix, String type, Admin utilisateur) {
        this.adresse = adresse;
        this.description = description;
        this.disponibilite = disponibilite;
        this.prix = prix;
        this.type = type;
        this.utilisateur = utilisateur; // Set the utilisateur field
    }
    public Location(String adresse, String description, boolean disponibilite, double prix, String type) {
        this.adresse = adresse;
        this.description = description;
        this.disponibilite = disponibilite;
        this.prix = prix;
        this.type = type;

    }

    public Location(int idLocation, double prix, String type, String description, String adresse, boolean disponibilite, Admin utilisateur) {
        this.idLocation = idLocation;
        this.prix = prix;
        this.type = type;
        this.description = description;
        this.adresse = adresse;
        this.disponibilite = disponibilite;
        this.utilisateur = utilisateur; // Set the utilisateur field
    }

    // Getters and setters
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public boolean getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Admin getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Admin utilisateur) {
        this.utilisateur = utilisateur;
    }
}
