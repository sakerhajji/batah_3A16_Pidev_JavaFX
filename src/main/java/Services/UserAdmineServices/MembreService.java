package Services.UserAdmineServices;
import DataBaseSource.DataSource;
import Entity.UserAdmin.Admin;
import Entity.UserAdmin.Membre;
import InterfaceServices.IService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreService implements IService<Membre> {

    private  Connection conn=DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    @Override
    public void add(Membre member) {
        String requete = "INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur, adresseEmail, dateDeNaissance, sexe, motDePasse,role) VALUES (?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, member.getNomUtilisateur());
            pst.setString(2, member.getPrenomUtilisateur());
            pst.setString(3, member.getMailUtilisateur());
            pst.setDate(4, member.getDateDeNaissance());
            pst.setString(5, String.valueOf(member.getSexeUtilisateur()));
            pst.setString(6, member.getMotDePassUtilisateur());
            pst.setString(7, String.valueOf(member.getRoleUtilisateur()));

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Membre membre) {

    }

    @Override
    public void update(Membre membre) {

    }

    @Override
    public List<Membre> readAll() {
        List<Membre> Membres = new ArrayList<>();
        String requete = "SELECT * FROM utilisateur";

        try {
            ste = conn.createStatement();
            ResultSet resultSet = ste.executeQuery(requete);
            while (resultSet.next()) {
                Membre M = new Membre();
                M.setIdUtilisateur(resultSet.getInt("id"));
                M.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                M.setPrenomUtilisateur(resultSet.getString("prenomUtilisateur"));
                M.setMailUtilisateur(resultSet.getString("adresseEmail"));
                M.setMotDePassUtilisateur(resultSet.getString("motDePasse"));
                M.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                M.setSexeUtilisateur(resultSet.getString("sexe").charAt(0));
                M.setCinUtilisateur(resultSet.getString("numeroCin"));
                M.setRoleUtilisateur(resultSet.getString("role").charAt(0));
                M.setNumUtilisateur(resultSet.getString("numeroTelephone"));
                M.setPays(resultSet.getString("pays"));
                M.setAvatar(resultSet.getString("avatar"));
                Membres.add(M);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Membres;
    }


    @Override
    public Membre readById(int id) {
        Membre Membre=new Membre();
        String requete = "SELECT * FROM utilisateur WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                Membre = new Membre();
                Membre.setIdUtilisateur(resultSet.getInt("id"));
                Membre.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                Membre.setPrenomUtilisateur(resultSet.getString("prenomUtilisateur"));
                Membre.setMailUtilisateur(resultSet.getString("adresseEmail"));
                Membre.setMotDePassUtilisateur(resultSet.getString("motDePasse"));
                Membre.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                Membre.setSexeUtilisateur(resultSet.getString("sexe").charAt(0));
                Membre.setCinUtilisateur(resultSet.getString("numeroCin"));
                Membre.setRoleUtilisateur(resultSet.getString("role").charAt(0));
                Membre.setNumUtilisateur(resultSet.getString("numeroTelephone"));
                Membre.setPays(resultSet.getString("pays"));
                Membre.setAvatar(resultSet.getString("avatar"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Membre;
    }
    public boolean emailExists(String email) {
        boolean exists = false;
        String requete = "SELECT COUNT(*) FROM utilisateur WHERE adresseEmail = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, email);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = (count > 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exists;
    }
    public Membre readByIGoogle(String id) {
        Membre membre=new Membre();
        String requete = "SELECT * FROM utilisateur WHERE idGoogle = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                membre = new Membre();
                membre.setIdUtilisateur(resultSet.getInt("id"));
                membre.setNomUtilisateur(resultSet.getString("nomUtilisateur"));
                membre.setPrenomUtilisateur(resultSet.getString("prenomUtilisateur"));
                membre.setMailUtilisateur(resultSet.getString("adresseEmail"));
                membre.setMotDePassUtilisateur(resultSet.getString("motDePasse"));
                membre.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                membre.setMailUtilisateur(resultSet.getString("adresseEmail"));
                // Check if the value retrieved from resultSet.getString("sexe") is null
                String sexe = resultSet.getString("sexe");
                if (sexe != null && !sexe.isEmpty()) {
                    membre.setSexeUtilisateur(sexe.charAt(0));
                }
                membre.setCinUtilisateur(resultSet.getString("numeroCin"));
                membre.setRoleUtilisateur(resultSet.getString("role").charAt(0));
                membre.setNumUtilisateur(resultSet.getString("numeroTelephone"));
                membre.setPays(resultSet.getString("pays"));
                membre.setAvatar(resultSet.getString("avatar"));
            }
            else return null ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return membre;
    }
    public void addGoogle(Membre member) {
        String requete = "INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur,role , statutVerificationCompte , idGoogle , adresseEmail ,dateInscription) VALUES (?, ?, ?, ?, ?,?,?)";
        member.setStatutVerificationCompte(1);
        member.setRoleUtilisateur('U');
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, member.getNomUtilisateur());
            pst.setString(2, member.getPrenomUtilisateur());
            pst.setString(3,String.valueOf(member.getRoleUtilisateur()));
            pst.setInt(4,member.getStatutVerificationCompte());
            pst.setString(5, member.getIdGoogle());
            pst.setString(6,member.getMailUtilisateur());
            Date currentDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
            pst.setDate(7,sqlDate);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCard(Membre membre) {
        String requete = "UPDATE utilisateur SET nomUtilisateur = ?, prenomUtilisateur = ?, adresseEmail = ?, numeroTelephone = ?, numeroCin = ?, dateDeNaissance = ? , avatar = ? , pays = ? WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, membre.getNomUtilisateur());
            pst.setString(2, membre.getPrenomUtilisateur());
            pst.setString(3, membre.getMailUtilisateur());
            pst.setString(4, membre.getNumUtilisateur());
            pst.setString(5, membre.getCinUtilisateur());
            pst.setDate(6, membre.getDateDeNaissance());
            pst.setString(7,membre.getAvatar());
            pst.setString(8,membre.getPays());
            pst.setInt(9, membre.getIdUtilisateur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isValidCredentials(int id, String password) {
        String requete = "SELECT COUNT(*) FROM utilisateur WHERE id = ? AND motDePasse = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.setString(2, password);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Returns true if count is greater than 0, indicating existence
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false; // Member not found
    }
    public void updatePassword(int id, String newPassword) {
        String requete = "UPDATE utilisateur SET motDePasse = ? WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, newPassword);
            pst.setInt(2, id);

            int rowsUpdated = pst.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update password. No rows affected.");
            }
            else System.out.println("Done");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getPassword(int id, String password) {
        String requete = "SELECT motDePasse FROM utilisateur WHERE id = ? AND motDePasse = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.setString(2, password);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("motDePasse"); // Returns the password if the credentials are valid
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // Member not found or invalid credentials
    }


}