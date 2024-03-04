package Entity.entitiesProduits;

import Entity.UserAdmin.Membre;

public class Produits {
    private int idProduit;
    private String type;
    private String description;
    private float prix;
    private String labelle;

    private String status;
    private int periodeGarentie ;

    private String photo;


//user id



    private Membre id;
//constructor

    public Produits() {

    }



    public Produits(String type, String description, float prix, String labelle, String status, int periodeGarentie, String photo,Membre id) {
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.labelle = labelle;

        this.status = status;
        this.periodeGarentie = periodeGarentie;
        this.photo=photo;
        this.id = id;
    }
    public Produits(int idProduit,String type, String description, float prix, String labelle, String status, int periodeGarentie,String photo,Membre id) {
        this.idProduit = idProduit;
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.labelle = labelle;

        this.status = status;
        this.periodeGarentie = periodeGarentie;
        this.photo=photo;

        this.id = id;
    }

    public Produits(String labelle, String photo, String description) {
        this.labelle = labelle;
        this.photo = photo;
        this.description = description;
    }

    public Produits(String labelle, float prix,String description ) {
        this.labelle = labelle;
        this.prix = prix;
        this.description = description;
    }

    public Produits(String labelle, float prix,String description , String photo) {
        this.labelle = labelle;
        this.prix = prix;
        this.description = description;
        this.photo = photo;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public Membre getId() {
        return id;
    }

    public void setId(Membre id) {
        this.id = id;
    }

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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPeriodeGarentie() {
        return this.periodeGarentie;
    }

    public void setPeriodeGarentie(int periodeGarentie) {
        this.periodeGarentie = periodeGarentie;
    }


    ///////
//    public ObjectProperty<Utilisateur> utilisateurProperty() {
//        if (utilisateur == null) {
//            utilisateur = new SimpleObjectProperty<>(this, "utilisateur");
//        }
//        return utilisateur;
//    }

    /*public Utlisateur getUtilisateur() {
        return utilisateurProperty().get();
    }

    public void setUtilisateur(Utlisateur utilisateur) {
        utilisateurProperty().set(utilisateur);
    }*/

    @Override
    public String toString() {
        return "Produits{" +
                "idProduit=" + idProduit +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", labelle='" + labelle + '\'' +
                ", photo='" + photo + '\'' +
                ", status=" + status +
                ", periodeGarentie=" + periodeGarentie +
                ", id=" + id +
                '}';
    }
}
