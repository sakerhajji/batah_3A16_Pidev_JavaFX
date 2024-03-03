package Entity.entitiesProduits;

import Entity.UserAdmin.Membre;

import java.util.Date;

public class Achats {
    private int idAchats;
    private Produits idProduits;
    private Membre idUtilisateur;
    private Date dateAchats;

    public Achats() {
    }

    public Achats(int idAchats, Produits idProduits, Membre idUtilisateur, Date dateAchats) {
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

    public Produits getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(Produits idProduits) {
        this.idProduits = idProduits;
    }

    public Membre getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Membre idUtilisateur) {
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
