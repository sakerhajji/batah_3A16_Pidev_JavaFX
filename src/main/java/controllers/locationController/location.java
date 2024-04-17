package controllers.locationController;


import Entity.location.Image;
import Entity.location.Location;
import Services.locationService.ImageService;
import Services.locationService.LocationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class location {

    public ImageView locationimg;
    public Label LocationPriceLabel;
    public Label LocationNameLabel;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label adresseLabel;



    @FXML
    private Label descriptionLabel;


    private LocationService locationService = new LocationService();

    @FXML
    private TextField adresseFilterTextField;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private int selectedLocationId;





    @FXML
    private void handleReserveButtonClick(ActionEvent event) {
        if (selectedLocationId == 0) {
            System.out.println(selectedLocationId);
            // If no location is selected, show an alert dialog
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Location Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a location to reserve.");
            alert.showAndWait();
        } else {
            try {
                // Load the reservation interface FXML
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/locationInterface/Reservation.fxml"));
                Parent parent = fxmlLoader.load();

                // Create a new stage for the reservation interface
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.setTitle("Reservation Form");

                // Pass the selected location ID to the ReservationController
                ReservationController reservationController = fxmlLoader.getController();
                reservationController.initialize(selectedLocationId);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to update the chosenFruitCard with the selected location information
    private void updateChosenLocation(Location location) {
        Label locationNameLabel = (Label) chosenFruitCard.lookup("#LocationNameLabel");
        Label locationPriceLabel = (Label) chosenFruitCard.lookup("#LocationPriceLabel");
        ImageView locationImageView = (ImageView) chosenFruitCard.lookup("#locationimg");
        Label adresseLabel = (Label) chosenFruitCard.lookup("#adresseLabel"); // Add this line
        Label descriptionLabel = (Label) chosenFruitCard.lookup("#descriptionLabel"); // Add this line

        // Update labels with location information
        locationNameLabel.setText(location.getType());
        locationPriceLabel.setText("TND" + String.valueOf(location.getPrix()));
        adresseLabel.setText(location.getAdresse()); // Set address label text
        descriptionLabel.setText(location.getDescription()); // Set description label text

        // Fetch the image for the selected location
        ImageService imageService = new ImageService();
        Image image = imageService.getImageByLocationId(location.getIdLocation());
        if (image != null) {
            // Load the image using the URL
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(image.getUrl());
            locationImageView.setImage(fxImage);
        } else {
            // Handle case where no image is available
            locationImageView.setImage(null);
        }
    }


    // Method to handle item click event
    @FXML
    private void handleItemClick(MouseEvent event, int locationId) {
        // Store the selected location ID
        selectedLocationId = locationId;

        // Fetch location details using locationId
        Location clickedLocation = locationService.readById(locationId);

        // Update chosen location
        updateChosenLocation(clickedLocation);

    }
/*
    @FXML
    private void initialize() {
        // Fetch locations from the database
        LocationService locationService = new LocationService();
        List<Location> locations = locationService.readAll();

        // Populate the grid with items representing each location
        for (int i = 4; i < locations.size(); i++) {
            Location location = locations.get(i);

            // Fetch the associated image for the location
            ImageService imageService = new ImageService();
            Image image = imageService.getImageByLocationId(location.getIdLocation());

            // Check if the image is available
            if (image != null) {
                // Create a new item controller for the location
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/locationInterface/item.fxml"));
                AnchorPane itemPane;
                try {
                    itemPane = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ItemController itemController = fxmlLoader.getController();
                // Load the image
                javafx.scene.image.Image fxImage = new javafx.scene.image.Image(image.getUrl());

                // Set the location information in the item controller
                itemController.setLocation(location.getType(), location.getPrix(), fxImage);

                // Add event handler for item click
                itemPane.setOnMouseClicked(this::handleItemClick);

                // Add the item to the grid
                grid.add(itemPane, i % 4, i / 4);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
            } else {
                // Handle case where image is not available
                System.out.println("Image not available for location: " + location.getIdLocation());
            }
        }
    }*/


    @FXML
    private void initialize() {
        // Fetch locations from the database
        List<Location> locations = locationService.readAll();

        // Initialize column and row counters
        int column = 0;
        int row = 1; // Start displaying images from the second row

        // Populate the grid with items representing each location
        for (Location location : locations) {
            // Fetch the associated image for the location
            ImageService imageService = new ImageService();
            Image image = imageService.getImageByLocationId(location.getIdLocation()); // Assuming location.getId() gives the location ID


            // Load the image
            if (image != null) {
                javafx.scene.image.Image fxImage = new javafx.scene.image.Image(image.getUrl());

                // Create a new item controller for the location
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/locationInterface/item.fxml"));
                AnchorPane itemPane;
                try {
                    itemPane = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Item itemController = fxmlLoader.getController();

                // Set the location information in the item controller
                itemController.setLocation(location.getType(), location.getPrix(), fxImage);

                // Set size constraints for the itemPane
                itemPane.setPrefSize(200, 200); // Adjust size as needed

                // Add event handler for item click
                itemPane.setOnMouseClicked(event -> handleItemClick(event, location.getIdLocation())); // Pass location ID to handleItemClick

                // Add the item to the grid
                grid.add(itemPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
            } else {
                // Print "not available" in the console
                System.out.println("Image not available for location: " + location.getIdLocation());
            }

            // Check if column exceeds the maximum allowed (3)
            if (column == 3) {
                column = 0;
                row++;
            }
        }

    }
    // Method to initialize the grid with location data
    private void initializeGrid() {
        // Fetch locations from the database

        List<Location> locations = locationService.readAll();

        // Clear the existing grid
        grid.getChildren().clear();

        // Initialize column and row counters
        int column = 0;
        int row = 1; // Start displaying images from the second row

        // Populate the grid with items representing each location
        for (Location location : locations) {
            // Fetch the associated image for the location
            ImageService imageService = new ImageService();
            Image image = imageService.getImageByLocationId(location.getIdLocation()); // Assuming location.getId() gives the location ID

            // Load the image
            if (image != null) {
                javafx.scene.image.Image fxImage = new javafx.scene.image.Image(image.getUrl());

                // Create a new item controller for the location
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/locationInterface/item.fxml"));
                AnchorPane itemPane;
                try {
                    itemPane = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Item itemController = fxmlLoader.getController();

                // Set the location information in the item controller
                itemController.setLocation(location.getType(), location.getPrix(), fxImage);

                // Set size constraints for the itemPane
                itemPane.setPrefSize(200, 200); // Adjust size as needed

                // Add event handler for item click
                itemPane.setOnMouseClicked(event -> handleItemClick(event, location.getIdLocation())); // Pass location ID to handleItemClick

                // Add the item to the grid
                grid.add(itemPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
            } else {
                // Print "not available" in the console
                System.out.println("Image not available for location: " + location.getIdLocation());
            }

            // Check if column exceeds the maximum allowed (3)
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    // Method to refresh the grid
    public void refreshGrid() {
        initializeGrid();
    }

    public void refreshLocation() {
        initialize();
    }

    public void Publier_location(ActionEvent actionEvent)  {

        try {
            // Load the FXML file of the interface you want to navigate to
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/locationInterface/publierproduit.fxml"));
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

    @FXML
    private void filtrer(ActionEvent event) {

        String adresseFilter = adresseFilterTextField.getText().trim(); // Get the entered address filter

        // Fetch locations from the database based on the address filter
        List<Location> filteredLocations = locationService.fetchLocationsByAddress(adresseFilter);

        // Clear the existing grid
        grid.getChildren().clear();

        // Populate the grid with filtered locations
        int column = 0;
        int row = 1;
        for (Location location : filteredLocations) {
            // Your code to add location items to the grid
        }
    }





    public void clear_filter(ActionEvent actionEvent) {
    }

   /* public void clear_filter(ActionEvent actionEvent) {
        tableView.getItems().clear();
        List<Location> locations = locationService.readAll();
        tableView.getItems().addAll(locations);
    */}
