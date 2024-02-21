package org.example;

import entities.Produits;
import entities.Utilisateur;
import service.ProduitsService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

ProduitsService ps=new ProduitsService();
        // Récupération de l'utilisateur existant par son ID
        Utilisateur existingUser = new Utilisateur();
        existingUser.setId(1);// Remplacez 1 par l'ID de l'utilisateur souhaité

        // Vérification si l'utilisateur existe
        if (existingUser == null) {
            System.out.println("L'utilisateur n'existe pas dans la base de données.");
            return; // Quittez la méthode si l'utilisateur n'existe pas
        }
        Produits nouvelleProduit = new Produits(
                "cccc",
                "www",
                10.0f,
                "ww",
                "ww",
                1,
                50,
                existingUser// Utilisateur récupéré
        );
        System.out.println(nouvelleProduit.getId().getId());
        System.out.println(existingUser.getId());

        // Ajout de l'enchère à la base de données
        ps.add(nouvelleProduit);

        System.out.println("\nListe des produits après l'ajout :");
        List<Produits> prodList = ps.readAll();
        for (Produits prod : prodList) {
            System.out.println(prod);
        }
        System.out.println(ps.getUserById(3));


    }
}