package controllers.ControllerProduits;

import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Produits;
import Services.ServiceProduit.ProduitsService;
import Services.ServiceProduit.RatingService;
import Services.ServiceProduit.ServiceBasket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Callback;

public class TestController {


    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;


    @FXML
    private TextField searchField;

    @FXML
    private Button filterByPriceButton;


    @FXML
    private ChoiceBox<String> filterTypeChoiceBox;
    private final ProduitsService produitsService = new ProduitsService();

    private Membre userId;
    private MediaPlayer mediaPlayer;

    public void setUserId(Membre userId) {
        this.userId = userId;
    }

    @FXML
    private Pagination pagination;

    private static final int ITEMS_PER_PAGE = 4;


    @FXML
    public void initialize() {

        hbox1.setSpacing(10);
        hbox2.setSpacing(10);
        userId = new Membre(); // Initialisation de userId avec un nouvel objet Membre
        userId.setIdUtilisateur(5);
        loadProduitsHbox();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                rechercherProduitParNom(newValue.trim());
            } else {
                loadProduitsHbox(); // Reload all products if the search field is empty
            }
        });
        String musicPath = "src/main/resources/cssProduits/music/once-in-paris-168895.mp3";
        Media media = new Media(new File(musicPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repeat indefinitely
        mediaPlayer.play();

    }

    private void rechercherProduitParNom(String nom) {
        List<Produits> resultatRecherche = produitsService.rechercheParNom(nom);
        afficherProduits(resultatRecherche);
    }

    private void afficherProduits(List<Produits> produits) {
        hbox1.getChildren().clear();
        hbox2.getChildren().clear();

        // Use a flag to alternate between hbox1 and hbox2
        boolean useHbox1 = true;
        for (int i = 0; i < produits.size(); i++) {
            // Create nodes for each product (you may customize this part based on your UI)
            Pane productPane = createAuctionPane(produits.get(i));

            // Add a separator (line) between each product
            if (i < produits.size() - 1) {
                Separator separator = new Separator();
                separator.setOrientation(Orientation.HORIZONTAL);

                hbox1.getChildren().addAll(productPane, separator);
            } else {
                hbox1.getChildren().add(productPane);
            }

            // Switch the flag for the next product
            useHbox1 = !useHbox1;
        }
    }

    private void loadProduitsHbox() {
        List<Produits> produitsList = produitsService.readAll();

        // Sort the produitsList based on some criteria, e.g., by date added
        produitsList.sort(Comparator.comparing(Produits::getIdProduit).reversed());

        // Take only the first 4 products
        List<Produits> recentProducts = produitsList.stream().limit(4).collect(Collectors.toList());

        // Flag to determine which HBox to use
        boolean useHbox1 = true;
        for (Produits produits : produitsList) {
            AnchorPane produitPane = createAuctionPane(produits);

            if (useHbox1) {
                hbox1.getChildren().add(produitPane);
            } else {
                hbox2.getChildren().add(produitPane);
            }

            // Switch the flag for the next product
            useHbox1 = !useHbox1;
        }
    }

    // Création de l'élément de produit pour l'affichage dans le VBox
    private AnchorPane createAuctionPane(Produits produits) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(250);
        anchorPane.setPrefHeight(400);

        Label titleLabel = new Label("labelle:" + produits.getLabelle());
        titleLabel.setLayoutX(300);
        titleLabel.setLayoutY(-5);
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label detailsLabel = new Label("Type: " + produits.getType() +
                "\nPrice: " + produits.getPrix() + "dt" +
                "\nstatus: " + produits.getStatus() +
                "\ndescription: " + produits.getDescription() +
                "\nWarranty: " + produits.getPeriodeGarentie() + " months");
        detailsLabel.setLayoutX(300);
        detailsLabel.setLayoutY(10);

        RatingService ratingService = new RatingService();

        double averageRating = ratingService.getAverageRating(produits.getIdProduit());
        int numberOfVotes = ratingService.getNumberOfVotes(produits.getIdProduit());

        Label averageRatingLabel = new Label("Average Rating: " + averageRating);
        averageRatingLabel.setLayoutX(300);
        averageRatingLabel.setLayoutY(230);

        Label numberOfVotesLabel = new Label("Number of Votes: " + numberOfVotes);
        numberOfVotesLabel.setLayoutX(300);
        numberOfVotesLabel.setLayoutY(270);

        Button detailButton = new Button("Détail");
        detailButton.setOnAction(event -> handleDetailButton(produits));
        detailButton.setLayoutX(anchorPane.getPrefWidth() - 90);
        detailButton.setLayoutY(60);

        Button playVideoButton = new Button("Play Video");
        playVideoButton.setLayoutX(anchorPane.getPrefWidth() - 90);
        playVideoButton.setLayoutY(30);  // Adjust the Y position to make it more visible
        playVideoButton.setStyle("-fx-font-size: 14;");  // Example: Increase font size for better visibility
        playVideoButton.setOnAction(event -> handlePlayVideoButton(produits));
        // Feedback TextField
        TextField feedbackField = new TextField();
        feedbackField.setPromptText("Provide your feedback (1-5)");
        feedbackField.setLayoutX(300);
        feedbackField.setLayoutY(150);
        // Submit Feedback Button
        Button submitFeedbackButton = new Button("Submit Feedback");
        submitFeedbackButton.setLayoutX(300);
        submitFeedbackButton.setLayoutY(190);

        // Add action for the "Submit Feedback" button
        submitFeedbackButton.setOnAction(event -> handleSubmitFeedbackButton(produits, feedbackField));


        Button cartButton = new Button();
        cartButton.setStyle("-fx-background-image: url('/cssProduits/icons/AjoutPanier.png'); " +
                "-fx-background-size: 25 25; " +
                "-fx-background-repeat: no-repeat;");
        cartButton.setPrefSize(30, 30);
        cartButton.setLayoutX(150); // Ajustez la position X en conséquence
        cartButton.setLayoutY(260); // Ajustez la position Y en conséquence

        cartButton.setOnAction(event -> handleAddToCartButton(produits));

        Button enchereButton = new Button("Ajouter ce Produit a l'enchere");
        enchereButton.setOnAction(event -> handleAddToBid(produits));
        enchereButton.setLayoutX(50);
        enchereButton.setLayoutY(300);

        // Associer la méthode handleReserveButton à l'action du bouton


        ImageView imageView = new ImageView();
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);

        try {
            // Charger l'image à partir du chemin spécifié dans Enchere
            String imageName = produits.getPhoto();
            if (imageName != null && !imageName.isEmpty()) {
                String imagePath = "src/main/resources/cssProduits/cars/" + imageName;
                Image image = new Image(new File(imagePath).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(250);
                imageView.setFitHeight(250);
            } else {
                // Utiliser l'image par défaut si le chemin est vide ou null
                Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                imageView.setImage(defaultImage);
            }


        } catch (Exception e) {
            // En cas d'erreur, afficher l'image par défaut
            Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
            imageView.setImage(defaultImage);
        }

        anchorPane.getChildren().addAll(titleLabel, detailsLabel, feedbackField, submitFeedbackButton, cartButton, imageView, averageRatingLabel, numberOfVotesLabel, playVideoButton, detailButton, enchereButton);

        return anchorPane;
    }

    private void handleDetailButton(Produits produits) {
        // Ouvrir une nouvelle fenêtre pour afficher les détails de l'enchère
        showAuctionDetails(produits);
    }

    private void showAuctionDetails(Produits produits) {
        Stage stage = new Stage();
        stage.setTitle("Détails du produit");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // ImageView pour afficher la photo du produit
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150); // Largeur souhaitée
        imageView.setFitHeight(150); // Hauteur souhaitée
        imageView.setPreserveRatio(true); // Conserver les proportions de l'image
        try {
            String imageName = produits.getPhoto();
            if (imageName != null && !imageName.isEmpty()) {
                String imagePath = "src/main/resources/cssProduits/cars/" + imageName;
                Image image = new Image(new File(imagePath).toURI().toString());
                imageView.setImage(image);
            } else {
                // Utiliser une image par défaut si aucune image n'est spécifiée
                Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                imageView.setImage(defaultImage);
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher une image par défaut
            Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
            imageView.setImage(defaultImage);
        }

        Label titleLabel = new Label("Type: " + produits.getType());
        Label descriptionLabel = new Label("Description : " + produits.getDescription());
        Label labelLabel = new Label("Label : " + produits.getLabelle());
        Label prixMaxLabel = new Label("Prix  : " + produits.getPrix());

        // Ajouter les éléments à la VBox
        vbox.getChildren().addAll(imageView, titleLabel, descriptionLabel, labelLabel, prixMaxLabel);

        Scene scene = new Scene(vbox, 400, 500); // Taille de la scène
        stage.setScene(scene);
        stage.show();
    }


    private void handleSubmitFeedbackButton(Produits produits, TextField feedbackField) {
        RatingService ratingService = new RatingService();  // Create an instance of RatingService

        int userIdValue = 0; // Default value or use an appropriate default user ID

        try {
            userIdValue = userId.getIdUtilisateur(); // Assuming userId is the current user's ID

            int feedback = Integer.parseInt(feedbackField.getText().trim());

            // Validate feedback value (should be between 1 and 5)
            if (feedback >= 1 && feedback <= 5) {

                // Check if the user has already submitted feedback for this product
                if (!ratingService.hasUserSubmittedFeedback(userIdValue, produits.getIdProduit())) {
                    ratingService.addRating(userIdValue, produits.getIdProduit(), feedback);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Feedback Submitted");
                    alert.setHeaderText("Thank you for your feedback!");
                    Optional<ButtonType> result = alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Duplicate Feedback");
                    alert.setHeaderText("You have already submitted feedback for this product.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Feedback");
                alert.setHeaderText("Please provide feedback in the range of 1 to 5.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Feedback");
            alert.setHeaderText("Please enter a valid numeric feedback value.");
            alert.showAndWait();
        }
    }

    // Method to handle the "Add to Cart" button click
    private void handleAddToCartButton(Produits produits) {
        ServiceBasket sb = new ServiceBasket();
        if ("not available".equalsIgnoreCase(produits.getStatus())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Not Available");
            alert.setHeaderText("This product is currently not available for purchase.");
            alert.showAndWait();
        } else {
            if (sb.ajouter(userId.getIdUtilisateur(), produits.getIdProduit())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Added to Cart");
                alert.setHeaderText("Product added to your cart.");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Exist");
                alert.setHeaderText("This product already exists in your cart.");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }


    private void handlePlayVideoButton(Produits produits) {
        String videoPath = getVideoPath(produits.getIdProduit());

        if (videoPath != null && !videoPath.isEmpty()) {
            // Use a concrete implementation of Media, for example, File or URL
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            // Create a new stage for the MediaPlayer
            Stage videoStage = new Stage();
            videoStage.setScene(new Scene(new BorderPane(mediaView), 300, 300));
            videoStage.setTitle("Product Video");
            videoStage.show();

            // Play the video
            mediaPlayer.play();
        } else {
            // Handle the case where the video path is not available
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Video Not Found");
            alert.setHeaderText("The video for this product is not available.");
            alert.showAndWait();
        }
    }

    private String getVideoPath(int productId) {
        String videoFileName = productId + ".mp4";
        File videoFile = new File("src/main/resources/cssProduit/vids/" + videoFileName);

        if (videoFile.exists()) {
            return videoFile.toURI().toString();
        } else {
            System.err.println("Video file not found: " + videoFile.getAbsolutePath());
            System.out.println("Absolute Path: " + videoFile.getAbsolutePath());
            return null;
        }
    }



    private void handleAddToBid(Produits produits){
      /*  try {
            // Load the FXML file for the auction interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/AuctionInterface.fxml"));
            Parent root = loader.load();

            // Access the controller for the auction interface
            AuctionController auctionController = loader.getController();

            // Pass the selected product to the auction interface
            auctionController.setProductForAuction(produits);

            // Create a new stage for the auction interface
            Stage auctionStage = new Stage();
            auctionStage.setScene(new Scene(root));
            auctionStage.setTitle("Auction Interface");
            auctionStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    @FXML
    void ImmobilierBtn(ActionEvent event) {

    }
@FXML
   void PanierBtn(MouseEvent event) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass()
                   .getResource("/interfaceProduit/Panier.fxml"));
           Parent root = loader.load();
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);

           stage.showAndWait();
           stage.setTitle("Ajouter Panier");


       } catch (IOException e) {

           System.out.println(e.getMessage());
       }
   }
    @FXML
    void publierAnnonceBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/Ajouterproduits.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            AfficherProduitsController ap=new AfficherProduitsController();
            ap.showProduits();
            stage.setTitle("Ajouter Partenaire");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }

    @FXML
    void vehiculeBtn(ActionEvent event) {

    }

    @FXML
    void handleFilterByPriceButton(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter by Price");
        dialog.setHeaderText("Enter the maximum price:");
        dialog.setContentText("Max Price:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(maxPrice -> {
            try {
                double maxPriceValue = Double.parseDouble(maxPrice.trim());
                filterProductsByPrice(maxPriceValue);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Please enter a valid numeric value for the maximum price.");
                alert.showAndWait();
            }
        });
    }

    private void filterProductsByPrice(double maxPrice) {
        List<Produits> filteredProducts = produitsService.readAll().stream()
                .filter(produit -> produit.getPrix() <= maxPrice)
                .collect(Collectors.toList());

        afficherProduits(filteredProducts);
    }


}