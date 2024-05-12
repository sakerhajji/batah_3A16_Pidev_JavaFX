package controllers.ControllerProduits;

import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Basket;
import Entity.entitiesProduits.Produits;
import Services.ServiceProduit.ServiceBasket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PanierController {

    @FXML
    private VBox vbox1;

    @FXML
    private TextField discountCodeField;
    List<Produits> produits;
    private static final int TEST_USER_ID = 5;

     // Initialisation de userId avec un nouvel objet Membre


    private ServiceBasket serviceBasket = new ServiceBasket();
    private Membre userId;

    public void setUserId(Membre userId) {
        this.userId = userId;
    }


    @FXML
    public void initialize() {
        userId = new Membre(); // Initialisation de userId avec un nouvel objet Membre
       userId.setIdUtilisateur(5);
        loadUserCart();

    }
        // This method should be called to load and display the user's cart
    public void loadUserCart() {
        // Clear existing content
        vbox1.getChildren().clear();

        // Get the user's cart from the service
        Basket userCart = serviceBasket.get(userId.getIdUtilisateur());

        // Display the cart items in vbox1
        for (Produits product : userCart.getArticles()) {
            vbox1.getChildren().add(createCartItemNode(product));
        }

        // Display the total cost or any other relevant information
        Label totalLabel = new Label("Total Cost: " + userCart.getTotalCost() + "dt");
        vbox1.getChildren().add(totalLabel);
    }

    // You can create a method to create a node for each cart item
    private Node createCartItemNode(Produits product) {
        // Create an HBox to hold the product information and delete button
        HBox itemBox = new HBox();

      /*  // Create an ImageView for the product image
        ImageView productImageView = new ImageView(new Image(product.getPhoto()));
        productImageView.setFitWidth(50); // Adjust the width as needed
        productImageView.setFitHeight(50); // Adjust the height as needed
*/
        // Create a Label to display the product information
        Label itemLabel = new Label(product.getLabelle() + " - " + product.getPrix() + "dt" );

        // Create a Button for deleting the product
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDeleteButtonClick(product));

        // Add the Label and Button to the HBox
        itemBox.getChildren().addAll(itemLabel, deleteButton);

        // Adjust spacing and alignment as needed
        itemBox.setSpacing(40);
        itemBox.setAlignment(Pos.CENTER_LEFT);



        return itemBox;
    }

    private void handleDeleteButtonClick(Produits product) {
        // Handle the delete button click here
        serviceBasket.RemoveFromBasket(userId.getIdUtilisateur(),product.getIdProduit());

        // Reload and display the updated cart
        loadUserCart();
    }




    @FXML
    public void PrintPDF(ActionEvent actionEvent) {
// Get data from your VBox or any other source
        List<Produits> produits = getProduitsFromVBox();

// Create a new PDF document
        PDDocument document = new PDDocument();

        try {
            // Add a page to the document
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a content stream to write on the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Add content to the PDF document
            float yStart = page.getMediaBox().getHeight() - 50;
            float margin = 50;
            float yPosition = yStart;

            for (Produits p : produits) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);

                contentStream.showText("pic: " + p.getPhoto());
                contentStream.showText("Labelle: " + p.getLabelle());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Prix: " + p.getPrix()+"$");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Description: " + p.getDescription());
                contentStream.newLineAtOffset(0, -20);
                // Add more content if needed
                contentStream.newLineAtOffset(0, -20); // Add space between entries
                contentStream.endText();
                yPosition -= 80;  // Adjust the value based on your content and font size
            }

            // Close the content stream
            contentStream.close();

            // Save the document to a file
            File file = new File("Recu_Panier.pdf");
            document.save(file);

            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("PDF file generated successfully!");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the document
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private List<Produits> getProduitsFromVBox() {
        List<Produits> produits = vbox1.getChildren().stream()
                .filter(node -> node instanceof HBox) // Assuming your cart items are represented by HBox
                .map(node -> {
                    Label itemLabel = (Label) ((HBox) node).getChildren().get(0);
                    String[] parts = itemLabel.getText().split(" - ");
                    String labelle = parts[0];
                    // Extract numeric part and convert to float
                    String numericPart = parts[1].replaceAll("[^\\d.]", "");
                    float prix = Float.parseFloat(numericPart);
                    // You may need to adjust this based on your actual UI structure
                    return new Produits(labelle, prix, ""); // Create Produits object with the extracted data
                })
                .collect(Collectors.toList());
        return produits;
    }

    @FXML
    void onCommanderButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/GuiPaiement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            stage.setTitle("Paiement");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

    @FXML
    void applyDiscount(ActionEvent event) {
        String discountCode = discountCodeField.getText(); // Récupérez le code de remise depuis le champ de texte

        // Vous devez implémenter une logique pour vérifier et appliquer la remise en fonction du code

        // Exemple de vérification de remise (vous devez adapter cela à votre cas d'utilisation spécifique)
        if ("BATAH".equals(discountCode)) {
            // Appliquer une remise de 50% (vous pouvez ajuster cela en fonction de vos besoins)
            Basket userCart = serviceBasket.get(userId.getIdUtilisateur());
            double remise = userCart.getTotalCost() * 0.50; // 50% de remise
            userCart.setTotalCost(userCart.getTotalCost() - remise);

            // Actualiser l'affichage du panier
            updateCartDisplay(userCart);

            // Afficher un message de réussite
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Discount Applied");
            alert.setHeaderText(null);
            alert.setContentText("Discount applied successfully!");
            alert.showAndWait();
        } else {
            // Afficher un message d'erreur si le code de remise est invalide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Discount Code");
            alert.setHeaderText(null);
            alert.setContentText("The entered discount code is invalid. Please try again.");
            alert.showAndWait();
        }
    }

    // Mettez à jour cette méthode pour reconstruire votre VBox avec les éléments mis à jour du panier
    private void updateCartDisplay(Basket userCart) {
        // Clear existing content
        vbox1.getChildren().clear();

        // Display the cart items in vbox1
        for (Produits product : userCart.getArticles()) {
            vbox1.getChildren().add(createCartItemNode(product));
        }

        // Display the total cost or any other relevant information
        Label totalLabel = new Label("Total Cost: " + userCart.getTotalCost() + "dt");
        vbox1.getChildren().add(totalLabel);
    }
    @FXML
    void goToReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviceApresVente/ServiceApresVenteAjouter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Reclamation");
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}



