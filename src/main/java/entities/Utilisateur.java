package entities;

import java.time.LocalDate;

public class Utilisateur {
    private int id;
    private String nomUtilisateur;
    private String prenomUtilisateur;
    private char sexe;
    private LocalDate dateDeNaissance;
    private String adresseEmail;
    private String motDePasse;
    private String adressePostale;
    private String numeroTelephone;
    private String numeroCin;
    private String pays;
    private int nbrProduitAchat;
    private int nbrProduitVendu;
    private int nbrProduit;
    private int nbrPoint;
    private String languePreferree;
    private float evaluationUtilisateur;
    private boolean statutVerificationCompte;
    private String avatar;
    private LocalDate dateInscription;
    private boolean role;

    // Constructeur par défaut
    public Utilisateur() {
    }

    public Utilisateur(int id, String nomUtilisateur, String prenomUtilisateur, char sexe, LocalDate dateDeNaissance, String adresseEmail, String motDePasse, String adressePostale, String numeroTelephone, String numeroCin, String pays, int nbrProduitAchat, int nbrProduitVendu, int nbrProduit, int nbrPoint, String languePreferree, float evaluationUtilisateur, boolean statutVerificationCompte, String avatar, LocalDate dateInscription, boolean role) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.adresseEmail = adresseEmail;
        this.motDePasse = motDePasse;
        this.adressePostale = adressePostale;
        this.numeroTelephone = numeroTelephone;
        this.numeroCin = numeroCin;
        this.pays = pays;
        this.nbrProduitAchat = nbrProduitAchat;
        this.nbrProduitVendu = nbrProduitVendu;
        this.nbrProduit = nbrProduit;
        this.nbrPoint = nbrPoint;
        this.languePreferree = languePreferree;
        this.evaluationUtilisateur = evaluationUtilisateur;
        this.statutVerificationCompte = statutVerificationCompte;
        this.avatar = avatar;
        this.dateInscription = dateInscription;
        this.role = role;
    }

    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, char sexe, LocalDate dateDeNaissance, String adresseEmail, String motDePasse, String adressePostale, String numeroTelephone, String numeroCin, String pays, int nbrProduitAchat, int nbrProduitVendu, int nbrProduit, int nbrPoint, String languePreferree, float evaluationUtilisateur, boolean statutVerificationCompte, String avatar, LocalDate dateInscription, boolean role) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.adresseEmail = adresseEmail;
        this.motDePasse = motDePasse;
        this.adressePostale = adressePostale;
        this.numeroTelephone = numeroTelephone;
        this.numeroCin = numeroCin;
        this.pays = pays;
        this.nbrProduitAchat = nbrProduitAchat;
        this.nbrProduitVendu = nbrProduitVendu;
        this.nbrProduit = nbrProduit;
        this.nbrPoint = nbrPoint;
        this.languePreferree = languePreferree;
        this.evaluationUtilisateur = evaluationUtilisateur;
        this.statutVerificationCompte = statutVerificationCompte;
        this.avatar = avatar;
        this.dateInscription = dateInscription;
        this.role = role;
    }
    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getNumeroCin() {
        return numeroCin;
    }

    public void setNumeroCin(String numeroCin) {
        this.numeroCin = numeroCin;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getNbrProduitAchat() {
        return nbrProduitAchat;
    }

    public void setNbrProduitAchat(int nbrProduitAchat) {
        this.nbrProduitAchat = nbrProduitAchat;
    }

    public int getNbrProduitVendu() {
        return nbrProduitVendu;
    }

    public void setNbrProduitVendu(int nbrProduitVendu) {
        this.nbrProduitVendu = nbrProduitVendu;
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

    public String getLanguePreferree() {
        return languePreferree;
    }

    public void setLanguePreferree(String languePreferree) {
        this.languePreferree = languePreferree;
    }

    public float getEvaluationUtilisateur() {
        return evaluationUtilisateur;
    }

    public void setEvaluationUtilisateur(float evaluationUtilisateur) {
        this.evaluationUtilisateur = evaluationUtilisateur;
    }

    public boolean isStatutVerificationCompte() {
        return statutVerificationCompte;
    }

    public void setStatutVerificationCompte(boolean statutVerificationCompte) {
        this.statutVerificationCompte = statutVerificationCompte;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }





    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nomUtilisateur='" + nomUtilisateur + '\'' +
                ", prenomUtilisateur='" + prenomUtilisateur + '\'' +
                ", sexe=" + sexe +
                ", dateDeNaissance=" + dateDeNaissance +
                ", adresseEmail='" + adresseEmail + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", adressePostale='" + adressePostale + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", numeroCin='" + numeroCin + '\'' +
                ", pays='" + pays + '\'' +
                ", nbrProduitAchat=" + nbrProduitAchat +
                ", nbrProduitVendu=" + nbrProduitVendu +
                ", nbrProduit=" + nbrProduit +
                ", nbrPoint=" + nbrPoint +
                ", languePreferree='" + languePreferree + '\'' +
                ", evaluationUtilisateur=" + evaluationUtilisateur +
                ", statutVerificationCompte=" + statutVerificationCompte +
                ", avatar='" + avatar + '\'' +
                ", dateInscription=" + dateInscription +
                ", role=" + role +
                '}';
    }
}
