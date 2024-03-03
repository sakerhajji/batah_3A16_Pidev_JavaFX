package controllers.locationController;

import Entity.UserAdmin.Admin;
import Entity.location.Image;
import Entity.location.Location;
import Entity.location.Reservation;
import Services.UserAdmineServices.AdminService;
import Services.locationService.ImageService;
import Services.locationService.LocationService;
import Services.locationService.PublierService;
import Services.locationService.ReservationService;
import controllers.locationInterface.AddImage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PublierProduit {


    @FXML
    private ComboBox<String> id_filter;
    @FXML
    private TextField id_adresse;

    @FXML
    private TextField id_description;

    @FXML
    private TextField id_prix;

    @FXML
    private ComboBox<String> id_type;

    @FXML
    private ComboBox<String> id_utilisateur;

    @FXML
    private TableView<Location> tableView_Location;

    @FXML
    private TableColumn<Location, String> colAdresse;

    @FXML
    private TableColumn<Location, String> colDescription;

    @FXML
    private TableColumn<Location, Double> colPrix;

    @FXML
    private TableColumn<Location, String> colType;

    @FXML
    private TableColumn<Location, String> colUtilisateur;

    private AdminService userService;
    private LocationService locationService;
    private PublierService publierService;
    private Location newLocation;

    @FXML
    void initialize() {
        userService = new AdminService();
        locationService = new LocationService();
        publierService = new PublierService();

        populateUserComboBox(userService);
        ObservableList<String> types = FXCollections.observableArrayList("Voiture", "Maison", "Appartement");
        id_type.setItems(types);
        id_filter.setItems(types);


        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colUtilisateur.setCellValueFactory(cellData -> {
            Location loc = cellData.getValue();
            if (loc.getUtilisateur() != null) {
                return new SimpleStringProperty(loc.getUtilisateur().getNomUtilisateur() + " " +
                        loc.getUtilisateur().getPrenomUtilisateur());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });

        refreshTable();
    }


    public void refreshTable() {
        tableView_Location.getItems().clear();
        List<Location> locations = locationService.readAll3();
        tableView_Location.getItems().addAll(locations);
    }

    private void populateUserComboBox(AdminService userService) {
        List<Admin> users = userService.readAll();
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (Admin user : users) {
            userNames.add(user.getNomUtilisateur() + " " + user.getPrenomUtilisateur());
        }
        id_utilisateur.setItems(userNames);
    }

    @FXML
    public void ajouter(javafx.event.ActionEvent actionEvent) {


        try {
            if (id_prix.getText().isEmpty() || id_type.getValue() == null || id_description.getText().isEmpty() || id_adresse.getText().isEmpty() || id_utilisateur.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                return;
            }

            double prix = Double.parseDouble(id_prix.getText());
            String type = id_type.getValue().toString();
            String description = id_description.getText();
            String adresse = id_adresse.getText();
            String selectedUserName = id_utilisateur.getValue().toString();

            String[] nameParts = selectedUserName.split(" ");
            String nomUtilisateur = nameParts[0];
            String prenomUtilisateur = nameParts[1];

            Admin selectedUser = userService.getUserByName(nomUtilisateur, prenomUtilisateur);

            Location newLocation = new Location(adresse, description, true, prix, type, selectedUser);
            publierService.add(newLocation);
            // Pass the location to the controller of the next interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/add_image.fxml"));
            Parent root = loader.load();
            AddImage nextController = loader.getController();
            nextController.setLocation(newLocation); // Pass the location
            // Show the image interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            id_prix.clear();
            id_type.getSelectionModel().clearSelection();
            id_description.clear();
            id_adresse.clear();
            id_utilisateur.getSelectionModel().clearSelection();


            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to set the Location object
    public void setLocation(Location location) {
        this.newLocation = location;
    }


    public void supprimer(javafx.event.ActionEvent actionEvent) {

        Location selectedLocation = tableView_Location.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {
            // Prompt the user to confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment supprimer cette location?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                ReservationService reservationService = new ReservationService();
                reservationService.deleteReservationsByLocationId(selectedLocation.getIdLocation());

                }
                // Delete the selected location
                // Retrieve the images associated with the location
                ImageService imageService = new ImageService();
                // Delete reservations associated with the location
                List<Image> images = imageService.readAllByLocationId(selectedLocation.getIdLocation());


                // Delete the associated images
                for (Image image : images) {
                    imageService.delete(image);
                }

                publierService.delete(selectedLocation);

                // Refresh the table to reflect the deletion
                refreshTable();

        } else {
            // If no location is selected, show an error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veuillez sélectionner une location à supprimer.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void modifier(javafx.event.ActionEvent actionEvent) {


        // Get the selected location from the table view
        Location selectedLocation = tableView_Location.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {
            try {
                // Load the update location FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/modifierProduit.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the update location FXML
                UpdateProduit controller = loader.getController();

                // Pass the selected location to the controller
                controller.initData(selectedLocation);


                controller.setPublierProduitController(this);

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



    public void filtrer(ActionEvent actionEvent) {
        String selectedType = id_filter.getValue();
        // If no type is selected, do nothing
        if (selectedType == null) {
            return;
        }

        // Fetch locations based on the selected type
        ObservableList<Location> filteredLocations = FXCollections.observableArrayList(locationService.fetchLocationsByType(selectedType));

        // Update the TableView to display only the filtered locations
        tableView_Location.setItems(filteredLocations);

        // Repopulate the ComboBox with all the types
        ObservableList<String> allTypes = FXCollections.observableArrayList("maison", "appartement", "voiture");
        id_filter.setItems(allTypes);
    }

    public void clear_filter(ActionEvent actionEvent) {
        tableView_Location.getItems().clear();
        List<Location> locations = locationService.readAll();
        tableView_Location.getItems().addAll(locations);

    }

    public void afficher_locations(ActionEvent actionEvent)  {

        try {
            // Load the FXML file of the interface you want to navigate to
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/location.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Set the new scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}