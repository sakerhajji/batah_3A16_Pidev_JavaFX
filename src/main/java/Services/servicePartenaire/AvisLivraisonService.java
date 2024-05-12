package Services.servicePartenaire;

import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.AvisLivraison;
import Entity.entitiesPartenaire.Livraison;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisLivraisonService {
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public AvisLivraisonService() {
        con= DataSource.getInstance().getCnx();
    }
    public List<AvisLivraison> readAll() {
        String requte="select * from avislivraison";
        List<AvisLivraison> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs =ste.executeQuery(requte);
            while(rs.next()){
                list.add(new AvisLivraison(rs.getInt(1),rs.getInt(2),rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<AvisLivraison> readByIdLivrainson(int id) {
        String requte="select * from avislivraison where idLivraison= ?";
        List<AvisLivraison> list=new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(requte);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new AvisLivraison(rs.getInt(1),rs.getInt(2),rs.getString(3)));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
