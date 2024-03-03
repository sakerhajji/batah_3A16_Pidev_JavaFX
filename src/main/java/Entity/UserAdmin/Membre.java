package Entity.UserAdmin;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;

public class Membre extends Utilisateur implements Serializable {
    private int nbrProuduitAchat;
    private int nbrProduitVendu;
    private String languePreferree;
    private int nbrProduit;
    private int nbrPoint ;
    private Date dateInscription;
    private int  statutVerificationCompte = 1 ;

    private String idGoogle ;
 public Membre(){}

    public Membre(int id,String nom,String prenom)
    {
        this.idUtilisateur=id;
        this.nomUtilisateur=nom;
        this.prenomUtilisateur=prenom;
    }
    public Membre(String nomUtilisateur, String prenomUtilisateur, String mailUtilisateur, String motDePassUtilisateur, java.sql.Date dateDeNaissance, char sexeUtilisateur) {
        super(nomUtilisateur, prenomUtilisateur, mailUtilisateur, motDePassUtilisateur, dateDeNaissance, sexeUtilisateur);
    }

    public Membre(int idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String mailUtilisateur, String motDePassUtilisateur, java.sql.Date dateDeNaissance, char sexeUtilisateur, String cinUtilisateur, char roleUtilisateur, String numUtilisateur, String pays, String avatar, int nbrProuduitAchat, int nbrProduitVendu, String languePreferree, int nbrProduit, int nbrPoint, Date dateInscription) {
        super(idUtilisateur, nomUtilisateur, prenomUtilisateur, mailUtilisateur, motDePassUtilisateur, dateDeNaissance, sexeUtilisateur, cinUtilisateur, roleUtilisateur, numUtilisateur, pays, avatar);
        this.nbrProuduitAchat = nbrProuduitAchat;
        this.nbrProduitVendu = nbrProduitVendu;
        this.languePreferree = languePreferree;
        this.nbrProduit = nbrProduit;
        this.nbrPoint = nbrPoint;
        this.dateInscription = dateInscription;
    }


    public int getStatutVerificationCompte() {
        return statutVerificationCompte;
    }

    public void setStatutVerificationCompte(int statutVerificationCompte) {
        this.statutVerificationCompte = statutVerificationCompte;
    }

    public int getNbrProuduitAchat() {
        return nbrProuduitAchat;
    }

    public void setNbrProuduitAchat(int nbrProuduitAchat) {
        this.nbrProuduitAchat = nbrProuduitAchat;
    }

    public int getNbrProduitVendu() {
        return nbrProduitVendu;
    }

    public void setNbrProduitVendu(int nbrProduitVendu) {
        this.nbrProduitVendu = nbrProduitVendu;
    }

    public String getLanguePreferree() {
        return languePreferree;
    }

    public void setLanguePreferree(String languePreferree) {
        this.languePreferree = languePreferree;
    }

    public int getNbrProduit() {
        return nbrProduit;
    }

    public void setNbrProduit(int nbrProduit) {
        this.nbrProduit = nbrProduit;
    }

    public int getNbrPoint() {
        return nbrPoint;
    }

    public void setNbrPoint(int nbrPoint) {
        this.nbrPoint = nbrPoint;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }
    public  Membre convertToMembre(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Membre.class);
    }
    public  Membre convertToGoogleMembre(String jsonString) {
        Gson gson = new Gson();
        GoogleUser googleUser = gson.fromJson(jsonString, GoogleUser.class);

        // Extracting relevant fields and creating a new Membre object
        Membre membre = new Membre();
        membre.setIdGoogle(googleUser.getId());
        membre.setNomUtilisateur(googleUser.getGiven_name());
        membre.setPrenomUtilisateur(googleUser.getFamily_name());
        membre.setMailUtilisateur(googleUser.getEmail());      // You may handle other fields similarly or leave them as default values

        return membre;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "nbrProuduitAchat=" + nbrProuduitAchat +
                ", nbrProduitVendu=" + nbrProduitVendu +
                ", languePreferree='" + languePreferree + '\'' +
                ", nbrProduit=" + nbrProduit +
                ", nbrPoint=" + nbrPoint +
                ", dateInscription=" + dateInscription +
                ", statutVerificationCompte=" + statutVerificationCompte +
                ", idGoogle='" + idGoogle + '\'' +
                ", idUtilisateur=" + idUtilisateur +
                ", nomUtilisateur='" + nomUtilisateur + '\'' +
                ", prenomUtilisateur='" + prenomUtilisateur + '\'' +
                ", mailUtilisateur='" + mailUtilisateur + '\'' +
                ", motDePassUtilisateur='" + motDePassUtilisateur + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                ", sexeUtilisateur=" + sexeUtilisateur +
                ", cinUtilisateur='" + cinUtilisateur + '\'' +
                ", roleUtilisateur=" + roleUtilisateur +
                ", NumUtilisateur='" + NumUtilisateur + '\'' +
                ", pays='" + pays + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}
