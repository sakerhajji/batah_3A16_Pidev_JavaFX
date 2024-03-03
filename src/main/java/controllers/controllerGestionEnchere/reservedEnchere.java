package controllers.controllerGestionEnchere;

import Entity.UserAdmin.Membre;
import Entity.entitiesEncheres.Enchere;
import Entity.entitiesEncheres.ReservationEnchere;
import Services.EnchereService.ReservationEnchereService;
import Services.UserAdmineServices.MembreService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class reservedEnchere implements Initializable {

    @FXML
    private VBox reservedAuctionsVBox;

    private final ReservationEnchereService reservationEnchereService = new ReservationEnchereService();
    private final MembreService membreService = new MembreService();

    private Membre userId;

    public void setUserId(Membre userId) {
        this.userId = userId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId = new Membre(); // Initialisation de userId avec un nouvel objet Membre
        userId.setIdUtilisateur(2);
        loadReservedAuctions();
    }

    private void loadReservedAuctions() {
        // Récupérer toutes les réservations
        List<ReservationEnchere> reservedAuctions = reservationEnchereService.readAll();
        for (ReservationEnchere reservation : reservedAuctions) {
            // Vérifier si la réservation appartient à l'utilisateur spécifié
            if (reservation.getIdUser().getIdUtilisateur() == userId.getIdUtilisateur()) {
                Enchere enchere = reservation.getIdEnchere();
                if (enchere != null && enchere.getIdProduit() != null) {
                    AnchorPane encherePane = createAuctionPane(enchere);
                    reservedAuctionsVBox.getChildren().add(encherePane);
                }
            }
        }
    }

    private AnchorPane createAuctionPane(Enchere enchere) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(500);
        anchorPane.setPrefHeight(100);

        ImageView productImageView = new ImageView();
        productImageView.setFitWidth(100);
        productImageView.setFitHeight(100);


        // Charger l'image avec gestion des erreurs
        try {
            String imageName = enchere.getIdProduit().getPhoto();
            if (imageName != null && !imageName.isEmpty()) {
                String imagePath = "src/main/resources/images/imagesPartenaire/" + imageName;
                Image image = new Image(new File(imagePath).toURI().toString());
                productImageView.setImage(image);
            } else {
                // Utiliser une image par défaut si le chemin est vide ou null
                Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                productImageView.setImage(defaultImage);
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher une image par défaut
            Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
            productImageView.setImage(defaultImage);
        }

        // Créer et configurer les autres éléments visuels pour chaque détail de l'enchère
        Label titleLabel = new Label("Title: " + enchere.getIdProduit().getDescription());
        titleLabel.setStyle("-fx-font-weight: bold;");
        titleLabel.setLayoutX(120);
        titleLabel.setLayoutY(50);

        Label datesLabel = new Label("Start Date: " + enchere.getDateDebut() + "\nEnd Date: " + enchere.getDateFin());
        datesLabel.setLayoutX(120);
        datesLabel.setLayoutY(50);
        Label typeLabel = new Label("Type: " + enchere.getIdProduit().getType());
        Label descriptionLabel = new Label("Description: " + enchere.getIdProduit().getDescription());
        datesLabel.setLayoutX(120);
        datesLabel.setLayoutY(50);


        // Positionnement des éléments dans l'AnchorPane
        AnchorPane.setTopAnchor(productImageView, 10.0);
        AnchorPane.setLeftAnchor(productImageView, 10.0);
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 120.0);
        AnchorPane.setTopAnchor(datesLabel, 40.0);
        AnchorPane.setLeftAnchor(datesLabel, 120.0);
        AnchorPane.setTopAnchor(typeLabel, 70.0);
        AnchorPane.setLeftAnchor(typeLabel, 120.0);
        AnchorPane.setTopAnchor(descriptionLabel, 90.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 120.0);

        // Ajouter les éléments visuels à l'AnchorPane
        anchorPane.getChildren().addAll(productImageView, titleLabel, datesLabel, typeLabel, descriptionLabel);

        return anchorPane;
    }
}
