package service;

import entities.Produit;
import entities.Utilisateur;

import java.util.List;

public interface IService<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    List<T> readAll();

    T readById(int id);


    Utilisateur getUserById(int i);

    Produit getProduitById(int i);
}
