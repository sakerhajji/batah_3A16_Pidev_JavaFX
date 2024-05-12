package controllers.locationController;

import Entity.UserAdmin.Admin;
import Entity.location.Location;
import Services.UserAdmineServices.AdminService;
import Services.locationService.LocationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterLocation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> coladresse;

    @FXML
    private TableColumn<?, ?> coldescription;

    @FXML
    private TableColumn<?, ?> coldisponible;

    @FXML
    private TableColumn<?, ?> colprix;

    @FXML
    private TableColumn<?, ?> coltype;

    @FXML
    private TextField id_adresse;

    @FXML
    private TextField id_description;

    @FXML
    private ComboBox<String> id_disponibilite; // Change from TextField to ComboBox<String>

    @FXML
    private TextField id_prix;

    @FXML
    private TextField id_type;

    @FXML
    private TableView<Location> table;

    @FXML
    private ComboBox<String> userComboBox;

    private AdminService userService;
    private LocationService locationService;

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }

    @FXML
    void initialize() {
        userService = new AdminService(); // Initialize the userService field
        populateUserComboBox(userService); // Pass the service to the method

        // Initialize disponibilité ComboBox
        ObservableList<String> disponibiliteOptions = FXCollections.observableArrayList("oui", "non");
        id_disponibilite.setItems(disponibiliteOptions);
        // Add a listener to the TextField to validate input
        id_prix.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Regular expression to match only digits
                id_prix.setText(oldValue); // If input is not a number, revert back to the old value

                // Display error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter numbers only.");
                alert.showAndWait();
            }
        });

    }



    private void populateUserComboBox(AdminService userService) {
        // Fetch users from the database
        List<Admin> users = userService.readAll();

        // Create an ObservableList to store user names
        ObservableList<String> userNames = FXCollections.observableArrayList();

        // Populate the ObservableList with user names
        for (Admin user : users) {
            userNames.add(user.getNomUtilisateur() + " " + user.getPrenomUtilisateur());
        }

        // Set the items of the ComboBox to the user names
        userComboBox.setItems(userNames);
    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        // Check for empty fields
        if(id_prix.getText().isEmpty() || id_type.getText().isEmpty() || id_description.getText().isEmpty() || id_adresse.getText().isEmpty() || id_disponibilite.getValue() == null || userComboBox.getValue() == null) {
            // Alert for empty fields
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields.");
            alert.showAndWait();
            return;
        }

        // Gather data from input fields
        double prix = Double.parseDouble(id_prix.getText());
        String type = id_type.getText();
        String description = id_description.getText();
        String adresse = id_adresse.getText();
        boolean disponibilite = id_disponibilite.getValue().equals("oui"); // Convert String to boolean
        String selectedUserName = userComboBox.getValue().toString();

        // Split the selected user name to extract first and last names
        String[] nameParts = selectedUserName.split(" ");
        String nomUtilisateur = nameParts[0];
        String prenomUtilisateur = nameParts[1];

        // Fetch the corresponding utilisateur object from the database
        Admin selectedUser = userService.getUserByName(nomUtilisateur, prenomUtilisateur);

        // Create a new Location object
        Location newLocation = new Location(adresse, description, disponibilite, prix, type, selectedUser);
        LocationService locationService= new LocationService();

        // Add the new location using the LocationService
        locationService.add(newLocation);

        // Clear input fields
        id_prix.clear();
        id_type.clear();
        id_description.clear();
        id_adresse.clear();
        id_disponibilite.getSelectionModel().clearSelection(); // Clear ComboBox selection
        userComboBox.getSelectionModel().clearSelection();

        // Show success alert
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Location added successfully.");
        successAlert.showAndWait();
    }

    @FXML
    void navigateToAffichageLocation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/affichageLocation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
