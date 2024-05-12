package Services.servicePartenaire;
import DataBaseSource.DataSource;
import Entity.entitiesPartenaire.Livraison;
import Entity.entitiesPartenaire.Partenaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LivraisonService {
    private Connection con;
    private Statement ste;
    private PreparedStatement pst;

    public LivraisonService() {
        con= DataSource.getInstance().getCnx();
    }

    public List<Livraison> readAll() {
        String requte="select * from livraison";
        List<Livraison> list=new ArrayList<>();
        try {
            ste=con.createStatement();
            ResultSet rs =ste.executeQuery(requte);
            while(rs.next()){
                list.add(new Livraison(rs.getInt(1),rs.getDate(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Livraison> readAllBySession(int id) {
        String requte = "SELECT l.idLivraison, l.dateLivraison, l.statut, l.idCommande, l.idPartenaire FROM livraison AS l INNER JOIN commands AS c ON c.id = l.idCommande WHERE c.id_client = ?";

        List<Livraison> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(requte)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idLivraison = rs.getInt("idLivraison");
                    Date dateLivraison = rs.getDate("dateLivraison");
                    String statut = rs.getString("statut");
                    int idCommande = rs.getInt("idCommande");
                    int idPartenaire = rs.getInt("idPartenaire");

                    // Création d'une instance de Livraison avec les valeurs récupérées de la base de données
                    Livraison livraison = new Livraison(idLivraison, dateLivraison, statut, idCommande, idPartenaire);
                    list.add(livraison);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Livraison readByID(int id) {
        String requte="select * from livraison where idLivraison = ?";
        Livraison par = null;
        try {
            PreparedStatement ps = con.prepareStatement(requte);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                par = new Livraison(rs.getInt(1),rs.getDate(2),rs.getString(3),rs.getInt(4),rs.getInt(5));
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return par;
    }
}
