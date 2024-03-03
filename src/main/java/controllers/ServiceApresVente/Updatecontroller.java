package controllers.ServiceApresVente;

import Entity.ControleDeSaisieClass.ControleDeSaisieClass;
import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.servicePartenaire.partenaireService;
import controllers.controllerPartenaire.AffectationPartenaireController;
import controllers.controllerPartenaire.Afficher_PartenaireController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Updatecontroller implements Initializable {
    private ControleDeSaisieClass controle = new ControleDeSaisieClass();
    @FXML
    private TableView<ServiceApresVente> table;
    @FXML
    private TextField idPartenaire;

    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private TextField status;

    @FXML
    private ComboBox<?> type;
    @FXML
    private Button  monBouton;
    private TableColumn idReclamtion;

    private Object showPartenaires;
    private int id;
    ;


    @FXML
    void MisAJourClicked(ActionEvent event) {
        if (controle.checkText(description.getText()) &&
                controle.chekNumero(status.getText()) &&
                controle.checkText((String) type.getValue()) &&
                controle.checkText(idPartenaire.getText()) &&
                controle.isDateValidAndOver18(date.getValue())) {

            LocalDate localDate = date.getValue();
            Date dateValue = Date.valueOf(localDate);

            ServiceApresVente service = new ServiceApresVente();
            service.setDescription(description.getText());
            service.setType((String) type.getValue());
            service.setDate(dateValue);
            service.setStatus(Boolean.parseBoolean(status.getText()));
            //service.setIdPartenaire((idPartenaire.getText());
            String idPartenaireText = idPartenaire.getText();
            Partenaire idPartenaire = new Partenaire();


            ServiceApresVentS serviceApresVentS = new ServiceApresVentS();
            serviceApresVentS.update(service);
            System.out.println(service);
            System.out.println("done");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ServiceApresVenteAffiche.fxml"));
            AffichageController affichageController = loader.getController();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le service après-vente a été mis à jour avec succès.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs invalides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier les champs et remplir correctement les informations.");
            alert.showAndWait();
        }
    }

    @FXML
    void SupprimerClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    void affecteClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/Afficher_Partenaire.fxml"));
            Parent root = loader.load();

            Afficher_PartenaireController Controller = loader.getController();
           //Controller.init(id);
           // String idBouton = monBouton.getId();

            //  c.init(event.getId());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Affectation");
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void showAffectation(int id) {
        ServiceApresVentS sav = new ServiceApresVentS();
        ServiceApresVente affectations = sav.readById(id);

        if (affectations != null) {
            ObservableList<ServiceApresVente> observableAffectations = FXCollections.observableArrayList(affectations);
            table.setItems(observableAffectations);
        }
    }
        private void showPartenaires() {
            // Récupérer la liste des partenaires depuis le service approprié
            partenaireService partenaireService = new partenaireService();
            List<Partenaire> partenaires = new ArrayList<>();

            // Effacer le contenu de la table des partenaires (si nécessaire)
            // tablePartenaires.getItems().clear();

            // Ajouter les partenaires à la table
            for (Partenaire partenaire : partenaires) {
                // Ajouter le partenaire à la table
                // tablePartenaires.getItems().add(partenaire);
            }
        }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAffectation(id);

    }
}
