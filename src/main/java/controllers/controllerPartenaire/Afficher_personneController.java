package controllers.controllerPartenaire;


import Entity.entitiesPartenaire.partenaire;
import Services.servicePartenaire.partenaireService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private TableColumn<partenaire, String> logo;
    @FXML
    private TableColumn<partenaire, Void> modifyTC;
    @FXML
    private TableColumn<partenaire, Void> deleteTC;
    @FXML
    private TextField searchField;


    @FXML
    private Button ajouter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPartenaires();
        buttonModifier();
        buttonSupprimer();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                rechercherPartenaireParNom(newValue.trim());
            }else { showPartenaires();}
        });
    }


    private void rechercherPartenaireParNom(String nom) {
        partenaireService ps = new partenaireService();
        List<partenaire> resultatRecherche = ps.rechercheParNom(nom);
        ObservableList<partenaire> listeResultat = FXCollections.observableArrayList(resultatRecherche);
        table.setItems(listeResultat);
    }


    @FXML
    void modifier(partenaire event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/modifier_personne.fxml"));
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
                    .getResource("/interfacePartenaire/Ajouter_personne.fxml"));
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
        List<partenaire> p = ps.readAll();
        ObservableList<partenaire> list = FXCollections.observableArrayList(p);
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<partenaire, Integer>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<partenaire, String>("nom"));
        coltel.setCellValueFactory(new PropertyValueFactory<partenaire, Integer>("tel"));
        coladresse.setCellValueFactory(new PropertyValueFactory<partenaire,String>("adresse"));
        coltype.setCellValueFactory(new PropertyValueFactory<partenaire, String>("type"));
        colemail.setCellValueFactory(new PropertyValueFactory<partenaire, String>("email"));
        logo.setCellFactory(column -> new TableCell<partenaire, String>() {
            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);
                if (empty || imageName == null) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView();
                    String imagePath = "E:/fac/3eme/java/batah_3A16_Pidev_JavaFX/src/main/resources/images/imagesPartenaire/" + imageName;
                    Image image = new Image(new File(imagePath).toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);




                }
            }
        });


    }
    private void buttonModifier() {
        modifyTC.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Charger l'image avec les dimensions spécifiées
                    Image image = new Image("cssPartenaire/modifier.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    modifyButton.setGraphic(imageView);
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
        deleteTC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Charger l'image avec les dimensions spécifiées
                    Image image = new Image("cssPartenaire/supprimer.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    deleteButton.setGraphic(imageView);
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
