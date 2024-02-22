package Entity.UserAdmin;

import java.util.Date;

public class Membre extends Utilisateur {
    private int nbrProuduitAchat;
    private int nbrProduitVendu;
    private String languePreferree;
    private int nbrProduit;
    private int nbrPoint ;
    private Date dateInscription;

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
}
