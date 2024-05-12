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
import com.mysql.cj.Session;
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

public class Afficher_UserLivraisonController implements Initializable {
    @FXML
    private TableColumn<Livraison, Date> coldate;

    @FXML
    private TableColumn<Livraison, String> colnom;


    @FXML
    private TableColumn<Livraison, String> colsatut;


    @FXML
    private TableView<Livraison> table;
    public static Membre membre=new Membre() ;
    MembreService membreService = new MembreService() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLivraisons();
        membre = membre.convertToMembre(membre.loadJsonFromBinFile());
        membre = membreService.readById(membre.getIdUtilisateur());
    }

    public void showLivraisons() {
        LivraisonService livraisonService = new LivraisonService();
        List<Livraison> livraisons = livraisonService.readAllBySession(membre.getIdUtilisateur());

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


    }


}
