package controllers.ServiceApresVente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LigneServiceController implements Initializable {

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
    void DetailleClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
