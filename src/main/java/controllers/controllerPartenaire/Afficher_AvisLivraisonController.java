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
import Services.servicePartenaire.AvisLivraisonService;
import Services.servicePartenaire.LivraisonService;
import Services.servicePartenaire.partenaireService;
import javafx.beans.property.SimpleObjectProperty;
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
public class Afficher_AvisLivraisonController implements Initializable{
    @FXML
    private TableColumn<AvisLivraison, Date> colDate;

    @FXML
    private TableColumn<AvisLivraison, String> colcom;

    @FXML
    private TableColumn<AvisLivraison, String> colnom;

    @FXML
    private TableView<AvisLivraison> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAvisLivraison();

    }
    public void showAvisLivraison() {
        AvisLivraisonService avisLivraisonService = new AvisLivraisonService();
        List<AvisLivraison> avisLivraisons = avisLivraisonService.readAll();

        ObservableList<AvisLivraison> list = FXCollections.observableArrayList(avisLivraisons);
        table.setItems(list);

        colnom.setCellValueFactory(cellData -> {
            Integer idLivraison = cellData.getValue().getIdLivraison();
            LivraisonService livraisonService = new LivraisonService();
            Livraison livraison = livraisonService.readByID(idLivraison);
            Integer idCommande = livraison.getIdCommande();
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

        colDate.setCellValueFactory(cellData -> {
            Integer idLivraison = cellData.getValue().getIdLivraison();
            LivraisonService livraisonService = new LivraisonService();
            Livraison livraison = livraisonService.readByID(idLivraison);
            return new SimpleObjectProperty<>(livraison.getDateLivraison());
        });

        colcom.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
    }

}
