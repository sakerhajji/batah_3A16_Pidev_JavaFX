package controllers;//package test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilAdminController implements Initializable {
    @FXML
    private Circle profile ;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (int i = 0; i < 10; i++) {
                final int j = i;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ligne.fxml"));
                Node item = loader.load();
                LigneController l = loader.getController();


                l.setNom("aaaa");
                l.setEmail("sofianne");

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
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color: #FFFFFF");
            pnlCustomer.toFront();
        } else if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color: #53639F");
            pnlMenus.toFront();
        } else if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color: #02030A");
            pnlOverview.toFront();
        } else if (actionEvent.getSource() == btnOrders) {
            pnlOrders.setStyle("-fx-background-color: #464F67");
            pnlOrders.toFront();
        }
    }
}
