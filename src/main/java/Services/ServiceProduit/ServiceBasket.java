/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.ServiceProduit;
/**
 *
 * @author medmo
 */

import DataBaseSource.DataSource;
import Entity.entitiesProduits.Basket;
import Entity.entitiesProduits.Produits;

import java.sql.*;


public class ServiceBasket implements InterfaceServiceBasket{
Statement ste=null;
Connection conn = DataSource.getInstance().getCnx();

@Override
public boolean ajouter(int idClient, int idProduit) {
    boolean added = false;
    try {
        String selectQuery = "SELECT * FROM basket WHERE id_client=? and id_produit=?";
        PreparedStatement selectPs = conn.prepareStatement(selectQuery);
        selectPs.setInt(2, idProduit);
        selectPs.setInt(1, idClient);
        ResultSet resultSet = selectPs.executeQuery();
        
        if (!resultSet.next()) {
            // idArticle does not exist in the database, add a new entry
            String insertQuery = "INSERT INTO `basket` (`id_client`, `id_produit`) VALUES (?,?)";
            PreparedStatement insertPs = conn.prepareStatement(insertQuery);
            insertPs.setInt(1, idClient);
            insertPs.setInt(2, idProduit);
            insertPs.executeUpdate();
            added = true;
        } else {
            // idArticle already exists in the database
            System.out.println("idProduit already exists in the database");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return added;
}

     @Override
    public void supprimerArticle(int idClient, int idProduit) {
        try {
            String req = "DELETE FROM `basket` WHERE id_client = ? and id_produit = ?";
            PreparedStatement st = conn.prepareStatement(req);
            st.setInt(1, idClient);
            st.setInt(2, idProduit);
            st.executeUpdate();
            System.out.println("Basket item deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void vider(int idClient) {
       try {
            String req = "DELETE FROM `basket` WHERE id_client = ?";
            PreparedStatement st = conn.prepareStatement(req);
            st.setInt(1, idClient);
            st.executeUpdate();
            System.out.println("Basket Empty !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void RemoveFromBasket(int idClient,int idProduit) {
        try {
            String req = "DELETE FROM `basket` WHERE id_client = ? And id_produit= ?";
            PreparedStatement st = conn.prepareStatement(req);
            st.setInt(1, idClient);
            st.setInt(2, idProduit);
            st.executeUpdate();
            System.out.println("Item Removed !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Basket get(int idClient) {
        Basket bask= new Basket();
       // ServiceArticle sa = new ServiceArticle();
        ProduitsService sa=new ProduitsService();
          try {
        String req = "SELECT * FROM `basket` WHERE id_client = ?";
        PreparedStatement pste=conn.prepareStatement(req);
        pste.setInt(1, idClient);
        
        ResultSet result = pste.executeQuery();
        while(result.next()){
           Produits resultArticle = sa.fetchProduitById(result.getInt("id_produit"));
      bask.addArticle(resultArticle);
     
        }
    } catch (SQLException ex) {
         System.out.println(ex);   
    }
          bask.setRefClient(idClient);
         double totalCost = bask.getArticles().stream().mapToDouble(x->x.getPrix()).sum();
         bask.setTotalCost(totalCost);
         
    return bask;
    }
    @Override
    public void appliquerRemise(int idClient, double remise) {
        try {
            String req = "UPDATE `basket` SET remise = ? WHERE id_client = ?";
            PreparedStatement st = conn.prepareStatement(req);
            st.setDouble(1, remise);
            st.setInt(2, idClient);
            st.executeUpdate();
            System.out.println("Remise appliquée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void RemoveProdFromBasket(int idProduit) {
        try {
            // Delete records from the basket where id_produit is the specified idProduit
            String req = "DELETE FROM `basket` WHERE id_produit = ?";
            PreparedStatement st = conn.prepareStatement(req);
            st.setInt(1, idProduit);
            st.executeUpdate();
            System.out.println("Product removed from all baskets!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

