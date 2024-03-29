package controllers.locationController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Item {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    void click(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }
    // Method to set location information in the UI
    public void setLocation(String name, double price, Image image) {
        nameLabel.setText(name);
        priceLable.setText("TND" + String.format("%.2f", price)); // Format price with two decimal places
        img.setImage(image);
    }

}