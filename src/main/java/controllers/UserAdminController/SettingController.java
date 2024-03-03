package controllers.UserAdminController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    @FXML
    private DatePicker DatedeNaissance;

    @FXML
    private TextField Email;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Numcin;

    @FXML
    private Circle Profile;

    @FXML
    private TextField Telephone;

    @FXML
    private TextField newPass;

    @FXML
    private TextField odlPas;

    @FXML
    private ChoiceBox<?> pays;

    @FXML
    private TextField prenom;

    @FXML
    void modifierClicked(ActionEvent event) {
        System.out.println("hello 1 ");

    }

    @FXML
    void passUpdateCliked(ActionEvent event) {
        System.out.println("hello 3 ");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
