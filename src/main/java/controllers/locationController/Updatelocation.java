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

public class Updatelocation {

    @FXML
    private ComboBox<String> availabilityComboBox;

    @FXML
    private TextField newAddressField;

    @FXML
    private TextField newDescriptionField;

    @FXML
    private TextField newPriceField;

    @FXML
    private TextField newTypeField;

    @FXML
    private ComboBox<String> userComboBox;
    @FXML

    private Label idLocationLabel;

    private Location selectedLocation;
    private LocationService locationService;
    private AjouterLocation ajouterLocationController;





    public void initData(Location location) {
        selectedLocation = location;
        idLocationLabel.setText(Integer.toString(selectedLocation.getIdLocation())); // Set the idLocation in the label
        newPriceField.setText(String.valueOf(selectedLocation.getPrix()));
        newTypeField.setText(selectedLocation.getType());
        newDescriptionField.setText(selectedLocation.getDescription());
        newAddressField.setText(selectedLocation.getAdresse());
        availabilityComboBox.setValue(selectedLocation.getDisponibilite() ? "Oui" : "Non");
    }


    @FXML
    void updateLocation(ActionEvent event) {
        // lahne nekhdhou les information eli badelnehom
        String newPriceText = newPriceField.getText();
        String newType = newTypeField.getText();
        String newDescription = newDescriptionField.getText();
        String newAddress = newAddressField.getText();
        String newAvailability = availabilityComboBox.getValue();


        int idLocation;
        idLocation = Integer.parseInt(idLocationLabel.getText());



        if (newPriceText.isEmpty() || newType.isEmpty() || newDescription.isEmpty() || newAddress.isEmpty() || newAvailability == null) {
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


       // sna3t instance location feha les valeurs jdod eli badalnehom
        Location updatedLocation = new Location(newAddress, newDescription, newAvailability.equals("Available"), newPrice, newType);
        updatedLocation.setIdLocation(idLocation);

        LocationService x = new LocationService();

        x.update(updatedLocation);

        showAlert("Update Successful", " Location modifier  avec succès.");

        closeWindow(event);

        //hedhy fonction refreshtable jebneha mil controller ajouter location bech naamlou refresh lil table
        ajouterLocationController.refreshTable();


    }
    //hedhy instance sna3neha mil controller lekhr bech najmou njibou minha el refresh table
    public void setAjouterLocationController(AjouterLocation ajouterLocationController) {
        this.ajouterLocationController = ajouterLocationController;
    }


    @FXML
    public void initialize() {
        // Initialize locationService
        locationService = new LocationService();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent event) {
        // Get the source node of the event (which is the button)
        Node source = (Node) event.getSource();
        // Get the stage (window) that contains the button
        Stage stage = (Stage) source.getScene().getWindow();
        // Close the stage
        stage.close();
    }



}
