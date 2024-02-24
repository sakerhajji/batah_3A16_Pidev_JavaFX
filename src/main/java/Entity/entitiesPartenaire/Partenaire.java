package Entity.entitiesPartenaire;

public class Partenaire {
    private int id;
    private String nom;
    private String type;
    private String adresse;
    private int tel;
    private String email;
    private String logo; // Nouveau champ logo
    private int points; // Nouveau champ points

    public Partenaire() {
    }

    public Partenaire(int id, String nom, String type, String adresse, int tel, String email, String logo) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        this.logo = logo;
    }

    public Partenaire(String nom, String type, String adresse, int tel, String email, String logo) {
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "partenaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", adresse='" + adresse + '\'' +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                ", logo='" + logo + '\'' +
                ", points=" + points +
                '}';
    }
}
