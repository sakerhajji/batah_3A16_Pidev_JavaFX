package controllers.ServiceApresVente;

import Entity.UserAdmin.Admin;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.UserAdmineServices.AdminService;
import controllers.UserAdminController.LigneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AffichageController implements Initializable {

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

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ligneServices.fxml"));
                Node item = loader.load();
                LigneServiceController l = loader.getController();
                String idService = String.valueOf(service.getIdService()) ;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String date = sdf.format(service.getDate());
                l.setIdService(idService);
                l.setDescription(service.getDescription());
                l.setType(service.getType());
                l.setDate(date);
                l.setStatus(String.valueOf(service.isStatus()));
                l.setIdPartenaire(service.getIdPartenaire().getNom());
                l.setIdAchats(String.valueOf(service.getIdAchats().getIdAchats()));
                item.setOnMouseEntered(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFF0E7"));
                item.setOnMouseExited(event -> pnItems.getChildren().get(j).setStyle("-fx-background-color: #FFFFFF"));

                pnItems.getChildren().add(item);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refrechPage() ;

    }
}
