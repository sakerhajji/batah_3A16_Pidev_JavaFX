package Entity.entitiesProduits;

import java.util.Date;

public class Achats {
    private int idAchats;
    private int idProduits;
    private int idUtilisateur;
    private Date dateAchats;

    public Achats(int idAchats, int idProduits, int idUtilisateur, Date dateAchats) {
        this.idAchats = idAchats;
        this.idProduits = idProduits;
        this.idUtilisateur = idUtilisateur;
        this.dateAchats = dateAchats;
    }

    public int getIdAchats() {
        return idAchats;
    }

    public void setIdAchats(int idAchats) {
        this.idAchats = idAchats;
    }

    public int getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(int idProduits) {
        this.idProduits = idProduits;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Date getDateAchats() {
        return dateAchats;
    }

    public void setDateAchats(Date dateAchats) {
        this.dateAchats = dateAchats;
    }

    @Override
    public String toString() {
        return "Achat{" +
                "idAchats=" + idAchats +
                ", idProduits=" + idProduits +
                ", idUtilisateur=" + idUtilisateur +
                ", dateAchats=" + dateAchats +
                '}';
    }
}
