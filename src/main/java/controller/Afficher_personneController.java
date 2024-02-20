package controller;

import entities.partenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.partenaireService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Afficher_personneController implements Initializable {

    @FXML
    private TableView<partenaire> table;

    @FXML
    private TableColumn<partenaire, Integer> colid;

    @FXML
    private TableColumn<partenaire, String> colnom;

    @FXML
    private TableColumn<partenaire, String> coltype;

    @FXML
    private TableColumn<partenaire, String> coladresse;

    @FXML
    private TableColumn<partenaire, Integer> coltel;

    @FXML
    private TableColumn<partenaire, String> colemail;
    @FXML
    private TableColumn<partenaire, Void> modifyTC;
    @FXML
    private TableColumn<partenaire, Void> deleteTC;


    @FXML
    private Button ajouter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPartenaires();
        buttonModifier();
        buttonSupprimer();

    }



    @FXML
    void modifier(partenaire event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_personne.fxml"));
                Parent root = loader.load();

                modifier_personneController c = loader.getController();
                c.initData(event.getId());

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modifier Partenaire");
                stage.showAndWait();
                showPartenaires();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }


    @FXML
    void supprimer(partenaire event) {

            partenaireService ps=new partenaireService();
            ps.delete(event.getId());
            showPartenaires();
        }

    @FXML
    void ajouter(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/Ajouter_personne.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            showPartenaires();
            stage.setTitle("Ajouter Partenaire");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }



        public void showPartenaires () {
            partenaireService ps = new partenaireService();
            List<partenaire> p = new ArrayList<>();
            p = ps.readAll();
            ObservableList<partenaire> list = FXCollections.observableArrayList(p);
            table.setItems(list);
            colid.setCellValueFactory(new PropertyValueFactory<partenaire, Integer>("id"));
            colnom.setCellValueFactory(new PropertyValueFactory<partenaire, String>("nom"));
            coltel.setCellValueFactory(new PropertyValueFactory<partenaire, Integer>("tel"));
            coladresse.setCellValueFactory(new PropertyValueFactory<partenaire,String>("adresse"));
            coltype.setCellValueFactory(new PropertyValueFactory<partenaire, String>("type"));
            colemail.setCellValueFactory(new PropertyValueFactory<partenaire, String>("email"));
        }
    private void buttonModifier() {

        modifyTC.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        partenaire pm = getTableView().getItems().get(getIndex());
                        modifier(pm);
                    });
                }
            }
        });
    }
    private void buttonSupprimer() {
        // Set up the delete button column
        deleteTC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        partenaire pm = getTableView().getItems().get(getIndex());
                        supprimer(pm);
                    });
                }
            }
        });
    }



}
