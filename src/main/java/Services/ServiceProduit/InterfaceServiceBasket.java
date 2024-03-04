/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.ServiceProduit;

import Entity.entitiesProduits.Basket;

/**
 *
 * @author medmo
 */
public interface InterfaceServiceBasket {
    public boolean ajouter(int idClient, int idArticle);
    public void supprimerArticle(int idClient, int idArticle);
    public void vider(int idClient);
    public void RemoveFromBasket(int idClient,int idProduit);
    public Basket get(int idClient);
    public void appliquerRemise(int idClient, double remise);
}