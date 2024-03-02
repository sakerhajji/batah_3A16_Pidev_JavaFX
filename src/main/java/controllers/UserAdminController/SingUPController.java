package controllers.UserAdminController;

import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

public class SingUPController implements Initializable {
    @FXML
    private DatePicker DateDeNaissance;

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

    @FXML
    void inscriptionUtilisateur(ActionEvent event) {
        LocalDate localDate = DateDeNaissance.getValue();
           Date date = java.sql.Date.valueOf(localDate);
            String gender = (String) Sexe.getValue();
            char sexe=gender.charAt(0);
        Admin admin =new Admin(NomUtlisateur.getText(),PrenomUtlisateur.getText(),MailUtilisateur.getText(),MotDePass.getText(),date,sexe);
        AdminService adminService =new AdminService();
        adminService.add(admin);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList("Homme", "Femme");
        Sexe.setItems(options);
    }

}
