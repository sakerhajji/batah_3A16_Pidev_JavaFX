package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import entities.Enchere;
import entities.Produits;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import service.EnchereService;
import service.ProduitsService;

public class afficherencheres {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField prixActuelField;

    @FXML
    private TextField prixMaxField;

    @FXML
    private TextField prixMinField;

    @FXML
    private ComboBox<Integer> productComboBox; // Assuming the descriptions are strings

    @FXML
    private CheckBox statusCheckbox;

    private EnchereService enchereService;

    @FXML
    void initialize() {
        enchereService = new EnchereService();
        populateProductComboBox();
    }

    private void populateProductComboBox() {
        List<Enchere> enchereList = enchereService.readAll();
        ObservableList<Integer> productDescriptions = FXCollections.observableArrayList();
        for (Enchere enchere : enchereList) {
            productDescriptions.add(enchere.getIdProduit().getIdProduit());
        }
        productComboBox.setItems(productDescriptions);
    }

    @FXML
    void ajouterAction(ActionEvent event) {


        // Retrieve data from the input fields
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        float prixActuel = Float.parseFloat(prixActuelField.getText());
        float prixMax = Float.parseFloat(prixMaxField.getText());
        float prixMin = Float.parseFloat(prixMinField.getText());
        Integer produit = productComboBox.getValue();
        boolean status = statusCheckbox.isSelected();
        ProduitsService ps=new ProduitsService();
        Produits p=new Produits();
        try {
            p=ps.getProduitById(produit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Enchere e=new Enchere(dateDebut,dateFin,status,prixMin,prixMax,prixActuel,p);
        EnchereService es=new EnchereService();
        es.add(e);



        // You can then use this data to create a new Enchere object and add it to your database
        // Example:
        // Enchere newEnchere = new Enchere(dateDebut, dateFin, status, prixMin, prixMax, prixActuel, getProductByDescription(productDescription));
        // enchereService.addEnchere(newEnchere);
    }

    // Method to find product based on description
    private Produits getProductByDescription(String description) {
        // Implement this method to retrieve the product from your database based on its description
        // Example:
        // return productService.getProductByDescription(description);
        return null; // Placeholder, replace with actual implementation
    }
}
