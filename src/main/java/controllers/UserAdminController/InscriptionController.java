package controllers.UserAdminController;

import Entity.AdminUser.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class InscriptionController {

    @FXML
    private PasswordField CMotDesPassUtlisatuers;

    @FXML
    private PasswordField MotDePass;

    @FXML
    private DatePicker DateDeNaissance;

    @FXML
    private TextField MailUtilisateur;

    @FXML
    private TextField NomUtlisateur;

    @FXML
    private TextField PrenomUtlisateur;

    @FXML
    private ChoiceBox<String> sexe;

    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Homme", "Femme");
        sexe.setValue("Homme");
        sexe.setItems(options);
    }

    @FXML
    void inscriptionUtilisateur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichage.xml.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }

    // Optionally, you can add a method to handle form submission and data processing
    // @FXML
    // void processForm(ActionEvent event) {
    //     LocalDate localDate = DateDeNaissance.getValue();
    //     Date date = java.sql.Date.valueOf(localDate);
    //     String gender = sexe.getValue();
    //
    //     // Now you can process the form data (e.g., create an Admin object and save it to the database)
    // }
}

