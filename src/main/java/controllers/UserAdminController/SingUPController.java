package controllers.UserAdminController;

import Entity.ControleDeSaisieClass.ControleDeSaisieClass;
import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

public class SingUPController implements Initializable {
    @FXML
    private DatePicker DateDeNaissance;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextField MailUtilisateur;

    @FXML
    private PasswordField MotDePass;

    @FXML
    private TextField NomUtlisateur;

    @FXML
    private TextField PrenomUtlisateur;

    @FXML
    private ComboBox<String> Sexe;
    private ControleDeSaisieClass controle=new ControleDeSaisieClass();



    @FXML
    void inscriptionUtilisateur(ActionEvent event) {

        String gender = Sexe.getValue();
        if (controle.checkText(NomUtlisateur.getText()) &&
                controle.checkText(PrenomUtlisateur.getText()) &&
                controle.isValidEmail(MailUtilisateur.getText()) &&
                controle.isDateValidAndOver18(DateDeNaissance.getValue()) &&
                (controle.checkPasswordStrength(MotDePass.getText()) != 0) &&
                Sexe.getValue() != null)
        {
            AdminService adminService = new AdminService();
            if(! adminService.emailExists(MailUtilisateur.getText()) ) {
                LocalDate localDate = DateDeNaissance.getValue();
                Date date = java.sql.Date.valueOf(localDate);
                char sexe = gender.charAt(0);
                Admin admin = new Admin(NomUtlisateur.getText(), PrenomUtlisateur.getText(), MailUtilisateur.getText(), MotDePass.getText(), date, sexe);
                adminService.add(admin);
                try {

                    root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));
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
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Votre Email deja esxiste");
                alert.setHeaderText("Formulaire d'inscription invalide");
                alert.setContentText("Veuillez vérifier votre email  assurez-vous de remplir tous les champs correctement.");
                alert.showAndWait();
                MailUtilisateur.setStyle("-fx-border-color: red");

            }

        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Formulaire d'inscription invalide");
            alert.setContentText("Veuillez vérifier vos informations et assurez-vous de remplir tous les champs correctement.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> options = FXCollections.observableArrayList("Homme", "Femme");
        Sexe.setItems(options);
        NomUtlisateur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.checkText(newValue))
                NomUtlisateur.setStyle("-fx-border-color: transparent");
            else
                NomUtlisateur.setStyle("-fx-border-color: red");
        });
        PrenomUtlisateur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.checkText(newValue))
                PrenomUtlisateur.setStyle("-fx-border-color: transparent");
            else
                PrenomUtlisateur.setStyle("-fx-border-color: red");
        });
        MailUtilisateur.textProperty().addListener((observable, oldValue, newValue) -> {

            if (controle.isValidEmail(newValue))
                MailUtilisateur.setStyle("-fx-border-color: transparent");
            else
                MailUtilisateur.setStyle("-fx-border-color: red");
        });
        MotDePass.textProperty().addListener((observable, oldValue, newValue) -> {

            if (controle.checkPasswordStrength(newValue)==1)
                MotDePass.setStyle("-fx-border-color: transparent");
            else if (controle.checkPasswordStrength(newValue)==0)
                MotDePass.setStyle("-fx-border-color: red");
            else MotDePass.setStyle("-fx-border-color: yellow");
        });
        DateDeNaissance.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (controle.isDateValidAndOver18(newValue))
                DateDeNaissance.setStyle("-fx-border-color: transparent");
            else
                DateDeNaissance.setStyle("-fx-border-color: red");
        });

        Sexe.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null ) Sexe.setStyle("-fx-border-color: transparent");
            else Sexe.setStyle("-fx-border-color: red");
        });




    }





}
