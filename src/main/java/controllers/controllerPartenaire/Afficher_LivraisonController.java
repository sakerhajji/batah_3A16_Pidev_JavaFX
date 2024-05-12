package controllers.controllerPartenaire;

import Entity.UserAdmin.Membre;
import Entity.UserAdmin.Utilisateur;
import Entity.entitiesPartenaire.AvisLivraison;
import Entity.entitiesPartenaire.Livraison;
import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesProduits.commande;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.ServiceProduit.commandeService;
import Services.UserAdmineServices.MembreService;
import Services.servicePartenaire.LivraisonService;
import Services.servicePartenaire.partenaireService;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Afficher_LivraisonController implements Initializable{
    @FXML
    private TableColumn<Livraison, Date> coldate;

    @FXML
    private TableColumn<Livraison, String> colnom;

    @FXML
    private TableColumn<Livraison, String> colpartner;

    @FXML
    private TableColumn<Livraison, String> colsatut;
    @FXML
    private TableColumn<Livraison, Void> colAvis;

    @FXML
    private TableView<Livraison> table;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLivraisons();
        buttonShowAff();
    }
    public void showLivraisons() {
        LivraisonService livraisonService = new LivraisonService();
        List<Livraison> livraisons = livraisonService.readAll();

        ObservableList<Livraison> list = FXCollections.observableArrayList(livraisons);
        table.setItems(list);

        colnom.setCellValueFactory(cellData -> {
            Integer idCommande = cellData.getValue().getIdCommande();
            commandeService commandeService = new commandeService();
            commande commande = commandeService.readByID(idCommande);
            Integer idUser = commande.getIdClient();
            if (idUser != null) {
                MembreService membreService = new MembreService();
                Utilisateur membre = membreService.readById(idUser);
                return new SimpleStringProperty(membre.getNomUtilisateur());
            } else {
                return new SimpleStringProperty("Non affecté");
            }
        });

        coldate.setCellValueFactory(new PropertyValueFactory<Livraison, Date>("dateLivraison"));
        colsatut.setCellValueFactory(new PropertyValueFactory<Livraison, String>("statut"));

        colpartner.setCellValueFactory(cellData -> {
            Integer idPartenaire = cellData.getValue().getIdPartenaire();
            if (idPartenaire != null) {
                partenaireService partenaireService = new partenaireService();
                Partenaire partenaire = partenaireService.readById(idPartenaire);
                if (partenaire != null) {
                    return new SimpleStringProperty(partenaire.getNom());
                } else {
                    return new SimpleStringProperty("Non affecté");
                }
            } else {
                return new SimpleStringProperty("Non affecté");
            }
        });
    }
    void showAvis(Livraison event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/ListeAvis.fxml"));
            Parent root = loader.load();

            ListeAvisController c = loader.getController();
            c.init(event.getIdLivraison());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Avis");
            stage.showAndWait();
            showLivraisons();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void buttonShowAff() {
        colAvis.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24;
            private final double iconHeight = 24;


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
                        Livraison pm = getTableView().getItems().get(getIndex());
                        showAvis(pm);
                    });
                }
            }
        });
    }




}
