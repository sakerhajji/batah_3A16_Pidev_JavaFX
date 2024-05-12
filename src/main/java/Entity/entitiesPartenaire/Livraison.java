package Entity.entitiesPartenaire;

import java.util.Date;

public class Livraison {
    private int idLivraison;
    private Date dateLivraison;
    private String statut;
    private int idCommande;
    private int idPartenaire;

    // Constructeur
    public Livraison(int idLivraison, Date dateLivraison, String statut, int idCommande, int idPartenaire) {
        this.idLivraison = idLivraison;
        this.dateLivraison = dateLivraison;
        this.statut = statut;
        this.idCommande = idCommande;
        this.idPartenaire = idPartenaire;
    }

    // Getters
    public int getIdLivraison() {
        return idLivraison;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public String getStatut() {
        return statut;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public Integer getIdPartenaire() {
        return idPartenaire;
    }

    // Setters
    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public void setIdPartenaire(Integer idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "Livraison{" +
                "idLivraison=" + idLivraison +
                ", dateLivraison=" + dateLivraison +
                ", statut='" + statut + '\'' +
                ", idCommande=" + idCommande +
                ", idPartenaire=" + idPartenaire +
                '}';
    }
}
