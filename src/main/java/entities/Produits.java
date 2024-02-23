package entities;

public class Produits {
    private int idProduit;
    private String type;
    private String description;
    private float prix;
    private String labelle;
    private int status;
    private int periodeGarantie ;
    private Utilisateur id;
//constructor

    public Produits() {

    }

    public Produits(int idProduit, String type, String description, float prix, String labelle, int status, int periodeGarentie, Utilisateur id) {
        this.idProduit = idProduit;
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.labelle = labelle;
        this.status = status;
        this.periodeGarantie = periodeGarentie;
        this.id = id;
    }

    public Produits(String type, String description, float prix, String labelle, int status, int periodeGarantie, Utilisateur id) {
        this.type = type;
        this.description = description;
        this.prix = prix;
        this.labelle = labelle;
        this.status = status;
        this.periodeGarantie = periodeGarantie;
        this.id = id;
    }

    public Produits(int idProduit) {
    }


    public Utilisateur getId() {
        return id;
    }

    public void setId(Utilisateur id) {
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



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPeriodeGarantie() {
        return this.periodeGarantie;
    }

    public void setPeriodeGarantie(int periodeGarantie) {
        this.periodeGarantie = periodeGarantie;
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
                ", status=" + status +
                ", periodeGarentie=" + periodeGarantie +
                ", id=" + id +
                '}';
    }
}