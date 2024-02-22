package Services.servicePartenaire;

import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.partenaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class partenaireService implements IService<partenaire>{
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public partenaireService() {
        con= DataSource.getInstance().getCnx();
    }
    public void add(partenaire p){
        String requete="insert into partenaires (nom,type,adresse,telephone,email) values ('"+p.getNom()+"','"+p.getType()+"','"+p.getAdresse()+"',"+p.getTel()+",'"+p.getEmail()+"')";
        try {
            ste=con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addPst(partenaire p){
        String requete="insert into partenaires (nom,type,adresse,telephone,email) values (?,?,?,?,?)";
        try {
            pst=con.prepareStatement(requete);
            pst.setString(1,p.getNom());
            pst.setString(2,p.getType());
            pst.setString(3,p.getAdresse());
            pst.setInt(4,p.getTel());
            pst.setString(5,p.getEmail());
            pst.executeUpdate();

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
    public void update(partenaire p) {
        String requete="update partenaires set nom='"+p.getNom()+"',type='"+p.getType()+"',adresse='"+p.getAdresse()+"',telephone="+p.getTel()+",email='"+p.getEmail()+"' where idPartenaire="+p.getId()+"";
        try {
            ste=con.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<partenaire> readAll() {
        String requte="select * from partenaires";
        List<partenaire> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs =ste.executeQuery(requte);
            while(rs.next()){
                list.add(new partenaire(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public partenaire readById(int id) {
        String requete = "SELECT * FROM partenaires WHERE idPartenaire=?";
        partenaire par = null;
        try {
            PreparedStatement ps = con.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                par = new partenaire(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return par;
    }
    public List<partenaire> rechercheParNom(String nom) {
        ResultSet resultSet = null;
        List<partenaire> partenaires = new ArrayList<>();
        try {
            String query = "SELECT * FROM partenaires WHERE nom LIKE ?";
            pst = con.prepareStatement(query);
            pst.setString(1, "%" + nom + "%");
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                partenaire p = new partenaire();
                p.setId(resultSet.getInt(1));
                p.setNom(resultSet.getString(2));
                p.setAdresse(resultSet.getString(3));
                p.setEmail(resultSet.getString(4));
                p.setTel(resultSet.getInt(5));
                p.setType(resultSet.getString(6));

                partenaires.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return partenaires;
    }


}
