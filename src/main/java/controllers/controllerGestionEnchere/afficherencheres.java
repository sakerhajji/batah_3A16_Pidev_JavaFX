package controllers.controllerGestionEnchere;

import Entity.entitiesEncheres.Enchere;
import Entity.entitiesProduits.Produits;
import Services.EnchereService.EnchereService;
import Services.ServiceProduit.ProduitsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class afficherencheres {

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
    private TextField nbrParticipantsField;

    @FXML
    private ComboBox<String> productComboBox; // Modifier le type du ComboBox

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
        ObservableList<String> productItems = FXCollections.observableArrayList();
        for (Enchere enchere : enchereList) {
            int productId = enchere.getIdProduit().getIdProduit();
            String productType = enchere.getIdProduit().getType(); // Supposons que le type soit stocké dans une propriété "type"
            productItems.add(productId + ":" + productType);
        }
        productComboBox.setItems(productItems);
    }

    @FXML
    void ajouterAction(ActionEvent event) {
        if (!validateInput()) {
            return;
        }

        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        float prixActuel = Float.parseFloat(prixActuelField.getText());
        float prixMax = Float.parseFloat(prixMaxField.getText());
        float prixMin = Float.parseFloat(prixMinField.getText());
        int nbrParticipants = Integer.parseInt(nbrParticipantsField.getText());

        // Séparation de l'ID du type de produit à partir de la sélection du ComboBox
        String selectedProduct = productComboBox.getValue();
        String[] parts = selectedProduct.split(":");
        int productId = Integer.parseInt(parts[0]);

        boolean status = statusCheckbox.isSelected();

        ProduitsService produitsService = new ProduitsService();
        Produits produit = produitsService.fetchProduitById(productId);

        Enchere nouvelleEnchere = new Enchere(dateDebut, dateFin, status, prixMin, prixMax, prixActuel, nbrParticipants, produit);

        EnchereService enchereService = new EnchereService();
        enchereService.add(nouvelleEnchere);

        showAlert(Alert.AlertType.INFORMATION, "Succès", "Enchère ajoutée avec succès.");
    }

    private boolean validateInput() {
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        float prixActuel, prixMax, prixMin;
        int nbrParticipants;

        if (dateDebut == null || dateFin == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une date de début et une date de fin.");
            return false;
        }

        if (dateDebut.isAfter(dateFin)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La date de début doit être antérieure à la date de fin.");
            return false;
        }

        try {
            prixActuel = Float.parseFloat(prixActuelField.getText());
            prixMax = Float.parseFloat(prixMaxField.getText());
            prixMin = Float.parseFloat(prixMinField.getText());
            nbrParticipants = Integer.parseInt(nbrParticipantsField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer des nombres valides pour les prix et le nombre de participants.");
            return false;
        }

        if (prixMin >= prixMax) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix minimum doit être inférieur au prix maximum.");
            return false;
        }

        if (prixActuel < prixMin || prixActuel > prixMax) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix actuel doit être compris entre le prix minimum et le prix maximum.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
