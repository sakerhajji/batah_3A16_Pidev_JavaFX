package Entity.AdminUser;

import java.sql.Date;

public class Admin extends Utlisateur {


    public Admin() {
    }

    public Admin(String nomUtlisateur, String prenomUtlisateur, String mailUtlisateur, String motDePassUtlisateur, Date dateDeNaissance, char sexeUtlisateur) {
        super(nomUtlisateur, prenomUtlisateur, mailUtlisateur, motDePassUtlisateur, dateDeNaissance, sexeUtlisateur);
        setRoleUtlisateur('A');
    }

    public Admin(int idUtlisateur, String nomUtlisateur, String prenomUtlisateur, String mailUtlisateur, String motDePassUtlisateur, Date dateDeNaissance, char sexeUtlisateur, String cinUtlisateur, char roleUtlisateur, String numUtlisateur, String pays, String avatar) {
        super(idUtlisateur, nomUtlisateur, prenomUtlisateur, mailUtlisateur, motDePassUtlisateur, dateDeNaissance, sexeUtlisateur, cinUtlisateur, roleUtlisateur, numUtlisateur, pays, avatar);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "idUtlisateur=" + idUtlisateur +
                ", nomUtlisateur='" + nomUtlisateur + '\'' +
                ", prenomUtlisateur='" + prenomUtlisateur + '\'' +
                ", mailUtlisateur='" + mailUtlisateur + '\'' +
                ", motDePassUtlisateur='" + motDePassUtlisateur + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                ", sexeUtlisateur=" + sexeUtlisateur +
                ", cinUtlisateur='" + cinUtlisateur + '\'' +
                ", roleUtlisateur=" + roleUtlisateur +
                ", NumUtlisateur='" + NumUtlisateur + '\'' +
                ", pays='" + pays + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
