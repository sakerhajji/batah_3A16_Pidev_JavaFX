
package controllers.ControllerProduits;

import Entity.entitiesProduits.Produits;
import Services.ServiceProduit.ProduitsService;
import Services.UserAdmineServices.MembreService;
import javafx.beans.property.SimpleStringProperty;
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

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class AfficherProduitsController  implements Initializable{

    @FXML
    private TableColumn<Produits, String> colUser;

    @FXML
    private TableColumn<Produits, String> coldescription;

    @FXML
    private TableColumn<Produits, Integer> colidProduit;

    @FXML
    private TableColumn<Produits, String> collabelle;

    @FXML
    private TableColumn<Produits, Integer> colperiodeGarentie;

    @FXML
    private TableColumn<Produits, String> colphoto;

    @FXML
    private TableColumn<Produits, Float> colprix;

    @FXML
    private TableColumn<Produits, String> colstatus;

    @FXML
    private TableColumn<Produits, String> coltype;

    @FXML
    private TableView<Produits> table;

    @FXML
    private TextField txtrecherche;
    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtlabelle;

    @FXML
    private TextField txtperiodeGarentie;

    @FXML
    private TextField txtphoto;

    @FXML
    private TextField txtprix;


    @FXML
    private ChoiceBox<String> txtstatus;

    @FXML
    private ChoiceBox<String> txttype;
    @FXML
    private TableColumn<Produits, Void> deleteTC;

    @FXML
    private TableColumn<Produits, Void> modifyTC;

    @FXML
    private ImageView placeholderImageView;


    private ProduitsService produitsService = new ProduitsService();
    MembreService ms=new MembreService();
    private final ObservableList<Produits> produits = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProduits();
        buttonModifier();
        buttonSupprimer();
        txtrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                rechercherProduitParNom(newValue.trim());
            }else { showProduits();}
        });
    }


    private void rechercherProduitParNom(String nom) {
        List<Produits> resultatRecherche = produitsService.rechercheParNom(nom);
        ObservableList<Produits> listeResultat = FXCollections.observableArrayList(resultatRecherche);
        table.setItems(listeResultat);
    }
    public void refreshTableView() {
        // Mettez à jour la table avec les nouvelles données
        try {
            List<Produits> prodList = produitsService.readAll();
            ObservableList<Produits> prodObservableList = FXCollections.observableArrayList(prodList);
            table.setItems(prodObservableList);

            // Lier les colonnes à la classe Produits (ajustez les noms des méthodes selon votre modèle)
            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getIdUtilisateur();
                String userName = ms.readById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });
            // ... (liens des autres colonnes)
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    void addProduits(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceProduit/AjouterProduits.fxml"));
            Parent root = loader.load();

            AjouterProduitsController c = loader.getController();
            // Assurez-vous d'appeler populateUserComboBox() avant initData
            // c.initData(event.getIdProduit());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter Produit");
            stage.showAndWait();
            showProduits();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }



    @FXML
    void getData(MouseEvent event) {
        Produits produits = table.getSelectionModel().getSelectedItem();
        if (produits != null) {
            List<String> typesPossibles = Arrays.asList("voiture", "maison");
            txttype.setItems(FXCollections.observableArrayList(typesPossibles));
            txttype.setValue(produits.getType());

            List<String> status = Arrays.asList("Available", "Not Available");
            txtstatus.setItems(FXCollections.observableArrayList(status));
            txtstatus.setValue(produits.getStatus());

            txtdescription.setText(produits.getDescription());
            txtlabelle.setText(produits.getLabelle());
            txtprix.setText(String.valueOf(produits.getPrix()));
            txtperiodeGarentie.setText(String.valueOf(produits.getPeriodeGarentie()));
            // Load and display the image
            String imagePath = "src/main/resources/images/imagesPartenaire/" + produits.getPhoto();
            Image image = new Image(new File(imagePath).toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(50);
            // Assuming you have a placeholder in your UI to display the image, replace 'placeholderImageView' with the actual ID of the ImageView in your FXML.
            placeholderImageView.setImage(imageView.getImage());
        }
        }

    @FXML
    void ExitToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceUserAdmin/AccueilAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void PrintPDF(ActionEvent event) {

    }



    @FXML
    void modifier(Produits event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceProduit/ModifierProduits.fxml"));
            Parent root = loader.load();

            ModifierProduitsController c = loader.getController();

            // Assurez-vous d'appeler populateUserComboBox() avant initData

            c.initData(event.getIdProduit());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Produit");
            stage.showAndWait();
            showProduits();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }




    @FXML
    void supprimer(Produits event) {

        ProduitsService ps=new ProduitsService();
        ps.delete(event.getIdProduit());
        showProduits();
    }

    @FXML
    void ajouter(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/AjouterProduits.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            showProduits();
            stage.setTitle("Ajouter Produits");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }



    public void showProduits () {
        ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsService.readAll());
        table.setItems(observableList);
        colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
        coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
        colprix.setCellValueFactory(new PropertyValueFactory<Produits, Float>("prix"));
        collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
        colstatus.setCellValueFactory(new PropertyValueFactory<Produits, String>("status"));
        colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarentie"));
        colphoto.setCellFactory(column -> new TableCell<Produits, String>() {
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


        //colphoto.setCellValueFactory(new PropertyValueFactory<Produits, String>("photoFileName"));


        colUser.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getId().getIdUtilisateur();
            String userName = ms.readById(userId).getNomUtilisateur();
            return new SimpleStringProperty(userName);
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
                        Produits pm = getTableView().getItems().get(getIndex());
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
                        Produits pm = getTableView().getItems().get(getIndex());
                        supprimer(pm);
                    });
                }
            }
        });
    }

    @FXML
    void Clientbtn(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/Client.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
          //  showPartenaires();
            stage.setTitle("Ajouter Partenaire");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }



}
