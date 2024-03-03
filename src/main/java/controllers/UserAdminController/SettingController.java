package controllers.UserAdminController;

import Entity.ControleDeSaisieClass.ControleDeSaisieClass;
import Entity.UserAdmin.Membre;
import Services.UserAdmineServices.AdminService;
import Services.UserAdmineServices.MembreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    Membre membre=new Membre();
    MembreService membreService = new MembreService();
    String img  ;

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
    private ChoiceBox<String> pays;

    @FXML
    private TextField Prenom;
    @FXML
    private Text NomPrenom;
    private ControleDeSaisieClass controle=new ControleDeSaisieClass();

    @FXML
    void modifierClicked(ActionEvent event) {
        if (controle.checkText(Nom.getText()) &&
                controle.checkText(Prenom.getText()) &&
                controle.isValidEmail(Email.getText()) &&
                controle.chekNumero(Telephone.getText()) &&
                controle.chekNumero(Numcin.getText()) &&
                controle.isDateValidAndOver18(DatedeNaissance.getValue()) &&
                pays.getValue() != null
        ) {
            LocalDate localDate = DatedeNaissance.getValue();
            Date date = java.sql.Date.valueOf(localDate);
            membre.setNomUtilisateur(Nom.getText());
            membre.setPrenomUtilisateur(Prenom.getText());
            membre.setMailUtilisateur(Email.getText());
            membre.setNumUtilisateur(Telephone.getText());
            membre.setDateDeNaissance(date);
            membre.setCinUtilisateur(Numcin.getText());
            membre.setPays(pays.getValue());
            membre.setAvatar(img);
            System.out.println(membre);
            membreService.updateCard(membre);
            System.out.println("done");

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Formulaire d'inscription invalide");
            alert.setContentText("Veuillez vérifier vos informations et assurez-vous de remplir tous les champs correctement.");
            alert.showAndWait();
        }
    }

    @FXML
    void passUpdateCliked(ActionEvent event) {

        if (membreService.isValidCredentials(membre.getIdUtilisateur(),odlPas.getText())) {
            if (controle.checkPasswordStrength(newPass.getText()) != 0 && !(membreService.getPassword(membre.getIdUtilisateur(),odlPas.getText())==odlPas.getText())) {
                membreService.updatePassword(membre.getIdUtilisateur(), newPass.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Mot De pass Faible");
                alert.setHeaderText("verifier votre Mot de pass ");
                alert.setContentText("Veuillez vérifier vos informations et assurez-vous de remplir tous les champs correctement.");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Mot De pass Incorrect");
            alert.setHeaderText("verifier votre Mot de pass ");
            alert.setContentText("Veuillez vérifier vos informations et assurez-vous de remplir tous les champs correctement.");
            alert.showAndWait();
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        membre = membre.convertToMembre(membre.loadJsonFromBinFile());
        membre=membreService.readById(membre.getIdUtilisateur()) ;
        if (membre != null) {
            if (membre.getNomUtilisateur() != null && membre.getPrenomUtilisateur() != null) {
                NomPrenom.setText(membre.getNomUtilisateur() + " " + membre.getPrenomUtilisateur());
            }
            if (membre.getNumUtilisateur() != null) {
                Telephone.setText(membre.getNumUtilisateur());
            }
            if (membre.getMailUtilisateur() != null) {
                Email.setText(membre.getMailUtilisateur());
            }
            if (membre.getNomUtilisateur() != null) {
                Nom.setText(membre.getNomUtilisateur());
            }
            if (membre.getPrenomUtilisateur() != null) {
                Prenom.setText(membre.getPrenomUtilisateur());
            }
            if (membre.getCinUtilisateur() != null) {
                Numcin.setText(membre.getCinUtilisateur());
            }
            if (membre.getDateDeNaissance() != null) {
                DatedeNaissance.setValue(membre.getDateDeNaissance().toLocalDate());
            }
            if(membre.getPays()!=null)
            {
                pays.setValue(membre.getPays());
            }
            if(membre.getAvatar()!= null)
            {
                Image image = new Image("/images/"+membre.getAvatar());
                Profile.setFill(new ImagePattern(image));
            }
        }

        //controle de saisie
        Nom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.checkText(newValue))
                Nom.setStyle("-fx-border-color: transparent");
            else
                Nom.setStyle("-fx-border-color: red");
        });
        Prenom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.checkText(newValue))
                Prenom.setStyle("-fx-border-color: transparent");
            else
                Prenom.setStyle("-fx-border-color: red");
        });
        Email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.isValidEmail(newValue))
                Email.setStyle("-fx-border-color: transparent");
            else
                Email.setStyle("-fx-border-color: red");
        });
        Telephone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.chekNumero(newValue))
                Telephone.setStyle("-fx-border-color: transparent");
            else
                Telephone.setStyle("-fx-border-color: red");
        });
        Numcin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.chekNumero(newValue))
                Numcin.setStyle("-fx-border-color: transparent");
            else
                Numcin.setStyle("-fx-border-color: red");
        });
        DatedeNaissance.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.isDateValidAndOver18(newValue))
                DatedeNaissance.setStyle("-fx-border-color: transparent");
            else
                DatedeNaissance.setStyle("-fx-border-color: red");
        });

    }
    @FXML
    void UpdateImg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File initialDirectory = new File("src/main/resources/images");
        fileChooser.setInitialDirectory(initialDirectory);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {

            Image image = new Image("/images/"+selectedFile.getName());
            Profile.setFill(new ImagePattern(image));
            img=selectedFile.getName();
            System.out.println(img);
        }

    }

}

