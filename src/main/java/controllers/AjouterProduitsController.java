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
import javafx.scene.control.*;
import entities.Produits;
import entities.Utilisateur;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ProduitsService;
import service.utilisateurService;

import java.io.IOException;
import java.sql.SQLException;
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
    private ChoiceBox<Integer> idUser;


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
        utilisateurService userService = new utilisateurService(); // Initialize the userService field
        populateUserComboBox(); // Pass the service to the method

        /*
            // Convertir les IDs en objets Utilisateur (vous devrez peut-être adapter cela selon votre structure)
            List<Utilisateur> users = userIds.stream()
                    .map(id -> produitsService.getUserById(id))
                    .collect(Collectors.toList());
*/

    }
                public void populateUserComboBox() {

                    utilisateurService u = new utilisateurService();
                    List<Utilisateur> users = null;

                    try {
                        users = u.readAll();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    ObservableList<Integer> userIds = FXCollections.observableArrayList();

                    for (Utilisateur user : users) {
                        userIds.add(user.getId());
                    }

                    idUser.setItems(userIds);
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
        //Utilisateur utilisateur = idUser.getValue();
        // Check if selectedUserid is not null before using it
        Integer selectedUserid = idUser.getValue();
        if (selectedUserid == null) {
            // Handle the case where no user is selected (e.g., show an error message)
            System.out.println("Error: No user selected");
            return;
        }

        Utilisateur selectedUser = produitsService.getUserById(selectedUserid);

        // Create a new Produits object
        Produits newProduit = new Produits( type,description,  prix,labelle, photo, status,periodeGarantie, selectedUser);

        // Add the product to the database
        produitsService.add(newProduit);

    }


    @FXML
    void ViewAllProduct(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherProduits.fxml"));
        try {
            Parent root = loader.load();
//je peut recupere la scene actuelle a traveres tous les composant graphiques
            txttype.getScene().setRoot(root);
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }

}
