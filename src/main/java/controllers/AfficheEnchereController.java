package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AfficheEnchereController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idEnchereField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private CheckBox statusCheckbox;

    @FXML
    private TextField prixMinField;

    @FXML
    private TextField prixMaxField;

    @FXML
    private TextField prixActuelField;

    @FXML
    private TextField idProduitField;



}
