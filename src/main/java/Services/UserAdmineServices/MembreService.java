package Services.UserAdmineServices;
import DataBaseSource.DataSource;
import Entity.UserAdmin.Admin;
import Entity.UserAdmin.Membre;
import InterfaceServices.IService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreService implements IService<Membre> {

    private  Connection conn=DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    @Override
    public void add(Membre membre) {

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
}