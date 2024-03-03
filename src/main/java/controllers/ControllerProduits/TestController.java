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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestController {



    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;


    @FXML
    private TextField searchField;


    private final ProduitsService produitsService = new ProduitsService();

    private Membre userId;

    public void setUserId(Membre userId) {
        this.userId = userId;
    }


    @FXML
    public void initialize() {
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
        for (Produits produit : produits) {
            // Create nodes for each product (you may customize this part based on your UI)
            Pane productPane = createAuctionPane(produit);
            if (useHbox1) {
                hbox1.getChildren().add(productPane);
            } else {
                hbox2.getChildren().add(productPane);
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
        anchorPane.setPrefWidth(500);
        anchorPane.setPrefHeight(500);

        Label titleLabel = new Label("labelle:" +produits.getLabelle());
        titleLabel.setLayoutX(300);
        titleLabel.setLayoutY(10);
        titleLabel.setStyle("-fx-font-weight: bold;");
        Label detailsLabel = new Label("Type: " + produits.getType() +
                "\nPrice: " + produits.getPrix() + "dt"+
                "\nstatus: " + produits.getStatus() +
                "\ndescription: " + produits.getDescription() +
                "\nWarranty: " + produits.getPeriodeGarentie() + " months");
        detailsLabel.setLayoutX(300);
        detailsLabel.setLayoutY(40);

        RatingService ratingService = new RatingService();

        double averageRating = ratingService.getAverageRating(produits.getIdProduit());
        int numberOfVotes = ratingService.getNumberOfVotes(produits.getIdProduit());

        Label averageRatingLabel = new Label("Average Rating: " + averageRating);
        averageRatingLabel.setLayoutX(300);
        averageRatingLabel.setLayoutY(270);

        Label numberOfVotesLabel = new Label("Number of Votes: " + numberOfVotes);
        numberOfVotesLabel.setLayoutX(300);
        numberOfVotesLabel.setLayoutY(300);


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


        Button cartButton = new Button("add to cart");
        cartButton.setLayoutX(300);
        cartButton.setLayoutY(230);
        // Add action for the "Add to Cart" button
        cartButton.setOnAction(event -> handleAddToCartButton(produits));

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

        anchorPane.getChildren().addAll(titleLabel, detailsLabel, feedbackField, submitFeedbackButton,cartButton, imageView,averageRatingLabel, numberOfVotesLabel);

        return anchorPane;
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




}