package controllers.UserAdminController;
import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
    private TextField MotDePass;

    @FXML
    void Loginclicked(ActionEvent event) {
        Admin admin = new Admin() ;
//        AdminService adminService =new AdminService();
//        admin.setMailUtilisateur(MailUtilisateur.getText());
//        admin.setMotDePassUtilisateur(MotDePass.getText());
//        admin=adminService.Login(admin);
        admin.setIdUtilisateur(1);
        if(admin.getIdUtilisateur()!=-1){

            try {

                root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        System.out.println(admin);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
