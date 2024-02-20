package org.example;

import entities.Enchere;
import entities.Produit;
import entities.Utilisateur;
import service.EncheresService;
import service.IService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Création d'un service EncheresService
        IService<Enchere> service = new EncheresService();

        // Récupération du produit existant par son ID
        Produit existingProduct = new Produit();
        existingProduct.setIdProduit(5);// Remplacez 1 par l'ID du produit souhaité

        // Vérification si le produit existe
        if (existingProduct == null) {
            System.out.println("Le produit n'existe pas dans la base de données.");
            return; // Quittez la méthode si le produit n'existe pas
        }

        // Récupération de l'utilisateur existant par son ID
        Utilisateur existingUser = new Utilisateur();
        existingUser.setId(4);// Remplacez 1 par l'ID de l'utilisateur souhaité

        // Vérification si l'utilisateur existe
        if (existingUser == null) {
            System.out.println("L'utilisateur n'existe pas dans la base de données.");
            return; // Quittez la méthode si l'utilisateur n'existe pas
        }
        Date d1 = Date.valueOf(LocalDate.now());
        Date d2 = Date.valueOf(LocalDate.now().plusDays(7));
        // Création d'une nouvelle enchère avec l'utilisateur et le produit existants
        Enchere nouvelleEnchere = new Enchere(
                d1, // Date de début (remplacée par la date actuelle)
                d2, // Date de fin (7 jours après la date actuelle)
                true, // Statut de l'enchère
                10.0f, // Prix minimum
                100.0f, // Prix maximum
                50.0f, // Prix actuel
                existingUser, // Utilisateur récupéré
                existingProduct // Produit récupéré
        );
        System.out.println(nouvelleEnchere.getId().getId());
        System.out.println(existingProduct.getIdProduit());

        // Ajout de l'enchère à la base de données
        //service.add(nouvelleEnchere);


        //Test de la méthode readAll() pour vérifier l'ajout
        System.out.println("\nListe des enchères après l'ajout :");
        List<Enchere> enchereList = service.readAll();
        for (Enchere enchere : enchereList) {
            System.out.println(enchere);
        }
    }
}
