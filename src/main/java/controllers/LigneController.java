package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LigneController implements Initializable {
    @FXML
    private Label DateNaissance;

    @FXML
    private Label Email;

    @FXML
    private Label Nom;

    @FXML
    private Label Prenom;

    @FXML
    private HBox itemC;

    public Label getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(Label dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public Label getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email.setText(email);
    }

    public Label getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom.setText(nom);
    }

    public Label getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom.setText(prenom);
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Nom.setText("saker");
        Prenom.setText("hajji");
        DateNaissance.setText(("28/05/2001"));
        Email.setText("saker.hajji13@gmail.com");



    }

}
