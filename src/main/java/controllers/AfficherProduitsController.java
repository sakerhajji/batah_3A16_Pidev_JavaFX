package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Produits;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.ProduitsService;
import utils.DataSource;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

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
    private TableColumn<Produits, Integer> colstatus;

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
    private TextField txtstatus;

    @FXML
    private ChoiceBox<String> txttype;
    @FXML
    private TableColumn<Produits, Void> deleteTC;

    @FXML
    private TableColumn<Produits, Void> modifyTC;


    private ProduitsService produitsService = new ProduitsService();
    private final ObservableList<Produits> produits = FXCollections.observableArrayList();

   /* @FXML
    void initialize() {
        try {
            ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsService.readAll());
            table.setItems(observableList);
            colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
            coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
            coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
            colprix.setCellValueFactory(new PropertyValueFactory<Produits, Float>("prix"));
            collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
            colstatus.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("status"));
            colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarantie"));
            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getId();
                String userName = produitsService.getUserById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProduits();
        buttonModifier();
        buttonSupprimer();
       // refreshTable();
    }

    public void refreshTableView() {
        // Mettez à jour la table avec les nouvelles données
        try {
            List<Produits> prodList = produitsService.readAll();
            ObservableList<Produits> prodObservableList = FXCollections.observableArrayList(prodList);
            table.setItems(prodObservableList);

            // Lier les colonnes à la classe Produits (ajustez les noms des méthodes selon votre modèle)
            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getId();
                String userName = produitsService.getUserById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });
            // ... (liens des autres colonnes)
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
   /* public void refreshTable() {
        produits.clear();
        List<Produits> produitsList = produitsService.readAll();
        produits.addAll(produitsList);
        table.setItems(produits);
    }*/

    @FXML
    void rechercher(ActionEvent event) {
        String nom = txtrecherche.getText().trim();
        if (!nom.isEmpty()) {
            // Appeler la méthode de recherche par nom
            rechercherPartenaireParNom(nom);
        } else {
            // Si le champ de recherche est vide, afficher tous les partenaires
            showProduits();
        }
    }
    private void rechercherPartenaireParNom(String nom) {
        ProduitsService ps = new ProduitsService();
        List<Produits> resultatRecherche = ps.rechercheParNom(nom);
        ObservableList<Produits> listeResultat = FXCollections.observableArrayList(resultatRecherche);
        table.setItems(listeResultat);
    }



    @FXML
    void addProduits(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AjouterProduits.fxml"));
        try {
            Parent root = loader.load();
//je peut recupere la scene actuelle a traveres tous les composant graphiques
            table.getScene().setRoot(root);
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
    void deletebtn(ActionEvent event) {
// Assuming you have a selected product in the table
        Produits selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Confirm the deletion with a dialog or alert
            boolean confirmed = showConfirmationDialog("Confirmation", "Are you sure you want to delete this product?");
            if (confirmed) {
                ProduitsService ps = new ProduitsService();
                // Assuming you have a method like deletePst in ProduitsService
                ps.delete(selectedProduct.getIdProduit());

                // Refresh the table data
                showProduits();
            }
        } else {
            // Show an alert or message indicating that no product is selected
        }
    }

    @FXML
    void updatebtn(ActionEvent event) {

           /* Produits selectedProduct = table.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                // Show a dialog or another form to edit the selected product details
                // After editing, update the product in the database
                ProduitsService ps = new ProduitsService();

                // Update the selected product with the new details
                selectedProduct.setType(txttype.getItems().toString());
                selectedProduct.setDescription(txtdescription.getText());
                selectedProduct.setPrix(Float.parseFloat(txtprix.getText()));
                selectedProduct.setLabelle(txtlabelle.getText());
                //selectedProduct.setPhoto(txtphoto.getText());
                selectedProduct.setStatus(Integer.parseInt(txtstatus.getText()));
                selectedProduct.setPeriodeGarentie(Integer.parseInt(txtperiodeGarentie.getText()));

                // Call the update method
                ps.update(selectedProduct);

                // Refresh the table data
                showProduits();
            } else {
                // Show an alert or message indicating that no product is selected
            }*/


    }
    @FXML
    void getData(MouseEvent event) {
        Produits produits = table.getSelectionModel().getSelectedItem();
        if (produits != null) {
            List<String> typesPossibles = Arrays.asList("voiture", "maison"); // ou obtenez la liste d'une autre manière
            txttype.setItems(FXCollections.observableArrayList(typesPossibles));

// Ensuite, définissez la valeur par défaut
            txttype.setValue(produits.getType());
            txtdescription.setText(produits.getDescription());
            txtlabelle.setText(produits.getLabelle());
            txtstatus.setText(String.valueOf(produits.getStatus()));
            txtprix.setText(String.valueOf(produits.getPrix()));
            txtperiodeGarentie.setText(String.valueOf(produits.getPeriodeGarentie()));
        }
    }
    @FXML
    void ExitToMenu(ActionEvent event) {

    }

    @FXML
    void PrintPDF(ActionEvent event) {

    }

    @FXML
    void modifier(Produits event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProduits.fxml"));
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
                    .getResource("/AjouterProduits.fxml"));
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
        colstatus.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("status"));
        colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarentie"));
        colUser.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getId().getId();
            String userName = produitsService.getUserById(userId).getNomUtilisateur();
            return new SimpleStringProperty(userName);
        });
    }
    private void buttonModifier() {
        modifyTC.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button();
            private final javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône


            {
                // Initialiser le bouton et l'image une seule fois
                javafx.scene.image.Image image = new javafx.scene.image.Image("cssPartenaire/modifier.png", iconWidth, iconHeight, true, true);
                imageView.setImage(image);
                modifyButton.setGraphic(imageView);

                // Attacher l'événement uniquement lorsque le bouton est cliquable
                modifyButton.setOnAction(event -> {
                    Produits pm = getTableView().getItems().get(getIndex());
                    modifier(pm);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Mettre à jour le graphique chaque fois que la ligne n'est pas vide
                    setGraphic(modifyButton);
                }
            }
        });
    }
    private void buttonSupprimer() {
        deleteTC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final javafx.scene.image.ImageView imageView = new ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Charger l'image avec les dimensions spécifiées
                    javafx.scene.image.Image image = new Image("cssPartenaire/supprimer.png", iconWidth, iconHeight, true, true);
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



}
