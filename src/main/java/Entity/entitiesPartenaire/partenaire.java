package Entity.entitiesPartenaire;

public class partenaire {
    private int id;
    private String nom;
    private String type;
    private String adresse;
    private int tel;
    private String email;

    public partenaire() {
    }

    public partenaire(int id, String nom, String type, String adresse, int tel, String email) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.tel = tel;
        this.email=email;
    }

    public partenaire(String nom, String type, String adresse, int tel, String email) {
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.tel = tel;
        this.email=email;
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

    @Override
    public String toString() {
        return "partenaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", adresse='" + adresse + '\'' +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                '}';
    }
}
