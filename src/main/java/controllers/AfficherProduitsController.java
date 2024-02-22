package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entities.Produits;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ProduitsService;

import java.sql.SQLException;
import java.util.List;

public class AfficherProduitsController {

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

    private ProduitsService produitsService = new ProduitsService();

    @FXML
    void initialize() {
        try {
            ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsService.readAll());
            table.setItems(observableList);
            colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
            coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
            coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
            colprix.setCellValueFactory(new PropertyValueFactory<Produits,Float>("prix"));
            collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
            colstatus.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("status"));
            colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarantie"));
            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getId();
                String userName = produitsService.getUserById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });



            }catch (Exception e){
            System.out.println(e.getMessage());
        }

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
    @FXML
    void DeleteProduct(ActionEvent event) {
        Produits selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            produitsService.delete(selectedProduct.getIdProduit());
            refreshTableView();
        }
    }

    @FXML
    void UpdateProduct(ActionEvent event) {
        Produits selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Ouvrez une fenêtre de mise à jour ou utilisez des champs de texte dans l'interface
            // pour permettre à l'utilisateur de modifier les détails du produit
            // Ensuite, appelez la méthode update du service
            // produitsService.update(selectedProduct);
            // N'oubliez pas de rafraîchir la table après la mise à jour
            refreshTableView();
        }
    }

    @FXML
    void addProduits(ActionEvent event) {
        // Ouvrez une fenêtre d'ajout ou utilisez des champs de texte dans l'interface
        // pour permettre à l'utilisateur de saisir les détails du nouveau produit
        // Ensuite, appelez la méthode add du service
        // produitsService.add(newProduit);
        // N'oubliez pas de rafraîchir la table après l'ajout
        refreshTableView();
    }
    @FXML
    void ExitToMenu(ActionEvent event) {

    }

    @FXML
    void PrintPDF(ActionEvent event) {

    }
}
