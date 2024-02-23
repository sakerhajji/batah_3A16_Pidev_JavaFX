package controllers.UserAdminController;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginSingUP implements Initializable {

    @FXML
    VBox vBox;
    Parent fxml ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the background of the AnchorPane to transparent

        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX((vBox.getLayoutX()*-1/2));
        t.play();
        t.setOnFinished((e)->{
            try {
                fxml= FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/SignUp.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    @FXML
    void SigninClick(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX((vBox.getLayoutX()*6.5));
        t.play();
        t.setOnFinished((e)->{
            try {
                fxml= FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/LogIn.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }
    @FXML
    void SingUpClicked(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX((vBox.getLayoutX()*-1/2));
        t.play();
        t.setOnFinished((e)->{
            try {
                fxml= FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/SignUp.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}
