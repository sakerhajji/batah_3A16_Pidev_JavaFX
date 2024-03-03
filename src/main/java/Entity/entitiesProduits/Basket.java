/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.entitiesProduits;
/**
 *
 * @author medmo
 */

   import java.util.ArrayList;

public class Basket {
    private int id;
    private int refClient;
    private ArrayList<Produits> articles;
    private double totalCost;
    private String dateAjout;
    private String remise;
    private final double tax= 0.025;
    
     public Basket() {
        articles = new ArrayList<>();
    }
    public Basket(int refClient) {
        this.refClient = refClient;
        articles = new ArrayList<>();
        totalCost = 0;
    }

    public Basket(int refClient, String dateAjout) {
        this.refClient = refClient;
        this.dateAjout = dateAjout;
    }

    public Basket(int id, int refClient, ArrayList<Produits> articles, double totalCost, String dateAjout) {
        this.id = id;
        this.refClient = refClient;
        this.articles = articles;
        this.totalCost = totalCost;
        this.dateAjout = dateAjout;
    }

    public Basket(int id, int refClient, ArrayList<Produits> articles, double totalCost, String dateAjout, String remise) {
        this.id = id;
        this.refClient = refClient;
        this.articles = articles;
        this.totalCost = totalCost;
        this.dateAjout = dateAjout;
        this.remise = remise;
    }

    public void addArticle(Produits article) {
        articles.add(article);
        totalCost += article.getPrix();
    }
   
    public void removeArticle(Produits article) {
        articles.remove(article);
        totalCost -= article.getPrix();
    } 
    
    public ArrayList<Produits> getArticles() {
        return articles;
    }
    
    public double getTotalCostTTC() {
        return articles.stream().mapToDouble(x->x.getPrix()).sum()* (1 + tax);
    }
    public double getTotalCostHT() {
        return articles.stream().mapToDouble(x->x.getPrix()).sum();
    }
   
    public int getRefClient() {
        return refClient;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getDateAjout() {
        return dateAjout;
    }
    
    
    
    public void setRefClient(int refClient) {
        this.refClient = refClient;
    }

    public void setArticles(ArrayList<Produits> articles) {
        this.articles = articles;
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }
    
    
    public double getTax() {
        return tax;
    }
    

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRemise() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise = remise;
    }

    @Override
    public String toString() {
        return "Basket{" + "refClient=" + refClient + ", articles=" + articles + ", totalCost=" + totalCost + ", tax=" + tax + '}';
    }

    
    
    
}


