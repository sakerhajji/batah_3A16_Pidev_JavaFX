package controllers.controllerGestionEnchere;


import Entity.entitiesEncheres.Enchere;
import Services.EnchereService.EnchereService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class modifypourclient {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField minPriceTextField;

    @FXML
    private TextField maxPriceTextField;

    @FXML
    private TextField currentPriceTextField;

    @FXML
    private TextField nbrParticipantsField;

    @FXML
    private ComboBox<Integer> productComboBox; // Assuming the descriptions are strings

    @FXML
    private Button saveButton;

    private Stage dialogStage;

    private Enchere enchere;
    private EnchereService enchereService =new EnchereService();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }



    private void populateProductComboBox() {
        List<Enchere> enchereList = enchereService.readAll();
        ObservableList<Integer> productDescriptions = FXCollections.observableArrayList();
        for (Enchere enchere : enchereList) {
            productDescriptions.add(enchere.getIdProduit().getIdProduit());
        }
        productComboBox.setItems(productDescriptions);
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
        startDatePicker.setValue(enchere.getDateDebut());
        endDatePicker.setValue(enchere.getDateFin());
        minPriceTextField.setText(String.valueOf(enchere.getPrixMin()));
        maxPriceTextField.setText(String.valueOf(enchere.getPrixMax()));
        currentPriceTextField.setText(String.valueOf(enchere.getPrixActuelle()));
        nbrParticipantsField.setText(String.valueOf(enchere.getNbrParticipants()));
        productComboBox.setValue(enchere.getIdProduit().getIdProduit());
        // Assuming getPrixActuel() returns the current price

    }

    @FXML
    void initialize() {
        System.out.println("Initializing ModifyEnchereController...");
        System.out.println("Product ComboBox: " + productComboBox); // Debugging statement
        enchereService = new EnchereService();
        populateProductComboBox();
    }


    @FXML
    private void handleSaveAction() {
        if (isInputValid()) {
            // Update the enchere object with the new values from the UI
            enchere.setDateDebut(startDatePicker.getValue());
            enchere.setDateFin(endDatePicker.getValue());
            enchere.setPrixMin(Float.parseFloat(minPriceTextField.getText()));
            enchere.setPrixMax(Float.parseFloat(maxPriceTextField.getText()));
            enchere.setPrixActuelle(Float.parseFloat(currentPriceTextField.getText()));
            enchere.setNbrParticipants(Integer.parseInt(nbrParticipantsField.getText()));
            enchere.getIdProduit().setIdProduit(productComboBox.getValue());

            // Call the update method of EnchereService to persist the changes in the database
            EnchereService enchereService = new EnchereService();
            enchereService.update(enchere);

            // Close the dialog/stage
            dialogStage.close();
        }
    }


    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        float minPrice = 0;
        float maxPrice = 0;

        if (startDate == null || endDate == null) {
            errorMessage += "Start date and end date must be specified!\n";
        }

        try {
            minPrice = Float.parseFloat(minPriceTextField.getText());
            maxPrice = Float.parseFloat(maxPriceTextField.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Invalid price format!\n";
        }

        if (minPriceTextField.getText() == null || minPriceTextField.getText().trim().isEmpty()) {
            errorMessage += "Min price must be specified!\n";
        }

        if (maxPriceTextField.getText() == null || maxPriceTextField.getText().trim().isEmpty()) {
            errorMessage += "Max price must be specified!\n";
        }

        if (nbrParticipantsField.getText() == null || nbrParticipantsField.getText().trim().isEmpty()) {
            errorMessage += "Nbr participants must be specified!\n";
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            errorMessage += "Invalid date range!\n";
        }

        if (minPrice >= maxPrice) {
            errorMessage += "Max price must be greater than min price!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
