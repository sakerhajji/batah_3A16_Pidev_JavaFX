package controllers.locationController;

import Entity.location.Location;
import Services.locationService.LocationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;



public class UpdateProduit {

    @FXML
    private Label idLocationLabel;

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField new_adresse;

        @FXML
        private TextField new_description;

        @FXML
        private TextField new_prix;

        @FXML
        private ComboBox<String> new_type;





    private Location selectedLocation;
    private LocationService locationService;
    private PublierProduit publierProduitController;


    public void initData(Location location) {
        selectedLocation = location;
        idLocationLabel.setText(Integer.toString(selectedLocation.getIdLocation())); // Set the idLocation in the label
        new_prix.setText(String.valueOf(selectedLocation.getPrix())); // Set the value in the new_prirx TextField
        new_type.setValue(selectedLocation.getType()); // Set the value in the new_type ComboBox
        new_description.setText(selectedLocation.getDescription()); // Set the value in the new_description TextField
        new_adresse.setText(selectedLocation.getAdresse()); // Set the value in the new_adresse TextField
        // Note: The availabilityComboBox is not present in your FXML file, so it won't be set here.
    }



    @FXML
        void importer(ActionEvent event) {

        }


        @FXML
        void initialize() {
            locationService = new LocationService();

        }

    public void updateLocation(ActionEvent actionEvent) {
        // Retrieve the values from the text fields and combo box
        String newPriceText = new_prix.getText();
        String newType = new_type.getValue();
        String newDescription = new_description.getText();
        String newAddress = new_adresse.getText();

        // Perform validation
        if (newPriceText.isEmpty() || newType.isEmpty() || newDescription.isEmpty() || newAddress.isEmpty()) {
            showAlert("Error", "Veuillez remplir tous les champs");
            return;
        }

        double newPrice;
        try {
            newPrice = Double.parseDouble(newPriceText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Le prix doit être un nombre");
            return;
        }

        // Create a new Location object with the updated values
        Location updatedLocation = new Location(newAddress, newDescription, true, newPrice, newType); // Assuming disponibilite is always true

        // Set the idLocation from the label
        updatedLocation.setIdLocation(Integer.parseInt(idLocationLabel.getText()));

        // Call the update method in the LocationService
        LocationService locationService = new LocationService();
        locationService.update(updatedLocation);

        // Show a confirmation message
        showAlert("Update Successful", "Location modifiée avec succès.");

        // Close the window
        closeWindow(actionEvent);


        // Call the refreshTable method in the AjouterLocationController to refresh the table
        publierProduitController.refreshTable();
    }


        //hedhy instance sna3neha mil controller lekhr bech najmou njibou minha el refresh table
        public void setPublierProduitController(PublierProduit publierProduitController) {
            this.publierProduitController = publierProduitController;
        }



        private void closeWindow(ActionEvent event) {
            // Get the source node of the event (which is the button)
            Node source = (Node) event.getSource();
            // Get the stage (window) that contains the button
            Stage stage = (Stage) source.getScene().getWindow();
            // Close the stage
            stage.close();
        }





    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
