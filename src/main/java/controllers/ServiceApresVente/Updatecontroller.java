package controllers.ServiceApresVente;

import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class Updatecontroller {

    @FXML
    private Text IdUtlisateur;

    @FXML
    private TextField NomPartenaire;

    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private ComboBox<String> type;
    private int idService;

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    @FXML
    void MisAJourClicked(ActionEvent event) {
        String nouveauNomPartenaire = NomPartenaire.getText();
        String nouvelleDescription = description.getText();
        Date nouvelleDate = Date.valueOf(date.getValue());
        String nouveauType = type.getValue();

        ServiceApresVentS s=new ServiceApresVentS();
        ServiceApresVente service=new ServiceApresVente(getIdService(),nouvelleDescription,nouveauType,nouvelleDate);
        s.updateS(service);
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();
    }

    @FXML
    void SupprimerClicked(ActionEvent event) {
        ServiceApresVentS s=new ServiceApresVentS();
        s.delete(getIdService());
        Stage stage = (Stage) description.getScene().getWindow();
        stage.close();
    }



void init(int id)
{
    setIdService(id);

}

    public Text getIdUtlisateur() {
        return IdUtlisateur;
    }

    public void setIdUtlisateur(String idUtlisateur) {
        IdUtlisateur.setText(idUtlisateur);
    }

    public TextField getNomPartenaire() {
        return NomPartenaire;
    }

    public void setNomPartenaire(String nomPartenaire) {
        NomPartenaire.setText(nomPartenaire);
    }

    public DatePicker getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date.setValue(date.toLocalDate());
    }

    public TextField getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public ComboBox<String> getType() {
        return type;
    }

    public void setType(ComboBox<String> type) {
        this.type = type;
    }

    @FXML
    void affecteClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ServiceApresVenteAffectation.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AffectationController controller = loader.getController();

        controller.init(getIdService());


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Affectation");
        stage.showAndWait();

    }
}
