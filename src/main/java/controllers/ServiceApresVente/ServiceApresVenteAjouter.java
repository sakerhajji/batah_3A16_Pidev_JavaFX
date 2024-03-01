package controllers.ServiceApresVente;

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
import java.util.List;
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

    ServiceApresVentS sav = new ServiceApresVentS();
    ProduitsService ps = new ProduitsService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateUserComboBox();
    }

    public void populateUserComboBox() {
        List<Achats> Achat = ps.readAllAchat();
        ObservableList<Integer> achtIds = FXCollections.observableArrayList();

        for (Achats user : Achat) {
            achtIds.add(user.getIdAchats());
        }

        idAchat.setItems(achtIds);
    }

    @FXML
    void addService(ActionEvent event) {
        LocalDate dat = date.getValue();
        if (dat == null) {
            showAlert("Date non sélectionnée", "Veuillez sélectionner une date.");
            return;
        }
        Date sqlDate = java.sql.Date.valueOf(dat);

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

        try {
            Stage stage = (Stage) description.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    void supprimer(ServiceApresVente event) {

        ServiceApresVentS ps=new ServiceApresVentS();
        ps.delete(event);
        //showAffectation(id);
    }

}