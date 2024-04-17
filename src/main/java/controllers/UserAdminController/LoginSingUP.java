package controllers.UserAdminController;

import Apis.OAuthAuthenticator;
import Apis.OAuthGoogleAuthenticator;
import Entity.UserAdmin.Membre;
import Services.UserAdmineServices.MembreService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginSingUP implements Initializable {

    @FXML
    VBox vBox;
    Parent fxml;
    Membre  membreSession =new Membre() ;
    MembreService membreService = new MembreService();

    private Stage stage;
    private Scene scene;
    private Parent root;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(!membreSession.isBinFileEmpty())
        {

        }

        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vBox);
        t.setToX((vBox.getLayoutX() * -1 / 2));
        t.play();
        t.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/SignUp.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @FXML
    void SigninClick(ActionEvent event) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.4), vBox);
        t.setToX((vBox.getLayoutX() * 6.5));
        t.play();
        t.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/LogIn.fxml"));
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
        t.setToX((vBox.getLayoutX() * -1 / 2));
        t.play();
        t.setOnFinished((e) -> {
            try {
                fxml = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/SignUp.fxml"));
                vBox.getChildren().removeAll();
                vBox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @FXML
    void GooleRegister(ActionEvent event) {
        // Your updated JSON data
        String jsonData = "{\"web\":{\"client_id\":\"34198700278-5o1n0105jn1u2m8odioepf3kavqh04dn.apps.googleusercontent.com\",\"project_id\":\"batahapp-415619\",\"auth_uri\":\"https://accounts.google.com/o/oauth2/auth\",\"token_uri\":\"https://oauth2.googleapis.com/token\",\"auth_provider_x509_cert_url\":\"https://www.googleapis.com/oauth2/v1/certs\",\"client_secret\":\"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\",\"redirect_uris\":[\"http://localhost/google/callback/\"]}}";
        // Parse JSON
        JsonObject jsonObject = new Gson().fromJson(jsonData, JsonObject.class);
        JsonObject web = jsonObject.getAsJsonObject("web");
        String gClientId = web.get("client_id").getAsString();
        String gRedir = web.get("redirect_uris").getAsJsonArray().get(0).getAsString(); // Assuming there's only one redirect URI
        String gScope = "https://www.googleapis.com/auth/userinfo.profile " +
                "https://www.googleapis.com/auth/userinfo.email " +
                "https://www.googleapis.com/auth/user.phonenumbers.read";
        String gSecret = web.get("client_secret").getAsString();

        OAuthAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin(() -> {

            membreSession =membreSession.convertToGoogleMembre(auth.getJsonData().toString()) ;
            if(membreService.readByIGoogle(membreSession.getIdGoogle()) == null ){
                membreService.addGoogle(membreSession);
            }
            membreSession=membreService.readByIGoogle(membreSession.getIdGoogle()) ;
            membreSession.clearBinFile();
            membreSession.saveJsonToBinFile(membreSession);
            try {
                if(membreSession.getRoleUtilisateur()=='U') {root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilUser.fxml"));}
                else {root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));}
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                root.setOnMousePressed((MouseEvent events) -> {
                    xOffset = events.getSceneX();
                    yOffset = events.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent events) -> {
                    stage.setX(events.getScreenX() - xOffset);
                    stage.setY(events.getScreenY() - yOffset);
                });
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }
    @FXML
    void closeClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

}

