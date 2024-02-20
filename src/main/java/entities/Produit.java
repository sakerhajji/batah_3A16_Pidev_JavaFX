package entities;

public class Produit {
    private int idProduit;
    private String type;
    private String description;
    private float prix;
    private String labelle;
    private String photo;
    private boolean status;
    private int periodeGarantie;
    private Utilisateur utilisateur;

    public Produit() {
    }

    public Produit(String type, String description, float prix, String labelle, String photo, boolean status, int periodeGarantie, Utilisateur utilisateur) {
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.labelle = labelle;
        this.photo = photo;
        this.status = status;
        this.periodeGarantie = periodeGarantie;
        this.utilisateur = utilisateur;
    }

    public Produit(int idProduit, String type, String description, float prix, String labelle, String photo, boolean status, int periodeGarantie, Utilisateur utilisateur) {
        this.idProduit = idProduit;
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.labelle = labelle;
        this.photo = photo;
        this.status = status;
        this.periodeGarantie = periodeGarantie;
        this.utilisateur = utilisateur;
    }

    // Getters et Setters
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getLabelle() {
        return labelle;
    }

    public void setLabelle(String labelle) {
        this.labelle = labelle;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPeriodeGarantie() {
        return periodeGarantie;
    }

    public void setPeriodeGarantie(int periodeGarantie) {
        this.periodeGarantie = periodeGarantie;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", labelle='" + labelle + '\'' +
                ", photo='" + photo + '\'' +
                ", status=" + status +
                ", periodeGarantie=" + periodeGarantie +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
