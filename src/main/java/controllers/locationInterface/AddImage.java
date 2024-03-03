package controllers.locationInterface;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.location.Image;
import Entity.location.Location;
import Services.locationService.ImageService;
import Services.locationService.PublierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddImage {

    @FXML
    private ResourceBundle resources;

    private Location location;


    public void setLocation(Location location) {
        this.location = location;
    }

    @FXML
    void addPhotoButtonClicked(ActionEvent event) {
        String x=location.getDescription();
        Location y = new Location();
        PublierService p = new PublierService();
        y=p.getLocationByDescription(x);
        if (y != null) {
            String imageUrl = importer(event);

            if (imageUrl != null) {
                Image image = new Image(imageUrl,y);
                ImageService imageService = new ImageService();
                imageService.add(image);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Location added successfully for location: " + y.getAdresse());
                alert.showAndWait();
                // Close the stage containing the FXML page
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No image selected. Cannot add photo.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Location object is null. Cannot add photo.");
            alert.showAndWait();
        }}

    public String importer(ActionEvent actionEvent) {
        // Create a file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Set the file extension filters if needed
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg","*.jfif")
        );

        // Show the file chooser dialog
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Process the selected file (e.g., save it to a specific location)
        if (selectedFile != null) {
            // Handle the selected file (e.g., save its path to the location object)
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            // You can update your location object with the path to the selected image file here
            return "file:///"+selectedFile.getAbsolutePath();

        } else {
            System.out.println("No file selected");
            return null;
        }
    }





    @FXML
    void initialize() {

    }

}
