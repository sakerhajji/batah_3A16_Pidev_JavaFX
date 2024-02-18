package Entity.AdminUser;

import java.util.Date;

public class Membre extends Utlisateur {
    private int nbrProuduitAchat;
    private int nbrProduitVendu;
    private String languePreferree;
    private int nbrProduit;
    private int nbrPoint ;
    private Date dateInscription;

    public Membre(String nomUtlisateur, String prenomUtlisateur, String mailUtlisateur, String motDePassUtlisateur, java.sql.Date dateDeNaissance, char sexeUtlisateur) {
        super(nomUtlisateur, prenomUtlisateur, mailUtlisateur, motDePassUtlisateur, dateDeNaissance, sexeUtlisateur);
    }

    public Membre(int idUtlisateur, String nomUtlisateur, String prenomUtlisateur, String mailUtlisateur, String motDePassUtlisateur, java.sql.Date dateDeNaissance, char sexeUtlisateur, String cinUtlisateur, char roleUtlisateur, String numUtlisateur, String pays, String avatar, int nbrProuduitAchat, int nbrProduitVendu, String languePreferree, int nbrProduit, int nbrPoint, Date dateInscription) {
        super(idUtlisateur, nomUtlisateur, prenomUtlisateur, mailUtlisateur, motDePassUtlisateur, dateDeNaissance, sexeUtlisateur, cinUtlisateur, roleUtlisateur, numUtlisateur, pays, avatar);
        this.nbrProuduitAchat = nbrProuduitAchat;
        this.nbrProduitVendu = nbrProduitVendu;
        this.languePreferree = languePreferree;
        this.nbrProduit = nbrProduit;
        this.nbrPoint = nbrPoint;
        this.dateInscription = dateInscription;
    }
}
