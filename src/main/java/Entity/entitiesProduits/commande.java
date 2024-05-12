package Entity.entitiesProduits;

import java.util.Date;

public class commande {
    private int id;
    private int idClient;
    private Date dateCommande;
    private String modeLivraison;
    private String modePaiement;
    private double coutTotal;
    private String etatCommande;
    private String adresse;
public commande(){}
    // Constructeur
    public commande(int id, int idClient, Date dateCommande, String modeLivraison, String modePaiement, double coutTotal, String etatCommande, String adresse) {
        this.id = id;
        this.idClient = idClient;
        this.dateCommande = dateCommande;
        this.modeLivraison = modeLivraison;
        this.modePaiement = modePaiement;
        this.coutTotal = coutTotal;
        this.etatCommande = etatCommande;
        this.adresse = adresse;
    }
    public commande(int id, int idClient, Date dateCommande,  String adresse) {
        this.id = id;
        this.idClient = idClient;
        this.dateCommande = dateCommande;

        this.adresse = adresse;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public String getModeLivraison() {
        return modeLivraison;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public String getAdresse() {
        return adresse;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setModeLivraison(String modeLivraison) {
        this.modeLivraison = modeLivraison;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", dateCommande=" + dateCommande +
                ", modeLivraison='" + modeLivraison + '\'' +
                ", modePaiement='" + modePaiement + '\'' +
                ", coutTotal=" + coutTotal +
                ", etatCommande='" + etatCommande + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
