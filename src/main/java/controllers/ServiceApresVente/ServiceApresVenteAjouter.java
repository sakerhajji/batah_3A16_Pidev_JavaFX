package controllers.ServiceApresVente;
import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Achats;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.ServiceProduit.ProduitsService;
import Services.UserAdmineServices.MembreService;
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
    private ComboBox<?> type;
    @FXML
    private ChoiceBox<Integer> idAchat;
    ServiceApresVentS sav=new ServiceApresVentS();
    ProduitsService ps = new ProduitsService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateUserComboBox();
    }
    public void populateUserComboBox() {


        List<Achats> Achat = null;

        Achat = ps.readAllAchat();

        ObservableList<Integer> achtIds = FXCollections.observableArrayList();

        for (Achats user : Achat) {
            achtIds.add(user.getIdAchats());
        }

        idAchat.setItems(achtIds);
    }
    @FXML
    void addService(ActionEvent event) {
        LocalDate dat= date.getValue();
        Date date = java.sql.Date.valueOf(dat);
        String descript=description.getText();
        String tp = (String) type.getValue();
        Integer selectedAchatid = idAchat.getValue();

        Achats achat=ps.readbyIdAchat(selectedAchatid);


        ServiceApresVente service=new ServiceApresVente(descript,tp,date,achat);
        sav.add(service);
        System.out.println("succees");

        try {
            Stage stage = (Stage) description.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
