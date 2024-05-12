package Services.ServiceProduit;

import DataBaseSource.DataSource;

import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.Livraison;
import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesProduits.commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class commandeService {
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public commandeService() {
        con= DataSource.getInstance().getCnx();
    }
    public List<commande> readAll() {
        String requte="select * from commands";
        List<commande> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs =ste.executeQuery(requte);
            while(rs.next()){
                list.add(new commande(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public commande readByID(int idC) {
        String requte="select * from commands where id= ?";
        commande par = null;
        try {
            PreparedStatement ps = con.prepareStatement(requte);
            ps.setInt(1, idC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                par = new commande(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getString(4));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return par;
    }
}
