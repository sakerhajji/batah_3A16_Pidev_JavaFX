package controllers.UserAdminController;

import Entity.UserAdmin.Membre;
import Services.UserAdmineServices.MembreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilUserController implements Initializable {

    public static Membre membre=new Membre() ;
    MembreService membreService = new MembreService() ;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button BtnProduit;

    @FXML
    private Label NameLastName;

    @FXML
    private Circle Profile;

    @FXML
    private Button ServiceButton;

    @FXML
    private Button bntAllocation;

    @FXML
    private Button btnEncheres;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnaccueil;

    @FXML
    private Pane pnlAccuiel;

    @FXML
    private Pane pnlAllocation;

    @FXML
    private Pane pnlEnchere;

    @FXML
    private Pane pnlProduit;

    @FXML
    private Pane pnlSetting;


    public Label getNameLastName() {
        return NameLastName;
    }

    public void setNameLastName(String nameLastName) {
        NameLastName.setText(nameLastName);
    }

    public Circle getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {


        if (profile != null ) {
            Image image = new Image("/images/"+profile);
            this.Profile.setFill(new ImagePattern(image));
        }
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnSettings) {
            pnlSetting.setStyle("-fx-background-color: #FFFFFF");
            pnlSetting.toFront();
            pnlSetting.getChildren().clear(); // Use clear() instead of removeAll()
            loadSXMLSetting();
        } else if (actionEvent.getSource() == btnaccueil) {
            pnlAccuiel.setStyle("-fx-background-color: #d07575");
            pnlAccuiel.toFront();
            // Uncommented block is removed because it's commented out
        } else if (actionEvent.getSource() == btnEncheres) {
            pnlEnchere.setStyle("-fx-background-color: #640680");
            pnlEnchere.toFront();
        } else if (actionEvent.getSource() == btnSignout) {
            membre.clearBinFile();
            try {
                root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/LoginSingUp.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
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
        }
        else if(actionEvent.getSource()==bntAllocation)
        {
            pnlAllocation.setStyle("-fx-background-color: #FFFFFF");
            pnlAllocation.toFront();
            pnlAllocation.getChildren().clear();
            loadSxmlLocation();
        }
    }


    private void loadSXMLSetting() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/SettingPage.fxml"));
            Pane settingPane = loader.load();
            pnlSetting.getChildren().clear();
            pnlSetting.getChildren().setAll(settingPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  void loadSxmlLocation(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/location.fxml"));
            Pane settingPane = loader.load();
            pnlAllocation.getChildren().clear();
            pnlAllocation.getChildren().setAll(settingPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        membre=membre.convertToMembre(membre.loadJsonFromBinFile()) ;
        //NameLastName.setText(membre.getNomUtilisateur()+" "+membre.getPrenomUtilisateur());



    }
}