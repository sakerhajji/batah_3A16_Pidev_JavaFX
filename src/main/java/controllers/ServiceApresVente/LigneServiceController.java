package controllers.ServiceApresVente;

import Entity.entitiesServiceApresVente.ServiceApresVente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LigneServiceController implements Initializable {
    ServiceApresVente sa;
    private double xOffset, yOffset;

    public ServiceApresVente getSa() {
        return sa;
    }

    public void setSa(ServiceApresVente sa) {
        this.sa = sa;
    }

    @FXML
    private Label date;

    @FXML
    private Label description;

    @FXML
    private Label idAchats;

    @FXML
    private Label idPartenaire;

    @FXML
    private Label idService;

    @FXML
    private HBox itemC;

    @FXML
    private Label status;

    @FXML
    private Label type;

    public Label getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public Label getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public Label getIdAchats() {
        return idAchats;
    }

    public void setIdAchats(String idAchats) {
        this.idAchats.setText(idAchats);
    }

    public Label getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(String idPartenaire) {
        this.idPartenaire.setText(idPartenaire);
    }

    public Label getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService.setText(idService);
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    public Label getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    public Label getType() {
        return type;
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    @FXML
    void detailleClicked(ActionEvent event) throws IOException {


        System.out.println(sa.getIdService());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ServiceApresVenteUpdate.fxml"));
        Parent root = null;
        root = loader.load();
        Updatecontroller c = loader.getController();

        c.setDescription(sa.getDescription());

        // c.setType(sa.getType());
        c.setNomPartenaire(sa.getIdPartenaire().getNom());
        c.setDate(sa.getDate());
        Updatecontroller u = loader.getController();
        u.init(sa.getIdService());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
