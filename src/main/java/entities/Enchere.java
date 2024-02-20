package entities;


import java.sql.Date;

public class Enchere {
    private int idEnchere;
    private Date dateDebut;
    private Date dateFin;
    private boolean status;
    private float prixMin;
    private float prixMax;
    private float prixActuelle;
    private Utilisateur id; // Je suppose que Utilisateur est une classe
    private Produit idProduit; // Je suppose que Produit est une classe

    // Constructeurs
    public Enchere(int idEnchere, Date dateDebut, Date dateFin, boolean status, float prixMin, float prixMax, float prixActuelle, Utilisateur id, Produit idProduit) {
        this.idEnchere = idEnchere;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.prixMin = prixMin;
        this.prixMax = prixMax;
        this.prixActuelle = prixActuelle;
        this.id = id;
        this.idProduit = idProduit;
    }



    // Getters et Setters
    public int getIdEnchere() {
        return idEnchere;
    }

    public Enchere(Date dateDebut, Date dateFin, boolean status, float prixMin, float prixMax, float prixActuelle, Utilisateur id, Produit idProduit) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.prixMin = prixMin;
        this.prixMax = prixMax;
        this.prixActuelle = prixActuelle;
        this.id = id;
        this.idProduit = idProduit;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(float prixMin) {
        this.prixMin = prixMin;
    }

    public float getPrixMax() {
        return prixMax;
    }

    public void setPrixMax(float prixMax) {
        this.prixMax = prixMax;
    }

    public float getPrixActuelle() {
        return prixActuelle;
    }

    public void setPrixActuelle(float prixActuelle) {
        this.prixActuelle = prixActuelle;
    }

    public Utilisateur getId() {
        return id;
    }

    public void setId(Utilisateur id) {
        this.id = id;
    }

    public Produit getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Produit idProduit) {
        this.idProduit = idProduit;
    }
    public int getIdUtilisateur() {
        return id.getId();
    }
    public int getIddProduit() {
        return idProduit.getIdProduit();
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "idEnchere=" + idEnchere +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", status=" + status +
                ", prixMin=" + prixMin +
                ", prixMax=" + prixMax +
                ", prixActuelle=" + prixActuelle +
                ", id=" + id +
                ", idProduit=" + idProduit +
                '}';
    }
// Autres méthodes si nécessaire
}
