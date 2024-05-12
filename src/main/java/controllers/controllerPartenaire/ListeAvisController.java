package controllers.controllerPartenaire;
import Entity.UserAdmin.Utilisateur;
import Entity.entitiesPartenaire.AvisLivraison;
import Entity.entitiesPartenaire.Livraison;
import Entity.entitiesProduits.commande;
import Services.ServiceProduit.commandeService;
import Services.UserAdmineServices.MembreService;
import Services.servicePartenaire.AvisLivraisonService;
import Services.servicePartenaire.LivraisonService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListeAvisController  {
    @FXML
    private TableColumn<AvisLivraison, String> colcom;

    @FXML
    private TableColumn<AvisLivraison, String> colnom;

    @FXML
    private TableView<AvisLivraison> tableAff;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void init(int id) {
        setId(id);
        AvisLivraisonService avisLivraisonService = new AvisLivraisonService();
        List<AvisLivraison> avisLivraisons = avisLivraisonService.readByIdLivrainson(getId());

        ObservableList<AvisLivraison> list = FXCollections.observableArrayList(avisLivraisons);
        tableAff.setItems(list);

        colnom.setCellValueFactory(cellData -> {
                LivraisonService livraisonService = new LivraisonService();
                Livraison livraison = livraisonService.readByID(getId());

                    Integer idCommande = livraison.getIdCommande();
                    commandeService commandeService = new commandeService();
                    commande commande = commandeService.readByID(idCommande);
                    if (commande != null) {
                        Integer idUser = commande.getIdClient();
                        if (idUser != null) {
                            MembreService membreService = new MembreService();
                            Utilisateur membre = membreService.readById(idUser);
                            return new SimpleStringProperty(membre.getNomUtilisateur());
                        } else {
                            return new SimpleStringProperty("Non affecté");
                        }
                    }

            return new SimpleStringProperty("Non affecté");
        });


        colcom.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
    }

    public void showAvis () {
        init(id);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAvis();
    }
}
