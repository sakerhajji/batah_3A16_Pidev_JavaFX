package controllers.controllerGestionEnchere;
import Entity.UserAdmin.Membre;
import Entity.entitiesEncheres.Enchere;
import Services.EnchereService.EnchereService;
import Services.UserAdmineServices.MembreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListEnchereClientController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox auctionsVBox;

    @FXML
    private DatePicker datePicker;

    private final EnchereService enchereService = new EnchereService();
    private final MembreService membreService = new MembreService();

    private Membre userId;

    public void setUserId(Membre userId) {
        this.userId = userId;
    }

    @FXML
    public void initialize() {
        userId = membreService.readById(3); // Remplacer 3 par l'ID de l'utilisateur
        loadUserAuctions();
        auctionsVBox.setPrefWidth(520); // Ajustement de la largeur de VBox
    }

    private void loadUserAuctions() {
        List<Enchere> userAuctions = enchereService.readAll().stream()
                .filter(enchere -> enchere.getIdProduit().getId().getIdUtilisateur() == userId.getIdUtilisateur())
                .collect(Collectors.toList());

        for (Enchere enchere : userAuctions) {
            AnchorPane encherePane = createAuctionPane(enchere);
            auctionsVBox.getChildren().add(encherePane);
        }
    }

    @FXML
    private void searchAuctions() {
        String searchText = searchField.getText().toLowerCase();

        List<Enchere> searchResults = enchereService.readAll().stream()
                .filter(enchere -> enchere.getIdProduit().getType().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        auctionsVBox.getChildren().clear();

        for (Enchere enchere : searchResults) {
            AnchorPane encherePane = createAuctionPane(enchere);
            auctionsVBox.getChildren().add(encherePane);
        }
    }


    @FXML
    private void filterByDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            List<Enchere> filteredEncheres = enchereService.readAll().stream()
                    .filter(enchere -> enchere.getDateDebut().equals(selectedDate))
                    .collect(Collectors.toList());
            updateAuctionsVBox(filteredEncheres);
        } else {
            loadUserAuctions();
        }
    }


    private void updateAuctionsVBox(List<Enchere> enchereList) {
        auctionsVBox.getChildren().clear();
        for (Enchere enchere : enchereList) {
            AnchorPane encherePane = createAuctionPane(enchere);
            auctionsVBox.getChildren().add(encherePane);
        }
    }

    private AnchorPane createAuctionPane(Enchere enchere) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(500);
        anchorPane.setPrefHeight(100);

        Label titleLabel = new Label(enchere.getIdProduit().getType());
        titleLabel.setLayoutX(120);
        titleLabel.setLayoutY(20);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label dateLabel = new Label("Date de début : " + enchere.getDateDebut().toString());
        dateLabel.setLayoutX(120);
        dateLabel.setLayoutY(40);

        Label statusLabel = new Label(enchere.isStatus() ? "Vendu" : "Non vendu");
        statusLabel.setLayoutX(120);
        statusLabel.setLayoutY(60);

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

        Button editButton = new Button();
        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/interfaceEnchere/stylo.png"), 20, 20, true, true)));
        editButton.setOnAction(event -> handleEditAuction(enchere));
        editButton.setLayoutX(anchorPane.getPrefWidth() - 30);
        editButton.setLayoutY(20);

        Button deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/interfaceEnchere/poubelle.png"), 20, 20, true, true)));
        deleteButton.setOnAction(event -> handleDeleteAuction(enchere));
        deleteButton.setLayoutX(anchorPane.getPrefWidth() - 30);
        deleteButton.setLayoutY(60);

        Button detailButton = new Button("Détail");
        detailButton.setOnAction(event -> handleDetailButton(enchere));
        detailButton.setLayoutX(anchorPane.getPrefWidth() - 90);
        detailButton.setLayoutY(60);

        anchorPane.getChildren().addAll(titleLabel, dateLabel, statusLabel, imageView, editButton, deleteButton, detailButton);

        return anchorPane;
    }

    private void handleDetailButton(Enchere enchere) {
        // Ouvrir une nouvelle fenêtre pour afficher les détails de l'enchère
        showAuctionDetails(enchere);
    }

    private void handleEditAuction(Enchere enchere) {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceEnchere/modifypourclient.fxml"));
            Parent root = loader.load();

            // Création de la scène
            Scene scene = new Scene(root);

            // Création de la fenêtre modale
            Stage stage = new Stage();
            stage.setTitle("Modifier Enchère");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // La fenêtre modale bloque les interactions avec les autres fenêtres
            stage.showAndWait(); // Attente de la fermeture de la fenêtre modale avant de continuer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteAuction(Enchere enchere) {
        enchereService.delete(enchere);
        auctionsVBox.getChildren().removeIf(node -> node.getUserData() == enchere);
    }

    private void showAuctionDetails(Enchere enchere) {
        Stage stage = new Stage();
        stage.setTitle("Détails de l'enchère");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // ImageView pour afficher la photo du produit
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300); // Largeur souhaitée
        imageView.setFitHeight(300); // Hauteur souhaitée
        imageView.setPreserveRatio(true); // Conserver les proportions de l'image
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

        Label titleLabel = new Label("Type: " + enchere.getIdProduit().getType());
        Label dateDebutLabel = new Label("Date de début : " + enchere.getDateDebut().toString());
        Label dateFinLabel = new Label("Date de fin : " + enchere.getDateFin().toString());
        Label prixMinLabel = new Label("Prix minimum : " + enchere.getPrixMin());
        Label prixMaxLabel = new Label("Prix maximum : " + enchere.getPrixMax());
        Label nbrParticipantsLabel = new Label("Nombre de participants : " + enchere.getNbrParticipants());

        // Ajouter les éléments à la VBox
        vbox.getChildren().addAll(imageView, titleLabel, dateDebutLabel, dateFinLabel, prixMinLabel, prixMaxLabel, nbrParticipantsLabel);

        Scene scene = new Scene(vbox, 400, 500); // Taille de la scène
        stage.setScene(scene);
        stage.show();
    }
}
