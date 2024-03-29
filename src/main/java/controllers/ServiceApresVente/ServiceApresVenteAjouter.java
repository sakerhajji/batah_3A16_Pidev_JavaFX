package controllers.ServiceApresVente;

import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesProduits.Achats;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.ServiceProduit.ProduitsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ServiceApresVenteAjouter implements Initializable {
    @FXML
    private Button addService;

    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private ComboBox<String> type;

    @FXML
    private ChoiceBox<Integer> idAchat;

    @FXML
    private ComboBox<Partenaire> idPartenaire;

    ServiceApresVentS sav = new ServiceApresVentS();
    ProduitsService ps = new ProduitsService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateUserComboBox();
    }

    public void populateUserComboBox() {
        ObservableList<Integer> achtIds = FXCollections.observableArrayList();
        ObservableList<Partenaire> partenaires = FXCollections.observableArrayList();

        // Lire les achats et les partenaires depuis les services appropriés
        for (Achats achat : ps.readAllAchat()) {
            achtIds.add(achat.getIdAchats());
        }
        idAchat.setItems(achtIds);

// Itération sur la liste des partenaires
        for (Partenaire partenaire : partenaires) {
            // Faites quelque chose avec chaque partenaire, par exemple :
            System.out.println(partenaire.getId() + ": " + partenaire.getNom());
        }
    }

    @FXML
    void addService(ActionEvent event) {
        LocalDate dat = date.getValue();
        if (dat == null) {
            showAlert("Date non sélectionnée", "Veuillez sélectionner une date.");
            return;
        }
        Date sqlDate = Date.valueOf(dat);

        String descript = description.getText();
        if (descript.isEmpty()) {
            showAlert("Description manquante", "Veuillez saisir une description pour le service après-vente.");
            return;
        }

        String tp = type.getValue();
        if (tp == null || tp.isEmpty()) {
            showAlert("Type non sélectionné", "Veuillez sélectionner un type pour le service après-vente.");
            return;
        }

        Integer selectedAchatid = idAchat.getValue();
        if (selectedAchatid == null) {
            showAlert("Achat non sélectionné", "Veuillez sélectionner un achat pour le service après-vente.");
            return;
        }



        Achats achat = ps.readbyIdAchat(selectedAchatid);

        ServiceApresVente service = new ServiceApresVente(descript, tp, sqlDate, achat);
        sav.add(service);
        System.out.println("Succès");

        closeWindow();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) addService.getScene().getWindow();
        stage.close();
    }
}