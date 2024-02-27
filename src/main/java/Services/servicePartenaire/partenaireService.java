package Services.servicePartenaire;

import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.Partenaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class partenaireService implements IServicePartenaire<Partenaire> {
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public partenaireService() {
        con= DataSource.getInstance().getCnx();
    }

    public void add(Partenaire p){
        String requete="insert into partenaires (nom,type,adresse,telephone,email,logo) values ('"+p.getNom()+"','"+p.getType()+"','"+p.getAdresse()+"',"+p.getTel()+",'"+p.getEmail()+"','"+p.getLogo()+"')";
        try {
            ste=con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Partenaire p) {
        String requete="update partenaires set nom='"+p.getNom()+"',type='"+p.getType()+"',adresse='"+p.getAdresse()+"',telephone="+p.getTel()+",email='"+p.getEmail()+"',logo='"+p.getLogo()+"' where idPartenaire="+p.getId()+"";
        try {
            ste=con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(int id) {
        String requete = "DELETE FROM partenaires WHERE idPartenaire=?";
        try {
            PreparedStatement ps = con.prepareStatement(requete);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Partenaire> readAll() {
        String requte="select * from partenaires";
        List<Partenaire> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs =ste.executeQuery(requte);
            while(rs.next()){
                list.add(new Partenaire(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6), rs.getString(8),rs.getInt(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Partenaire readById(int id) {
        String requete = "SELECT * FROM partenaires WHERE idPartenaire=?";
        Partenaire par = null;
        try {
            PreparedStatement ps = con.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                par = new Partenaire(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(8));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return par;
    }

    public List<Partenaire> rechercheParNom(String nom) {
        ResultSet resultSet = null;
        List<Partenaire> Partenaires = new ArrayList<>();
        try {
            String query = "SELECT * FROM partenaires WHERE nom LIKE ?";
            pst = con.prepareStatement(query);
            pst.setString(1, "%" + nom + "%");
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Partenaire p = new Partenaire();
                p.setId(resultSet.getInt(1));
                p.setNom(resultSet.getString(2));
                p.setAdresse(resultSet.getString(3));
                p.setEmail(resultSet.getString(4));
                p.setTel(resultSet.getInt(5));
                p.setType(resultSet.getString(6));
                p.setLogo(resultSet.getString(8));


                Partenaires.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Partenaires;
    }


}
