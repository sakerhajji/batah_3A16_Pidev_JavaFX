package controllers.UserAdminController;

import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.ResourceBundle;


public class UpdateInterfaceAUControllers implements Initializable {
    private Admin admin ;
    @FXML
    private TextField nomUtilisateur;
    @FXML
    private TextField prenomUtilisateur;
    @FXML
    private TextField adresseEmail;
    @FXML
    private DatePicker dateDeNaissance;
    @FXML
    private TextField numeroCin;
    @FXML
    private TextField numeroTelephone;



    @FXML
    void MisAJourClicked(ActionEvent event) {

        LocalDate localDate = dateDeNaissance.getValue();
        Date date = java.sql.Date.valueOf(localDate);
        AdminService adminService =new AdminService();

        admin.setNomUtilisateur(nomUtilisateur.getText());
        admin.setPrenomUtilisateur(prenomUtilisateur.getText());
        admin.setMailUtilisateur(adresseEmail.getText());
        admin.setNumUtilisateur(numeroTelephone.getText());
        admin.setDateDeNaissance(date);
        admin.setCinUtilisateur(numeroCin.getText());
        System.out.println(admin);
        adminService.updateCard(admin);
        System.out.println("done");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));
        AccueilAdminController c=loader.getController();
        //c.refrechPage();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();




    }

    @FXML
    void fermerClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public TextField getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail.setText(adresseEmail);
    }

    public DatePicker getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {

        this.dateDeNaissance.setValue(dateDeNaissance.toLocalDate());
    }

    public TextField getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur.setText(nomUtilisateur);
    }

    public TextField getNumeroCin() {
        return numeroCin;
    }

    public void setNumeroCin(String numeroCin) {
        this.numeroCin.setText(numeroCin);
    }

    public TextField getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone.setText(numeroTelephone);
    }

    public TextField getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur.setText(prenomUtilisateur);
    }
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}

