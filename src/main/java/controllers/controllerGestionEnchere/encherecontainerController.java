package controllers.controllerGestionEnchere;

import Entity.entitiesEncheres.Enchere;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class encherecontainerController {

    @FXML
    private VBox vboxx;

    @FXML
    private ImageView image;
    @FXML
    private Button ReserverButton;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    private String[] colors = {"#B9E5FF", "#BDB2FE", "#FF506"};


    public void setData(Enchere enchere) {
        // String photoUrl = enchere.getIdProduit().getPhoto();
        Image image;

        //if (photoUrl != null) {
        //image = new Image(getClass().getResourceAsStream(photoUrl));
        // } else {
        image = new Image("/interfaceEnchere/mercedes.jpg");
        //}

        //image.setImage(image);
        label1.setText(enchere.getIdProduit().getLabelle());
       label2.setText(enchere.getIdProduit().getDescription());

        if (vboxx != null) {
            vboxx.setStyle("-fx-background-color: " + colors[(int) (Math.random() * colors.length)]);
        } else {
            System.err.println("HBox 'box' is null!");
        }
    }

}
