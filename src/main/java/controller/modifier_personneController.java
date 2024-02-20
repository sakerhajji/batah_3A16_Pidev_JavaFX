package controller;

import entities.partenaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.partenaireService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modifier_personneController  {
    @FXML
    private TextField adressee;

    @FXML
    private TextField nom;

    @FXML
    private TextField tel;

    @FXML
    private TextField type;

    @FXML
    private TextField email;
    private int id;
    public void initData(int id) {
        this.id = id;

        partenaireService ps=new partenaireService();
        partenaire personne = ps.readById(id);


        adressee.setText(personne.getAdresse());
        nom.setText(personne.getNom());
        tel.setText(String.valueOf(personne.getTel()));
        type.setText(personne.getType());
        email.setText(personne.getEmail());
    }
    @FXML
    void modifier(ActionEvent event) {

      partenaireService ps = new partenaireService();
       String ad = adressee.getText();
        String nm = nom.getText();
       int tl = Integer.parseInt(tel.getText());
        String tp = type.getText();
        String eml = email.getText();

        partenaire pr = new partenaire(id,nm, tp, ad, tl, eml);
        ps.update(pr);

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficher_personne.fxml"));
        try {
            Stage stage = (Stage) adressee.getScene().getWindow();
            stage.close();

            //Parent root = loader.load();

            //adressee.getScene().setRoot(root);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }


}
