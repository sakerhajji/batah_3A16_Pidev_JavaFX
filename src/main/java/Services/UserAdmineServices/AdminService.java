package Services.UserAdmineServices;

import DataBaseSource.DataSource;
import Entity.AdminUser.Admin;
import Entity.AdminUser.Membre;
import InterfaceServices.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService implements IService<Admin> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public AdminService() {
        conn= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Admin admin) {
        String requete = "INSERT INTO utilisateur (nomUtilisateur, prenomUtilisateur, adresseEmail, dateDeNaissance, sexe, motDePasse,role) VALUES (?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, admin.getNomUtlisateur());
            pst.setString(2, admin.getPrenomUtlisateur());
            pst.setString(3, admin.getMailUtlisateur());
            pst.setDate(4, admin.getDateDeNaissance());
            pst.setString(5, String.valueOf(admin.getSexeUtlisateur()));
            pst.setString(6, admin.getMotDePassUtlisateur());
            pst.setString(7, String.valueOf(admin.getRoleUtlisateur()));

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void delete(Admin admin) {
        String requete = "DELETE FROM utilisateur WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, admin.getIdUtlisateur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Admin admin) {
        String requete = "UPDATE utilisateur SET nomUtilisateur = ?, prenomUtilisateur = ?, adresseEmail = ?, motDePasse = ?, dateDeNaissance = ?, sexe = ?, numeroCin = ?, role = ?, numeroTelephone = ?, pays = ?, avatar = ? WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, admin.getNomUtlisateur());
            pst.setString(2, admin.getPrenomUtlisateur());
            pst.setString(3, admin.getMailUtlisateur());
            pst.setString(4, admin.getMotDePassUtlisateur());
            pst.setDate(5, new java.sql.Date(admin.getDateDeNaissance().getTime()));
            pst.setString(6, String.valueOf(admin.getSexeUtlisateur()));
            pst.setString(7, admin.getCinUtlisateur());
            pst.setString(8, String.valueOf(admin.getRoleUtlisateur()));
            pst.setString(9, admin.getNumUtlisateur());
            pst.setString(10, admin.getPays());
            pst.setString(11, admin.getAvatar());
            pst.setInt(12, admin.getIdUtlisateur());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<Admin> readAll() {
        List<Admin> admins = new ArrayList<>();
        String requete = "SELECT * FROM utilisateur";

        try {
            ste = conn.createStatement();
            ResultSet resultSet = ste.executeQuery(requete);
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setIdUtlisateur(resultSet.getInt("id"));
                admin.setNomUtlisateur(resultSet.getString("nomUtilisateur"));
                admin.setPrenomUtlisateur(resultSet.getString("prenomUtilisateur"));
                admin.setMailUtlisateur(resultSet.getString("adresseEmail"));
                admin.setMotDePassUtlisateur(resultSet.getString("motDePasse"));
                admin.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                admin.setSexeUtlisateur(resultSet.getString("sexe").charAt(0));
                admin.setCinUtlisateur(resultSet.getString("numeroCin"));
                admin.setRoleUtlisateur(resultSet.getString("role").charAt(0));
                admin.setNumUtlisateur(resultSet.getString("numeroTelephone"));
                admin.setPays(resultSet.getString("pays"));
                admin.setAvatar(resultSet.getString("avatar"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }


    @Override
    public Admin readById(int id) {
        Admin admin = null;
        String requete = "SELECT * FROM utilisateur WHERE id = ?";

        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                admin = new Admin();
                admin.setIdUtlisateur(resultSet.getInt("id"));
                admin.setNomUtlisateur(resultSet.getString("nomUtilisateur"));
                admin.setPrenomUtlisateur(resultSet.getString("prenomUtilisateur"));
                admin.setMailUtlisateur(resultSet.getString("adresseEmail"));
                admin.setMotDePassUtlisateur(resultSet.getString("motDePasse"));
                admin.setDateDeNaissance(resultSet.getDate("dateDeNaissance"));
                admin.setSexeUtlisateur(resultSet.getString("sexe").charAt(0));
                admin.setCinUtlisateur(resultSet.getString("numeroCin"));
                admin.setRoleUtlisateur(resultSet.getString("role").charAt(0));
                admin.setNumUtlisateur(resultSet.getString("numeroTelephone"));
                admin.setPays(resultSet.getString("pays"));
                admin.setAvatar(resultSet.getString("avatar"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admin;
    }

}
