package controllers.UserAdminController;
import Entity.ControleDeSaisieClass.ControleDeSaisieClass;
import Entity.UserAdmin.Admin;
import Entity.UserAdmin.Membre;
import Services.UserAdmineServices.AdminService;
import Services.UserAdmineServices.MembreService;
import controllers.controllerPartenaire.emailPartenaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField MailUtilisateur;

    @FXML
    private PasswordField MotDePass;
    private double xOffset = 0;
    private double yOffset = 0;
    private  Admin admin = new Admin() ;
    private AdminService adminService =new AdminService() ;
    private ControleDeSaisieClass controle = new ControleDeSaisieClass();

    @FXML
    void Loginclicked(ActionEvent event) {
        admin.setIdUtilisateur(-1);
        admin.setMailUtilisateur(MailUtilisateur.getText());
        admin.setMotDePassUtilisateur(MotDePass.getText());
        admin=adminService.Login(admin);

        if(controle.isValidEmail(MailUtilisateur.getText()) && controle.checkPasswordStrength(MotDePass.getText())!= 0 && admin.getIdUtilisateur()!=-1 )
        {

            emailPartenaire.sendEmail(admin.getMailUtilisateur(),"Login","vous avez recu login de votre compte a Batah");
            try {
                    if (admin.getRoleUtilisateur()=='A')
                    {root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));}
                    else {
                        Membre membre = new Membre() ;
                        MembreService membreService =new MembreService();
                        membre.setIdUtilisateur(admin.getIdUtilisateur());
                        membre=membreService.readById(membre.getIdUtilisateur()) ;
                        membre.saveJsonToBinFile(membre);
                        root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilUser.fxml"));}
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



        }

        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("verifer votre mail et Mots de pass svp ");
            alert.setHeaderText("Formulaire Login invalide");
            alert.setContentText("Veuillez vérifier votre email et votre  Mots de pass   assurez-vous de remplir tous les champs correctement.");
            alert.showAndWait();
            MailUtilisateur.setStyle("-fx-border-color: red");
            MotDePass.setStyle("-fx-border-color: red");

        }







    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MailUtilisateur.textProperty().addListener((observable, oldValue, newValue) -> {

            if (controle.isValidEmail(newValue))
                MailUtilisateur.setStyle("-fx-border-color: transparent");
            else
                MailUtilisateur.setStyle("-fx-border-color: red");
        });
        MotDePass.textProperty().addListener((observable, oldValue, newValue) -> {

            if (controle.checkPasswordStrength(newValue) != 0)
                MotDePass.setStyle("-fx-border-color: transparent");
            else
                MotDePass.setStyle("-fx-border-color: red");

        });


    }
}
