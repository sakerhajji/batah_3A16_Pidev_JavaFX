package controllers.ServiceApresVente;


import Entity.UserAdmin.Admin;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageController implements Initializable {
    private Timeline timeline;

    private Admin adminSession ;

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
    private VBox pnItems;

    @FXML
    private Label status;

    @FXML
    private Label type;

    List<ServiceApresVente>serviceApresVenteList ;
    public void refrechPage()
    {
        pnItems.getChildren().clear();

        try {

            ServiceApresVentS s = new ServiceApresVentS() ;
            serviceApresVenteList=s.readAll();


            int i = 0 ;

            for (ServiceApresVente service:serviceApresVenteList) {
                final int j = i;
                i++ ;

                String idPartenaireText = String.valueOf(service.getIdPartenaire().getId());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ligneServices.fxml"));
                Node item = loader.load();
                LigneServiceController l = loader.getController();
                l.setSa(service);
                String idService = String.valueOf(service.getIdService());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String date = sdf.format(service.getDate());
                l.setIdService(idService);
                l.setDescription(service.getDescription());
                l.setType(service.getType());
                l.setDate(date);
                l.setStatus(String.valueOf(service.isStatus()));
                if (idPartenaireText!=null) {
                    l.setIdPartenaire(service.getIdPartenaire().getNom());
                } else {
                    l.setIdPartenaire("Null");
                }
                l.setIdAchats(String.valueOf(service.getIdAchats().getIdAchats()));
                item.setOnMouseEntered(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFF0E7"));
                item.setOnMouseExited(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFFFFF"));

                pnItems.getChildren().add(item);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void ajouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/serviceApresVente/ServiceApresVenteAjouter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            refrechPage();
            stage.setTitle("Ajouter Reclamation");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refrechPage() ;
        ServiceApresVentS ServiceApresVentS = new ServiceApresVentS();
        timeline = new Timeline(new KeyFrame(Duration.seconds(3), this::updatePage));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



    }
    private void updatePage(ActionEvent events) {


        refrechPage();
        System.out.println("Mise à jour de la page 1..");
    }
}
