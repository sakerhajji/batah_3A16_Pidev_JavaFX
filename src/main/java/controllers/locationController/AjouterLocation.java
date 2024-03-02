package controllers.locationController;

import Entity.UserAdmin.Admin;
import Entity.location.Location;
import Services.UserAdmineServices.AdminService;
import Services.locationService.LocationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterLocation {
    @FXML
    private TableView<Location> tableView;



    @FXML
    private TableColumn<Location, String> colType;

    @FXML
    private TableColumn<Location, String> colDescription;

    @FXML
    private TableColumn<Location, Double> colPrix;

    @FXML
    private TableColumn<Location, String> colAdresse;

    @FXML
    private TableColumn<Location, Boolean> colDisponibilite;

    @FXML
    private TableColumn<Location, String> colUtilisateur;


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

            // Get the selected location from the table view
            Location selectedLocation = tableView.getSelectionModel().getSelectedItem(); //je selectionne  la ligne
            if (selectedLocation != null) { //kan variable selectedLocation not null yaani fama haja selectionneer hawka chnaamlo delete sinon  tjini l msg ali lout a
                // Confirm the delete operation with the user
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Supprimer la location");
                alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer la location ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User confirmed, delete the location
                    locationService.delete(selectedLocation);

                    // Refresh the table view to reflect the changes
                    refreshTable();
                }
            } else {
                // No location selected, show an error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Aucune location sélectionnée");
                alert.setContentText("Veuillez sélectionner une location à supprimer");
                alert.showAndWait();
            }
        }



    @FXML
    void update(ActionEvent event) {
        // Get the selected location from the table view
        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {
            try {
                // Load the update location FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/updatelocation.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the update location FXML
                Updatelocation controller = loader.getController();

                // Pass the selected location to the controller
                controller.initData(selectedLocation);

                // Pass the AjouterLocation controller instance to the Updatelocation controller
                controller.setAjouterLocationController(this);

                // Create a new stage for the update location window
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // No location selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Aucune location sélectionnée");
            alert.setContentText("Veuillez sélectionner une location à modifier");
            alert.showAndWait();
        }
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
                alert.setContentText("Le prix doit être un nombre");
                alert.showAndWait();
            }
        });
        locationService = new LocationService(); // Assuming you have a LocationService class


        colType.setCellValueFactory(new PropertyValueFactory<>("type")); //n3abii table mta3ii
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colDisponibilite.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        colUtilisateur.setCellValueFactory(cellData -> {
            Location loc = cellData.getValue();
            if (loc.getUtilisateur() != null) {
                return new SimpleStringProperty(loc.getUtilisateur().getNomUtilisateur() + " " +
                        loc.getUtilisateur().getPrenomUtilisateur());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        // Load locations directly into the table
        List<Location> locations = locationService.readAll(); //affichiithom houni
        tableView.getItems().addAll(locations); //nafichihom f tableview mta3i
        refreshTable();
    }
    public void refreshTable() {
        tableView.getItems().clear(); // Clear the existing items in the table
        List<Location> locations = locationService.readAll(); // Read updated data from the service
        tableView.getItems().addAll(locations); // Add the updated data to the table
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
            alert.setContentText("Veuillez remplir tous les champs");
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

        // ba3d man3amar les champs f formulaire kif najouterrr directement yitfas5oo
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
        successAlert.setContentText("Location ajouter avec succès");
        successAlert.showAndWait();
        refreshTable();
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
