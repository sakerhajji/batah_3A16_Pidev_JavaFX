package controllers;

import entities.Produits;
import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ProduitsService;
import service.utilisateurService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ModifierProduitsController  {
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


///////////////

    private int id;
    public ModifierProduitsController() {
        // Initialiser idUser ici si nécessaire
        idUser = new ChoiceBox<>();
    }

    public void initData(int id) {
        this.id=id;

            ProduitsService ps = new ProduitsService();
            Produits produits = ps.fetchProduitById(id);

            txttype.setText(produits.getType());
            txtdescription.setText(produits.getDescription());
            txtprix.setText(String.valueOf(produits.getPrix()));
            idUser.setValue(produits.getId().getId());
            txtlabelle.setText(produits.getLabelle());
            txtstatus.setText(String.valueOf(produits.getStatus()));
            txtperiodeGarentie.setText(String.valueOf(produits.getPeriodeGarentie()));
            populateUserComboBox();


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
 void updateProduits(ActionEvent event) {


     String description = txtdescription.getText();
     String labelle = txtlabelle.getText();

     String type = txttype.getText();
     int status = Integer.parseInt(txtstatus.getText());
     float prix = Float.parseFloat(txtprix.getText());
     int periodeGarantie = Integer.parseInt(txtperiodeGarentie.getText());
     Integer selectedUserid = idUser.getValue();
      ProduitsService produitsService = new ProduitsService();
     Utilisateur iduser = produitsService.getUserById(selectedUserid);
     Produits pr = new Produits(id,type, description, prix, labelle, status, periodeGarantie, iduser);
     produitsService.update(pr);

     try {
         // Fermez la fenêtre
         Stage stage = (Stage) txttype.getScene().getWindow();
         stage.close();

         // Actualisez la TableView dans le contrôleur principal
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduits.fxml"));
         Parent root = loader.load();
         AfficherProduitsController afficherProduitsController = loader.getController();/* obtenir une référence à votre contrôleur principal */;
         afficherProduitsController.refreshTableView();
     } catch (Exception e) {
         System.out.println(e.getMessage());
     }
 }


    private void setValid(TextField field) {
        field.setStyle("-fx-border-color: green;");
    }


    private void setInvalid(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }


    private void setValid(ChoiceBox<?>  choiceBox) {
        choiceBox.setStyle("-fx-border-color: green;");
    }


    private void setInvalid(ChoiceBox<?> choiceBox) {
        choiceBox.setStyle("-fx-border-color: red;");
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
