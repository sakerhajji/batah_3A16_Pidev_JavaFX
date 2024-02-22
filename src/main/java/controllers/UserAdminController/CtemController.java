package controllers.UserAdminController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CtemController {
    @FXML
    private Label DateNaissance;
@FXML
    private TextField x ;
     @FXML
    private Label Email;

    @FXML
    private Label Name;

    @FXML
    private Label UserName;

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

    public void setEmail(Label email) {
        Email = email;
    }

    public Label getName() {
        return Name;
    }

    public void setName(Label name) {
        Name = name;
    }

    public Label getUserName() {
        return UserName;
    }

    public void setUserName(Label userName) {
        UserName = userName;
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    public CtemController() {
    }

    public void initialize(URL location, ResourceBundle resources){

x.setText("saker");

    }
}
