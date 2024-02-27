package controllers.UserAdminController;//package test;
import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AccueilAdminController implements Initializable {
    private  List<Admin>a;
    AdminService adminService=new AdminService() ;


    public VBox getPnItems() {
        return pnItems;
    }

    public void setPnItems(VBox pnItems) {
        this.pnItems = pnItems;
    }
    @FXML
    private Circle Profile;

    @FXML
    private VBox pnItems;

    @FXML
    private Button btnOverview;

    @FXML
    private Button ServiceButton;

    @FXML
    private Button PartenaireButton;

    @FXML
    private Button ProduitButton;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;



    private int n ;
    private Timeline timeline;

    private Admin adminSession ;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refrechPage();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), this::updatePage));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }
    public void refrechPage()
    {

        pnItems.getChildren().clear();

        try {
            Image image = new Image("/images/SakerHajji.png");
            Profile.setFill(new ImagePattern(image));


            a=adminService.readAll();
            int i = 0 ;
            for (Admin admin:a) {
                final int j = i;
                i++ ;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/ligne.fxml"));
                Node item = loader.load();
                LigneController loaderController = loader.getController();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateOfBirthString = sdf.format(admin.getDateDeNaissance());
                loaderController.setAdmin(admin);
                loaderController.setNom(admin.getNomUtilisateur());
                loaderController.setPrenom(admin.getPrenomUtilisateur());
                loaderController.setDateNaissance(dateOfBirthString);
                loaderController.setEmail(admin.getMailUtilisateur());
                loaderController.setProfile(admin.getAvatar());
                item.setOnMouseEntered(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFF0E7"));
                item.setOnMouseExited(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFFFFF"));

                pnItems.getChildren().add(item);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            a.clear();
        }
    }
    private void updatePage(ActionEvent events) {


        refrechPage();
        System.out.println("Mise à jour de la page...");
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == PartenaireButton) {
            pnlCustomer.setStyle("-fx-background-color: #FFFFFF");
            pnlCustomer.toFront();
            FXMLLoader p = new FXMLLoader(getClass().getResource("/interfacePartenaire/Afficher_Partenaire.fxml"));
            pnlCustomer.getChildren().removeAll();
            loadSofXMLContent();


        } else if (actionEvent.getSource() == ProduitButton) {
            pnlMenus.setStyle("-fx-background-color: #FFFFFF");
            pnlMenus.toFront();
            FXMLLoader p = new FXMLLoader(getClass().getResource("/interfaceProduit/AfficherProduits.fxml"));
            pnlMenus.getChildren().removeAll();
            loadhamzaXMLContent();
        } else if (actionEvent.getSource() == btnOverview) {
            timeline.stop();
            timeline.play();
            pnlOverview.setStyle("-fx-background-color: #fff0e7");
            pnlOverview.toFront();
        } else if (actionEvent.getSource() == ServiceButton) {
            FXMLLoader p = new FXMLLoader(getClass().getResource("/serviceApresVente/ligneServices.fxml"));
            pnlOrders.setStyle("-fx-background-color: #464F67");
            pnlOrders.toFront();
            loadServiceXMLContent();

        }
    }
    private void loadSofXMLContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/Afficher_Partenaire.fxml"));
            Pane sofXMLPane = loader.load();
            pnlCustomer.getChildren().clear(); // Use clear() instead of removeAll() for clarity
            pnlCustomer.getChildren().setAll(sofXMLPane);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    private void loadhamzaXMLContent() {
        try {
    timeline.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceProduit/AfficherProduits.fxml"));
            Pane sofXMLPane = loader.load();
            pnlMenus.getChildren().clear(); // Use clear() instead of removeAll() for clarity
            pnlMenus.getChildren().setAll(sofXMLPane);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void loadServiceXMLContent() {
        try {
            timeline.stop();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ServiceApresVenteAffiche.fxml"));
            Pane XMLPane = loader.load();
            pnlOrders.getChildren().clear();
            pnlOrders.getChildren().setAll(XMLPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public Admin getAdminSession() {
        return adminSession;
    }

    public void setAdminSession(Admin adminSession) {
        this.adminSession = adminSession;
    }
    public void setProfile(Circle profile) {
        Profile = profile;
    }
}


