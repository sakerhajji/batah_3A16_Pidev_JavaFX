package controllers.UserAdminController;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginSingUP implements Initializable {

    @FXML
    VBox vBox;

    @FXML
    AnchorPane parent; // Note: Corrected the variable name to match the FXML id

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the background of the AnchorPane to transparent

        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX((vBox.getLayoutX()*-2));

        t.play();
    }
}
