package controllers.controllerPartenaire;


import Entity.entitiesPartenaire.Partenaire;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.servicePartenaire.partenaireService;
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
import java.util.List;
import java.util.ResourceBundle;

public class Afficher_PartenaireController implements Initializable {

    @FXML
    private TableView<Partenaire> table;

    @FXML
    private TableColumn<Partenaire, Integer> colid;

    @FXML
    private TableColumn<Partenaire, String> colnom;

    @FXML
    private TableColumn<Partenaire, String> coltype;

    @FXML
    private TableColumn<Partenaire, String> coladresse;

    @FXML
    private TableColumn<Partenaire, Integer> coltel;

    @FXML
    private TableColumn<Partenaire, String> colemail;
    @FXML
    private TableColumn<Partenaire, String> logo;
    @FXML
    private TableColumn<Partenaire, Void> modifyTC;
    @FXML
    private TableColumn<Partenaire, Void> deleteTC;
    @FXML
    private TableColumn<Partenaire, Void> Affectation;
    @FXML
    private TextField searchField;


    @FXML
    private Button ajouter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPartenaires();
        buttonModifier();
        buttonSupprimer();
        buttonShowAff();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                rechercherPartenaireParNom(newValue.trim());
            }else { showPartenaires();}
        });
    }


    private void rechercherPartenaireParNom(String nom) {
        partenaireService ps = new partenaireService();
        List<Partenaire> resultatRecherche = ps.rechercheParNom(nom);
        ObservableList<Partenaire> listeResultat = FXCollections.observableArrayList(resultatRecherche);
        table.setItems(listeResultat);
    }


    @FXML
    void modifier(Partenaire event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/modifier_Partenaire.fxml"));
                Parent root = loader.load();

                modifier_PartenaireController c = loader.getController();
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
    void supprimer(Partenaire event) {

            partenaireService ps=new partenaireService();
            ps.delete(event.getId());
            showPartenaires();
        }

    @FXML
    void ajouter(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfacePartenaire/Ajouter_Partenaire.fxml"));
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
        List<Partenaire> p = ps.readAll();
        ObservableList<Partenaire> list = FXCollections.observableArrayList(p);
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Partenaire, Integer>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("nom"));
        coltel.setCellValueFactory(new PropertyValueFactory<Partenaire, Integer>("tel"));
        coladresse.setCellValueFactory(new PropertyValueFactory<Partenaire,String>("adresse"));
        coltype.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("type"));
        colemail.setCellValueFactory(new PropertyValueFactory<Partenaire, String>("email"));
        logo.setCellFactory(column -> new TableCell<Partenaire, String>() {
            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);
                if (empty || imageName == null) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView();
                    String imagePath = "src/main/resources/images/imagesPartenaire/" + imageName;
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
                        Partenaire pm = getTableView().getItems().get(getIndex());
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
                        Partenaire pm = getTableView().getItems().get(getIndex());
                        supprimer(pm);
                    });
                }
            }
        });
    }
    void showAffectation(Partenaire event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/AffectationPartenaire.fxml"));
            Parent root = loader.load();

            AffectationPartenaireController c = loader.getController();
            c.init(event.getId());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Affectation");
            stage.showAndWait();
            showPartenaires();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void buttonShowAff() {
        Affectation.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24;
            private final double iconHeight = 24;

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Image image = new Image("images/affectation.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    modifyButton.setGraphic(imageView);
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        Partenaire pm = getTableView().getItems().get(getIndex());
                        showAffectation(pm);
                    });
                }
            }
        });
    }
    @FXML
    void showStatistique(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfacePartenaire/statistiquePartenaires.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            showPartenaires();
            stage.setTitle("Actives Partenaires");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }



}
