package controllers.controllerGestionEnchere;


import Entity.entitiesEncheres.Enchere;
import Services.EnchereService.EnchereService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;

public class ListeEnchereController {

    @FXML
    private TableView<Enchere> auctionsTableView;

    @FXML
    private TableColumn<Enchere, Integer> idColumn;

    @FXML
    private TableColumn<Enchere, LocalDate> startDateColumn;

    @FXML
    private TableColumn<Enchere, LocalDate> endDateColumn;

    @FXML
    private TableColumn<Enchere, Boolean> statusColumn;

    @FXML
    private TableColumn<Enchere, Float> minPriceColumn;

    @FXML
    private TableColumn<Enchere, Float> maxPriceColumn;

    @FXML
    private TableColumn<Enchere, Float> currentPriceColumn;

    @FXML
    private TableColumn<Enchere, Integer> nbrParticipantsColumn;

    @FXML
    private TableColumn<Enchere, Integer> idProduitColumn;

    @FXML
    private TableColumn<Enchere, Void> modifyColumn;

    @FXML
    private TableColumn<Enchere, Void> deleteColumn;

    private final EnchereService enchereService = new EnchereService();

    public void initialize() {
        // Initialize TableColumn cell values
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdEnchere()).asObject());
        startDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateDebut()));
        endDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateFin()));
        statusColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isStatus()));
        minPriceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrixMin()).asObject());
        maxPriceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrixMax()).asObject());
        nbrParticipantsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNbrParticipants()).asObject());
        currentPriceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrixActuelle()).asObject());
        idProduitColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdProduit().getIdProduit()).asObject());

        // Set up cell value factories for additional columns
        setUpModifyColumn();
        setUpDeleteColumn();

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        // Fetch all Enchere objects from the database
        ObservableList<Enchere> enchereList = FXCollections.observableArrayList(enchereService.readAll());

        // Populate the TableView with the fetched data
        auctionsTableView.setItems(enchereList);
    }

    private void setUpModifyColumn() {
        modifyColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");

            {
                modifyButton.setOnAction(event -> {
                    Enchere enchere = getTableView().getItems().get(getIndex());
                    openModifyEnchereInterface(enchere);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                }
            }
        });
    }

    private void setUpDeleteColumn() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Enchere enchere = getTableView().getItems().get(getIndex());
                    deleteEnchere(enchere);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void deleteEnchere(Enchere enchere) {
        enchereService.delete(enchere);
        auctionsTableView.getItems().remove(enchere);
    }

    private void openModifyEnchereInterface(Enchere enchere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceEnchere/ModifyEnchere.fxml"));
            Parent root = loader.load();

            ModifyEnchereController modifyController = loader.getController();
            modifyController.setEnchere(enchere);

            // Create a new stage for the dialog
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Set the dialog stage in the controller
            modifyController.setDialogStage(stage);

            // Show the dialog
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
