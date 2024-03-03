package controllers.ControllerProduits;
import Entity.entitiesProduits.Produits;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class CarsController {
    @FXML
    private HBox box;

    @FXML
    private ImageView imageProduit;

    @FXML
    private Label txtdescription;

    @FXML
    private Label txtlabelle;

    private String[] colors = {"B9E5FF", "BDB2FE", "FF506"};

    public void setData(Produits produits) {
        Image image = new Image(getClass().getResourceAsStream(produits.getPhoto()));
        imageProduit.setImage(image);
        txtlabelle.setText(produits.getLabelle());
        txtdescription.setText(produits.getDescription());

        box.setStyle("-fx-background-color: " + Color.web(colors[(int) (Math.random() * colors.length)]));

    }

    }

