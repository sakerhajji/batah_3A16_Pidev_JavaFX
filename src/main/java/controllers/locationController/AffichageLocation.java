package controllers.locationController;

import Entity.location.Location;
import Services.locationService.LocationService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AffichageLocation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    private TableView<Location> tableView;

    private final LocationService locationService = new LocationService();

    @FXML
    void initialize() {

    }


}
