package controllers.controllerGestionEnchere;

import Entity.UserAdmin.Membre;
import Entity.entitiesEncheres.Enchere;
import Entity.entitiesEncheres.ReservationEnchere;
import Services.EnchereService.EnchereService;
import Services.EnchereService.ReservationEnchereService;
import Services.UserAdmineServices.MembreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static APIEnchere.EmailSender.sendConfirmationEmail;

public class testController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox auctionsVBox;


    private final EnchereService enchereService = new EnchereService();
    private final ReservationEnchereService reservationEnchereService = new ReservationEnchereService();
    private final MembreService membreService=new MembreService();


    private int reservationCount = 0; // Variable pour compter le nombre de réservations

    // ID de l'utilisateur, vous devez définir cette valeur avant d'utiliser le contrôleur
    private Membre userId;

    // Méthode d'initialisation
    @FXML
    public void initialize() {
        // Recherche de l'utilisateur dans la base de données en fonction de l'ID
        userId = membreService.readById(12); // Remplacer 12 par l'ID de l'utilisateur
        loadAuctions();
    }


    // Chargement des enchères dans le VBox
    // Chargement des enchères dans le VBox
    private void loadAuctions() {
        List<Enchere> enchereList = enchereService.readAll();
        enchereList.sort(Comparator.comparingInt(Enchere::getIdEnchere)); // Tri par IDEnchere
        for (Enchere enchere : enchereList) {
            AnchorPane encherePane = createAuctionPane(enchere);
            auctionsVBox.getChildren().add(encherePane);
        }
    }



    // Rechercher les enchères en fonction du texte saisi dans la barre de recherche
    @FXML
    private void searchAuctions() {
        String searchText = searchField.getText().toLowerCase();

        // Obtenez toutes les enchères
        List<Enchere> allAuctions = enchereService.readAll();

        // Filtrer les enchères en fonction du texte de recherche
        List<Enchere> searchResults = allAuctions.stream()
                .filter(enchere -> enchere.getIdProduit().getDescription().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Effacez les enchères précédentes pour afficher les nouveaux résultats
        auctionsVBox.getChildren().clear();

        // Afficher les résultats de la recherche
        for (Enchere enchere : searchResults) {
            AnchorPane encherePane = createAuctionPane(enchere);
            auctionsVBox.getChildren().add(encherePane);
        }
    }

    // Création de l'élément d'enchère pour l'affichage dans le VBox
    private AnchorPane createAuctionPane(Enchere enchere) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(500);
        anchorPane.setPrefHeight(100);

        Label titleLabel = new Label(enchere.getIdProduit().getDescription());
        titleLabel.setLayoutX(120);
        titleLabel.setLayoutY(20);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label datesLabel = new Label("Start Date: " + enchere.getDateDebut() + "\nEnd Date: " + enchere.getDateFin());
        datesLabel.setLayoutX(120);
        datesLabel.setLayoutY(50);

        Label minPriceLabel = new Label("Mise à prix: " + enchere.getPrixMin());
        minPriceLabel.setLayoutX(120);
        minPriceLabel.setLayoutY(90);
        minPriceLabel.setStyle("-fx-background-color: red; -fx-padding: 5px; -fx-text-fill: white;");

        Button reserveButton = new Button("Reserve");
        reserveButton.setLayoutX(300);
        reserveButton.setLayoutY(40);
        // Associer la méthode handleReserveButton à l'action du bouton
        reserveButton.setOnAction(event -> handleReserveButton(enchere));

        Button detailButton = new Button("Detail");
        detailButton.setLayoutX(400);
        detailButton.setLayoutY(40);
        // Set action for the "Detail" button
        detailButton.setOnAction(event -> showAuctionDetails(enchere));

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        try {
            // Charger l'image à partir du chemin spécifié dans Enchere
            String imageName = enchere.getIdProduit().getPhoto();
            if (imageName != null && !imageName.isEmpty()) {
                String imagePath = "/images/imagesPartenaire/" + imageName; // Le chemin doit être relatif au répertoire resources
                InputStream inputStream = getClass().getResourceAsStream(imagePath);
                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } else {
                    // Utiliser une image par défaut si le chemin n'est pas valide
                    Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                    imageView.setImage(defaultImage);
                }
            } else {
                // Utiliser une image par défaut si le chemin est vide ou null
                Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                imageView.setImage(defaultImage);
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher une image par défaut
            e.printStackTrace(); // Imprimer la trace de la pile pour déboguer l'erreur
            Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
            imageView.setImage(defaultImage);
        }

        anchorPane.getChildren().addAll(titleLabel, datesLabel, minPriceLabel, reserveButton, imageView, detailButton);

        return anchorPane;
    }



    private void handleReserveButton(Enchere enchere) {
        // Utiliser l'ID de l'utilisateur courant pour la réservation
        int selectedUserId = userId.getIdUtilisateur(); // Utiliser l'ID de l'utilisateur courant

        // Comparer reservationCount avec enchere.getNbrParticipants()
        if (reservationCount < enchere.getNbrParticipants()) {
            reservationEnchereService.add(new ReservationEnchere(enchere, userId, LocalDate.now(), true));
            reservationCount++;
            showConfirmationDialog("Réservation confirmée", "Bonjour cher client, félicitations, vous pouvez participer à cette enchère. Veuillez entrer votre e-mail pour recevoir la confirmation.");
            String membreEmail = membreService.readById(selectedUserId).getMailUtilisateur();
            sendConfirmationEmail(membreEmail, enchere.getIdProduit().getDescription(), "Cher Membre");
        } else {
            reservationEnchereService.add(new ReservationEnchere(enchere, userId, LocalDate.now(), false));
            showConfirmationDialog("Réservation ajoutée", "Votre réservation a été ajoutée mais n'a pas été confirmée.");
        }

    }


    private void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Méthode pour afficher les détails d'une enchère dans une nouvelle fenêtre
    private void showAuctionDetails(Enchere enchere) {
        // Créer une nouvelle fenêtre pour afficher les détails de l'enchère
        Stage stage = new Stage();
        stage.setTitle("Détails de l'enchère");

        // Créer un StackPane pour centrer l'image
        StackPane stackPane = new StackPane();

        // Créer une VBox pour contenir les détails
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // Créer une ImageView pour afficher l'image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300); // Ajuster la largeur de l'image
        imageView.setPreserveRatio(true); // Conserver les proportions de l'image

        try {
            // Charger l'image à partir du chemin spécifié dans l'enchère
            String imageName = enchere.getIdProduit().getPhoto();
            if (imageName != null && !imageName.isEmpty()) {
                String imagePath = "/interfaceEnchere/" + imageName; // Le chemin doit être relatif au répertoire resources
                InputStream inputStream = getClass().getResourceAsStream(imagePath);
                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                } else {
                    // Utiliser une image par défaut si le chemin n'est pas valide
                    Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                    imageView.setImage(defaultImage);
                }
            } else {
                // Utiliser une image par défaut si le chemin est vide ou null
                Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                imageView.setImage(defaultImage);
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher une image par défaut
            e.printStackTrace();
            Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
            imageView.setImage(defaultImage);
        }

        // Ajouter l'ImageView à StackPane et centrer
        stackPane.getChildren().add(imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);

        // Ajouter les détails de l'enchère à la VBox
        Label typeLabel = new Label("Type: " + enchere.getIdProduit().getType());
        Label descriptionLabel = new Label("Description: " + enchere.getIdProduit().getDescription());
        Label priceLabel = new Label("Price: " + enchere.getIdProduit().getPrix());
        Label labelLabel = new Label("Label: " + enchere.getIdProduit().getLabelle());
        Label warrantyLabel = new Label("Warranty Period: " + enchere.getIdProduit().getPeriodeGarentie());

        vbox.getChildren().addAll(typeLabel, descriptionLabel, priceLabel, labelLabel, warrantyLabel);

        // Ajouter le StackPane contenant l'image à la VBox
        vbox.getChildren().add(stackPane);

        // Créer une scène avec la VBox
        Scene scene = new Scene(vbox, 400, 400);
        stage.setWidth(600);
        stage.setScene(scene);

        // Afficher la fenêtre
        stage.show();
    }



    public void handleVentesEnCours(ActionEvent actionEvent) {
    }

    public void handleAutreTexte1(ActionEvent actionEvent) {
    }

    public void handleAutreTexte2(ActionEvent actionEvent) {
    }

}
