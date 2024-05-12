package Entity.entitiesEncheres;

import Entity.entitiesProduits.Produits;

import java.time.LocalDate;

public class Enchere {
    private int idEnchere;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean status;
    private float prixMin;
    private float prixMax;
    private float prixActuelle;
    private int nbrParticipants;
    private Produits idProduit;

    public Enchere() {
    }

    public Enchere(int idEnchere, LocalDate dateDebut, LocalDate dateFin, boolean status, float prixMin, float prixMax, float prixActuelle,int nbrParticipants, Produits idProduit) {
        this.idEnchere = idEnchere;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.prixMin = prixMin;
        this.prixMax = prixMax;
        this.prixActuelle = prixActuelle;
        this.nbrParticipants=nbrParticipants;
        this.idProduit = idProduit;
    }

    public Enchere(LocalDate dateDebut, LocalDate dateFin, boolean status, float prixMin, float prixMax, float prixActuelle,int nbrParticipants, Produits idProduit) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.prixMin = prixMin;
        this.prixMax = prixMax;
        this.prixActuelle = prixActuelle;
        this.nbrParticipants=nbrParticipants;
        this.idProduit = idProduit;
    }

    public int getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(int idEnchere) {
        this.idEnchere = idEnchere;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
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

    public int getNbrParticipants() {
        return nbrParticipants;
    }

    public void setNbrParticipants(int nbrParticipants) {
        this.nbrParticipants = nbrParticipants;
    }

    public Produits getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Produits idProduit) {
        this.idProduit = idProduit;
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
                ", nbrParticipants=" + nbrParticipants +
                ", idProduit=" + idProduit +
                '}';
    }
}
