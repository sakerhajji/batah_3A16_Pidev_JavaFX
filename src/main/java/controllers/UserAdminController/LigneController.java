package controllers.UserAdminController;

import Entity.UserAdmin.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LigneController implements Initializable {
    private Admin admin ;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    private Label DateNaissance;

    @FXML
    private Label Email;

    @FXML
    private Label Nom;

    @FXML
    private Label Prenom;

    @FXML
    private HBox itemC;
    private double xOffset , yOffset;

    public Label getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        DateNaissance.setText(dateNaissance);
    }

    public Label getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email.setText(email);
    }

    public Label getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom.setText(nom);
    }

    public Label getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom.setText(prenom);
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    void DetailleClick(ActionEvent event) throws IOException {
        System.out.println(admin);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/UpdateInterfaceAU.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            UpdateInterfaceAUControllers c = loader.getController();
            c.setAdmin(admin);
            c.setNomUtilisateur(admin.getNomUtilisateur());
            c.getPrenomUtilisateur().setText(admin.getPrenomUtilisateur());
            c.setAdresseEmail(admin.getMailUtilisateur());
            c.setDateDeNaissance(admin.getDateDeNaissance());
            if(admin.getNumUtilisateur()==null)c.setNumeroTelephone("svp taper votre numero");
            else c.setNumeroTelephone(admin.getNumUtilisateur());
            if(admin.getCinUtilisateur()==null)c.setNumeroCin("svp taper votre numero cin") ;
            else c.setNumeroCin(admin.getCinUtilisateur());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            root.setOnMousePressed((MouseEvent events) -> {
                xOffset = events.getSceneX();
                yOffset = events.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent events) -> {
                stage.setX(events.getScreenX() - xOffset);
                stage.setY(events.getScreenY() - yOffset);
            });
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


}
