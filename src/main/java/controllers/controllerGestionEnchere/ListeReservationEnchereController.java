package controllers.controllerGestionEnchere;

import Entity.entitiesEncheres.ReservationEnchere;
import Services.EnchereService.ReservationEnchereService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ListeReservationEnchereController {

    @FXML
    private TableView<ReservationEnchere> reservationsTableView;

    @FXML
    private TableColumn<ReservationEnchere, Integer> idReservationColumn;

    @FXML
    private TableColumn<ReservationEnchere, LocalDate> dateReservationColumn;

    @FXML
    private TableColumn<ReservationEnchere, Boolean> confirmationColumn;

    @FXML
    private TableColumn<ReservationEnchere, Integer> idEnchereColumn;

    @FXML
    private TableColumn<ReservationEnchere, Integer> idUserColumn;

    @FXML
    private TableColumn<ReservationEnchere, Void> actionColumn;

    private final ReservationEnchereService reservationEnchereService = new ReservationEnchereService();

    public void initialize() {
        // Initialize TableColumn cell values
        idReservationColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdReservation()).asObject());
        dateReservationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateReservation()));
        confirmationColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getConfirmation()));
        idEnchereColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdEnchere().getIdEnchere()).asObject());
        idUserColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdUser().getIdUtilisateur()).asObject());

        // Add action buttons to actionColumn
        actionColumn.setCellFactory(param -> new TableCell<ReservationEnchere, Void>() {
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");

            {
                updateButton.setOnAction(event -> {
                    ReservationEnchere reservation = getTableView().getItems().get(getIndex());
                    handleUpdate(reservation);
                });

                deleteButton.setOnAction(event -> {
                    ReservationEnchere reservation = getTableView().getItems().get(getIndex());
                    handleDelete(reservation);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(updateButton, deleteButton));
                }
            }
        });

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        // Fetch all ReservationEnchere objects from the database
        ObservableList<ReservationEnchere> reservationEnchereList = FXCollections.observableArrayList(reservationEnchereService.readAll());

        // Populate the TableView with the fetched data
        reservationsTableView.setItems(reservationEnchereList);
    }

    private void handleUpdate(ReservationEnchere reservation) {
        try {
            // Charger le fichier FXML de l'interface de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceEnchere/ModifierReservationEnchere.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de l'interface de modification
            ModifierReservationEnchereController modifierController = loader.getController();

            // Initialiser les données dans l'interface de modification
            modifierController.initData(reservation);

            // Créer une nouvelle fenêtre pour l'interface de modification
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier la réservation");

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleDelete(ReservationEnchere reservation) {
        reservationEnchereService.delete(reservation);
        loadDataIntoTableView();
    }

    @FXML
    private void handleAjouter(ActionEvent event) {
        try {
            // Charger le fichier FXML de l'interface d'ajout de réservation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceEnchere/AjouterReservationEnchere.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre pour l'interface d'ajout
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une réservation");

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
