package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entities.Produits;
import entities.Utilisateur;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ProduitsService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AjouterProduitsController {

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
    private ChoiceBox<Utilisateur> idUser;

    @FXML
    private TableView<Produits> table;

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
    private TextField txttype;

    private ProduitsService produitsService = new ProduitsService();

    @FXML
    void initialize() {
        try {
            // Initialize the ChoiceBox with user IDs
            List<Integer> userIds = produitsService.getAllUsers();

            // Convertir les IDs en objets Utilisateur (vous devrez peut-être adapter cela selon votre structure)
            List<Utilisateur> users = userIds.stream()
                    .map(id -> produitsService.getUserById(id))
                    .collect(Collectors.toList());

            ObservableList<Utilisateur> userObservableList = FXCollections.observableArrayList(users);
            idUser.setItems(userObservableList);

            // Lier les colonnes à la classe Produits
          /*  colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
            coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
            coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
            colprix.setCellValueFactory(new PropertyValueFactory<Produits,Float>("prix"));
            collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
            colphoto.setCellValueFactory(new PropertyValueFactory<Produits, String>("photo"));
            colstatus.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("status"));
            // colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarantie"));
            colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarantie"));
            colUser.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idUser"));
            //colUser.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
*/
            //colUser.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
            colUser.setCellValueFactory(cellData -> {
                /*int userId = cellData.getValue().getId().getId();
                return Bindings.createObjectBinding(() -> userId);
*/
                int userId = cellData.getValue().getId().getId();
                String userName = produitsService.getUserById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });
            coldescription.setCellValueFactory(cellData -> {
                String description = cellData.getValue().getDescription();
                return Bindings.createStringBinding(() -> description);
            });
            colidProduit.setCellValueFactory(cellData ->
                    Bindings.createObjectBinding(() -> cellData.getValue().getIdProduit()));

            collabelle.setCellValueFactory(cellData -> {
                String labelle = cellData.getValue().getLabelle();
                return Bindings.createStringBinding(() -> labelle);
            });

            colperiodeGarentie.setCellValueFactory(cellData ->
                    Bindings.createObjectBinding(() -> cellData.getValue().getPeriodeGarentie()));

            colphoto.setCellValueFactory(cellData -> {
                String photo = cellData.getValue().getPhoto();
                return Bindings.createStringBinding(() -> photo);
            });

            colprix.setCellValueFactory(cellData ->
                    Bindings.createObjectBinding(() -> cellData.getValue().getPrix()));

            colstatus.setCellValueFactory(cellData ->
                    Bindings.createObjectBinding(() -> cellData.getValue().getStatus()));


            coltype.setCellValueFactory(cellData -> {
                String type = cellData.getValue().getType();
                return new SimpleStringProperty(type);
            });


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void addProduits(ActionEvent event) {
        String description = txtdescription.getText();
        String labelle = txtlabelle.getText();
        String photo = txtphoto.getText();
        int status = Integer.parseInt(txtstatus.getText());
        String type = txttype.getText();
        float prix = Float.parseFloat(txtprix.getText());
        int periodeGarantie = Integer.parseInt(txtperiodeGarentie.getText());
        Utilisateur utilisateur = idUser.getValue();

        // Create a new Produits object
        Produits newProduit = new Produits( type,description,  prix,labelle, photo, status,periodeGarantie, utilisateur);

        // Add the product to the database
        produitsService.add(newProduit);

    }

    private void refreshTableView() {
        // Update the table view with the new data
        List<Produits> prodList = produitsService.readAll();
        ObservableList<Produits> prodObservableList = FXCollections.observableArrayList(prodList);
        table.setItems(prodObservableList);
    }

    @FXML
    void ViewAllProduct(ActionEvent event) {
      /*  FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherProduits.fxml"));
        try {
            Parent root = loader.load();
//je peut recupere la scene actuelle a traveres tous les composant graphiques
            txttype.getScene().setRoot(root);
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }*/
        try {
            // Charger le fichier FXML de l'interface AfficherProduitsController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
            Parent root = loader.load();

            // Obtenir la scène à partir de n'importe quel élément graphique dans votre scène actuelle
            Scene scene = table.getScene();

            // Changer la racine de la scène pour afficher la nouvelle interface
            scene.setRoot(root);

            // Si le contrôleur AfficherProduitsController a besoin d'accéder au contrôleur AjouterProduitsController,
            // vous pouvez l'obtenir à partir du loader
            AfficherProduitsController afficherProduitsController = loader.getController();
            // Ensuite, vous pouvez appeler des méthodes ou accéder à des membres du contrôleur AfficherProduitsController
            // si nécessaire

            // Rafraîchir la table dans le contrôleur AfficherProduitsController
            afficherProduitsController.refreshTableView();

        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les exceptions en conséquence
        }
    }
}
