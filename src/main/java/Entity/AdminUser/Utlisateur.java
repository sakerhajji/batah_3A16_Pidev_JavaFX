package Entity.AdminUser;

import java.sql.Date;

public class Utlisateur {
    protected int idUtlisateur;
    protected String nomUtlisateur;
    protected String prenomUtlisateur;
    protected String mailUtlisateur;
    protected String motDePassUtlisateur ;
    protected Date dateDeNaissance ;
    protected char sexeUtlisateur;
    protected String cinUtlisateur;
    protected char roleUtlisateur;

    protected String NumUtlisateur;

    protected String pays;
    protected String avatar;


    public Utlisateur() {
    }

    public Utlisateur(String nomUtlisateur, String prenomUtlisateur, String mailUtlisateur, String motDePassUtlisateur, Date dateDeNaissance, char sexeUtlisateur) {
        this.nomUtlisateur = nomUtlisateur;
        this.prenomUtlisateur = prenomUtlisateur;
        this.mailUtlisateur = mailUtlisateur;
        this.motDePassUtlisateur = motDePassUtlisateur;
        this.dateDeNaissance = dateDeNaissance;
        this.sexeUtlisateur = sexeUtlisateur;
    }

    public Utlisateur(int idUtlisateur, String nomUtlisateur, String prenomUtlisateur, String mailUtlisateur, String motDePassUtlisateur, Date dateDeNaissance, char sexeUtlisateur, String cinUtlisateur, char roleUtlisateur, String numUtlisateur, String pays, String avatar) {
        this.idUtlisateur = idUtlisateur;
        this.nomUtlisateur = nomUtlisateur;
        this.prenomUtlisateur = prenomUtlisateur;
        this.mailUtlisateur = mailUtlisateur;
        this.motDePassUtlisateur = motDePassUtlisateur;
        this.dateDeNaissance = dateDeNaissance;
        this.sexeUtlisateur = sexeUtlisateur;
        this.cinUtlisateur = cinUtlisateur;
        this.roleUtlisateur = roleUtlisateur;
        NumUtlisateur = numUtlisateur;
        this.pays = pays;
        this.avatar = avatar;
    }

    public int getIdUtlisateur() {
        return idUtlisateur;
    }

    public void setIdUtlisateur(int idUtlisateur) {
        this.idUtlisateur = idUtlisateur;
    }

    public String getNomUtlisateur() {
        return nomUtlisateur;
    }

    public void setNomUtlisateur(String nomUtlisateur) {
        this.nomUtlisateur = nomUtlisateur;
    }

    public String getPrenomUtlisateur() {
        return prenomUtlisateur;
    }

    public void setPrenomUtlisateur(String prenomUtlisateur) {
        this.prenomUtlisateur = prenomUtlisateur;
    }

    public String getMailUtlisateur() {
        return mailUtlisateur;
    }

    public void setMailUtlisateur(String mailUtlisateur) {
        this.mailUtlisateur = mailUtlisateur;
    }

    public String getMotDePassUtlisateur() {
        return motDePassUtlisateur;
    }

    public void setMotDePassUtlisateur(String motDePassUtlisateur) {
        this.motDePassUtlisateur = motDePassUtlisateur;
    }

    public char getRoleUtlisateur() {
        return roleUtlisateur;
    }

    public void setRoleUtlisateur(char roleUtlisateur) {
        this.roleUtlisateur = roleUtlisateur;
    }

    public String getCinUtlisateur() {
        return cinUtlisateur;
    }

    public void setCinUtlisateur(String cinUtlisateur) {
        this.cinUtlisateur = cinUtlisateur;
    }

    public String getNumUtlisateur() {
        return NumUtlisateur;
    }

    public void setNumUtlisateur(String numUtlisateur) {
        NumUtlisateur = numUtlisateur;
    }

    public char getSexeUtlisateur() {
        return sexeUtlisateur;
    }

    public void setSexeUtlisateur(char sexeUtlisateur) {
        this.sexeUtlisateur = sexeUtlisateur;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
}
