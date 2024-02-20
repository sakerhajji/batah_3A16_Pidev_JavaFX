package controller;

import entities.partenaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.partenaireService;

import java.io.IOException;

public class Ajouter_personneController {

    @FXML
    private TextField adresse;

    @FXML
    private TextField nom;

    @FXML
    private TextField tel;

    @FXML
    private TextField type;

    @FXML
    private TextField email;

    @FXML
    void addPerson(ActionEvent event) {
        String ad = adresse.getText();
        String nm = nom.getText();
        int tl = Integer.parseInt(tel.getText());
       String tp = type.getText();
        String eml = email.getText();
       partenaire p = new partenaire(nm, tp, ad, tl, eml);
       partenaireService ps = new partenaireService();
        ps.add(p);


        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficher_personne.fxml"));
        try {
            Stage stage = (Stage) adresse.getScene().getWindow();
            stage.close();
            //Parent root = loader.load();

            //adresse.getScene().setRoot(root);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
