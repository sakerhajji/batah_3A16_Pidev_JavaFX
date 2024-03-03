package controllers.controllerGestionEnchere;

import Entity.UserAdmin.Membre;
import Entity.entitiesEncheres.Enchere;
import Entity.entitiesEncheres.ReservationEnchere;
import Services.EnchereService.EnchereService;
import Services.EnchereService.ReservationEnchereService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ClientEnchereGridController implements Initializable {

    @FXML
    private FlowPane productFlowPane;

    @FXML
    private HBox recentAuctionsHBox;

    @FXML
    private VBox availableAuctionsVBox;

    private final EnchereService enchereService = new EnchereService();
    private final ReservationEnchereService reservationEnchereService = new ReservationEnchereService();

    private int reservationCount = 0; // Variable pour compter le nombre de réservations

    // ID de l'utilisateur, vous devez définir cette valeur avant d'utiliser le contrôleur
    private Membre userId;

    public void setUserId(Membre userId) {
        this.userId = userId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userId = new Membre(); // Initialisation de userId avec un nouvel objet Membre
        userId.setIdUtilisateur(2);
        List<Enchere> allAuctions = enchereService.readAll();

        for (Enchere enchere : allAuctions) {
            if (recentAuctionsHBox.getChildren().size() < 6) {
                recentAuctionsHBox.getChildren().add(createAuctionNode(enchere));
            }
            availableAuctionsVBox.getChildren().add(createAuctionNode(enchere));
        }
    }

    private Node createAuctionNode(Enchere enchere) {
        ImageView imageView;
        String imageDataString = enchere.getIdProduit().getPhoto();

        if (imageDataString != null && !imageDataString.isEmpty()) {
            Image image = new Image(getClass().getResourceAsStream("/interfaceEnchere/mercedes.jpg"));
            imageView = new ImageView(image);
        } else {
            Image defaultImage = new Image("/interfaceEnchere/Capture.png");
            imageView = new ImageView(defaultImage);
        }

        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Button reserveButton = new Button("Réserver");
        reserveButton.setOnAction(event -> handleReserveButton(enchere));

        Button detailButton = new Button("Détail");
        detailButton.setOnAction(event -> showAuctionDetails(enchere));

        AnchorPane productNode = new AnchorPane();
        productNode.setPrefSize(200, 200);

        AnchorPane.setTopAnchor(imageView, 10.0);
        AnchorPane.setLeftAnchor(imageView, 10.0);

        AnchorPane.setTopAnchor(reserveButton, 170.0);
        AnchorPane.setLeftAnchor(reserveButton, 60.0);

        AnchorPane.setTopAnchor(detailButton, 170.0);
        AnchorPane.setLeftAnchor(detailButton, 120.0);

        productNode.getChildren().addAll(imageView, reserveButton, detailButton);

        return productNode;
    }


    private void handleReserveButton(Enchere enchere) {
        // Utiliser l'ID du membre 2 pour la réservation
        int selectedUserId = 2;

        // Comparer reservationCount avec enchere.getNbrParticipants()
        if (reservationCount < enchere.getNbrParticipants()) {
            reservationEnchereService.add(new ReservationEnchere(enchere, userId, LocalDate.now(), true));
            reservationCount++;
            showConfirmationDialog("Réservation confirmée", "Votre réservation a été ajoutée avec succès.");
        } else {
            reservationEnchereService.add(new ReservationEnchere(enchere, userId, LocalDate.now(), false));
            showConfirmationDialog("Réservation ajoutée", "Votre réservation a été ajoutée mais n'a pas été confirmée.");
        }
    }

    private void showAuctionDetails(Enchere enchere) {
        // Code pour afficher les détails de l'enchère dans une nouvelle fenêtre
    }

    private void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
