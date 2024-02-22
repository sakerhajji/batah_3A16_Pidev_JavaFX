package controllers.controllerPartenaire;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LigneController  {
    @FXML
    private Label id;

    @FXML
    private Label nom;

    @FXML
    private Label type;

    @FXML
    private Label adresse;

    @FXML
    private Label tel;

    @FXML
    private Label email;

    @FXML
    private HBox itemC;

    public Label getId() {
        return id;
    }

    public void setId(String idText) {
        id.setText(idText);
    }

    public Label getNom() {
        return nom;
    }

    public void setNom(String nomText) {
        nom.setText(nomText);
    }

    public Label getType() {
        return type;
    }

    public void setType(String typeText) {
        type.setText(typeText);
    }

    public Label getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresseText) {
        adresse.setText(adresseText);
    }

    public Label getTel() {
        return tel;
    }

    public void setTel(String telText) {
        tel.setText(telText);
    }

    public Label getEmail() {
        return email;
    }

    public void setEmail(String emailText) {
        email.setText(emailText);
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    @FXML
    void DetailleClick(ActionEvent event) {

    }
}
