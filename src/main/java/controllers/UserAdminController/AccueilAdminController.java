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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AccueilAdminController implements Initializable {
    @FXML
    private Circle profile;
    @FXML
    private VBox pnItems;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    public void setProfile(Circle profile) {
        Profile = profile;
    }

    private int n ;
    private Timeline timeline;

    private Admin adminSession ;

    public Admin getAdminSession() {
        return adminSession;
    }

    public void setAdminSession(Admin adminSession) {
        this.adminSession = adminSession;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            timeline = new Timeline(new KeyFrame(Duration.seconds(10), this::updatePage));
            timeline.setCycleCount(Timeline.INDEFINITE); // Pour répéter indéfiniment
            timeline.play();
            Image image = new Image("/images/SakerHajji.png");
         Profile.setFill(new ImagePattern(image));
            List<Admin>a;
            AdminService adminService=new AdminService() ;
            a=adminService.readAll();
            int i = 0 ;
            n=a.size();

            for (Admin admin:a) {
                final int j = i;
                i++ ;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/ligne.fxml"));
                Node item = loader.load();
                LigneController l = loader.getController();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateOfBirthString = sdf.format(admin.getDateDeNaissance());

                l.setAdmin(admin);
                l.setNom(admin.getNomUtilisateur());
                l.setPrenom(admin.getPrenomUtilisateur());
                l.setDateNaissance(dateOfBirthString);
                l.setEmail(admin.getMailUtilisateur());

                // Give the items some effect
                item.setOnMouseEntered(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFF0E7"));
                item.setOnMouseExited(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFFFFF"));

                pnItems.getChildren().add(item);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Circle Profile;
    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color: #FFFFFF");
            pnlCustomer.toFront();
            FXMLLoader p = new FXMLLoader(getClass().getResource("/interfacePartenaire/Afficher_personne.fxml"));
            pnlCustomer.getChildren().removeAll();
            loadSofXMLContent();


        } else if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color: #FFFFFF");
            pnlMenus.toFront();
            FXMLLoader p = new FXMLLoader(getClass().getResource("/interfaceProduit/AfficherProduits.fxml"));
            pnlMenus.getChildren().removeAll();
            loadhamzaXMLContent();
        } else if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color: #fff0e7");
            pnlOverview.toFront();
        } else if (actionEvent.getSource() == btnOrders) {
            pnlOrders.setStyle("-fx-background-color: #464F67");
            pnlOrders.toFront();
        }
    }
    private void loadSofXMLContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacePartenaire/Afficher_personne.fxml"));
            Pane sofXMLPane = loader.load();
            pnlCustomer.getChildren().clear(); // Use clear() instead of removeAll() for clarity
            pnlCustomer.getChildren().setAll(sofXMLPane);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    private void loadhamzaXMLContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceProduit/AfficherProduits.fxml"));
            Pane sofXMLPane = loader.load();
            pnlMenus.getChildren().clear(); // Use clear() instead of removeAll() for clarity
            pnlMenus.getChildren().setAll(sofXMLPane);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void updatePage(ActionEvent events) {
        try {

            List<Admin>a;
            AdminService adminService=new AdminService() ;
            a=adminService.readAll();
            int k = a.size() ;
            int x = a.size();
            if(x>n){

            for (int  i = (a.size()-x)-1;i<a.size();i++) {
                final int j = k;
                k++ ;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/ligne.fxml"));
                Node item = loader.load();
                LigneController l = loader.getController();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateOfBirthString = sdf.format(a.get(i).getDateDeNaissance());

                l.setNom(a.get(i).getNomUtilisateur());
                l.setPrenom(a.get(i).getPrenomUtilisateur());
                l.setDateNaissance(dateOfBirthString);
                l.setEmail(a.get(i).getMailUtilisateur());

                // Give the items some effect
                item.setOnMouseEntered(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFF0E7"));
                item.setOnMouseExited(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFFFFF"));

                pnItems.getChildren().add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Mise à jour de la page...");
    }
}


