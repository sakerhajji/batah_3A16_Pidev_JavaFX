package controllers.locationController;

import Entity.location.Location;
import Services.locationService.LocationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class AffichageLocation implements Initializable {

    @FXML
    private TableView<Location> tableView;

    @FXML
    private TableColumn<Location, Integer> colId;

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

    private LocationService locationService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationService = new LocationService(); // Assuming you have a LocationService class

        colId.setCellValueFactory(new PropertyValueFactory<>("idLocation"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
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
        List<Location> locations = locationService.readAll();
        tableView.getItems().addAll(locations);
    }
}
