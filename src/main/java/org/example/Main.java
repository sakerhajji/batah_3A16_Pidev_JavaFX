package org.example;

import entities.partenaire;
import service.partenaireService;
import util.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {


    public static void main(String[] args) {
        List<partenaire> list=new ArrayList<>();
        partenaire p1=new partenaire("sofienne","type","dd",555,"@sof");
        partenaire p2=new partenaire(2,"sofienne","type","dd",555,"@sof");
        partenaireService p=new partenaireService();
        //p.add(p1);
        //p.addPst(p1);
        //p.delete(p2.getId());
        //p.update(p2);
      // p.readAll().forEach(System.out::println);
        System.out.println(p.readById(2));


    }
    }
