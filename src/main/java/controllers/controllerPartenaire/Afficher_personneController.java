package controllers.controllerPartenaire;


import Entity.entitiesPartenaire.partenaire;
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

    }
    @FXML
    void rechercher(ActionEvent event) {
        String nom = searchField.getText().trim();
        if (!nom.isEmpty()) {
            // Appeler la méthode de recherche par nom
            rechercherPartenaireParNom(nom);
        } else {
            // Si le champ de recherche est vide, afficher tous les partenaires
            showPartenaires();
        }
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
