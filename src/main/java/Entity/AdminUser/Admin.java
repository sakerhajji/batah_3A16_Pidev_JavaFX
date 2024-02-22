package Entity.AdminUser;

import java.sql.Date;

public class Admin extends Utilisateur {


    public Admin() {
    }

    public Admin(String nomUtilisateur, String prenomUtilisateur, String mailUtilisateur, String motDePassUtilisateur, Date dateDeNaissance, char sexeUtilisateur) {
        super(nomUtilisateur, prenomUtilisateur, mailUtilisateur, motDePassUtilisateur, dateDeNaissance, sexeUtilisateur);
        setRoleUtilisateur('A');
    }

    public Admin(int idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String mailUtilisateur, String motDePassUtilisateur, Date dateDeNaissance, char sexeUtilisateur, String cinUtilisateur, char roleUtilisateur, String numUtilisateur, String pays, String avatar) {
        super(idUtilisateur, nomUtilisateur, prenomUtilisateur, mailUtilisateur, motDePassUtilisateur, dateDeNaissance, sexeUtilisateur, cinUtilisateur, roleUtilisateur, numUtilisateur, pays, avatar);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "idUtilisateur=" + idUtilisateur +
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
