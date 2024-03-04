
package controllers.ControllerProduits;

import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesProduits.Produits;
import Services.UserAdmineServices.MembreService;
import Services.servicePartenaire.partenaireService;
//import com.itextpdf.text.pdf.PdfPTable;
import controllers.UserAdminController.AccueilAdminController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Services.ServiceProduit.ProduitsService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.sun.javafx.scene.control.skin.Utils.getResource;


public class AfficherProduitsController  implements Initializable {

    @FXML
    private TableColumn<Produits, String> colUser;

    @FXML
    private TableColumn<Produits, String> coldescription;

    @FXML
    private TableColumn<Produits, Integer> colidProduit;

    @FXML
    private TableColumn<Produits, String> collabelle;

    @FXML
    private TableColumn<Produits, Integer> colperiodeGarentie;

    @FXML
    private TableColumn<Produits, String> colphoto;

    @FXML
    private TableColumn<Produits, Float> colprix;

    @FXML
    private TableColumn<Produits, String> colstatus;

    @FXML
    private TableColumn<Produits, String> coltype;

    @FXML
    private TableView<Produits> table;

    @FXML
    private TextField txtrecherche;
    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtlabelle;

    @FXML
    private TextField txtperiodeGarentie;

    @FXML
    private TextField txtphoto;

    @FXML
    private TextField txtprix;


    @FXML
    private ChoiceBox<String> txtstatus;

    @FXML
    private ChoiceBox<String> txttype;
    @FXML
    private TableColumn<Produits, Void> deleteTC;

    @FXML
    private TableColumn<Produits, Void> modifyTC;

    @FXML
    private TableColumn<Produits, Void> detailsTC;
    @FXML
    private Button ajouterButton;

    @FXML
    private ImageView placeholderImageView;


    private ProduitsService produitsService = new ProduitsService();
    MembreService ms = new MembreService();
    private final ObservableList<Produits> produits = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProduits();
        buttonModifier();
        buttonSupprimer();
        buttonDetails();
        txtrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                rechercherProduitParNom(newValue.trim());
            } else {
                showProduits();
            }
        });
    }


    private void rechercherProduitParNom(String nom) {
        List<Produits> resultatRecherche = produitsService.rechercheParNom(nom);
        ObservableList<Produits> listeResultat = FXCollections.observableArrayList(resultatRecherche);
        table.setItems(listeResultat);
    }

    public void refreshTableView() {
        // Mettez à jour la table avec les nouvelles données
        try {
            List<Produits> prodList = produitsService.readAll();
            ObservableList<Produits> prodObservableList = FXCollections.observableArrayList(prodList);
            table.setItems(prodObservableList);


            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getIdUtilisateur();
                String userName = ms.readById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void addProduits(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/Ajouterproduits.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            showProduits();
            stage.setTitle("Ajouter Partenaire");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


    @FXML
    void getData(MouseEvent event) {
        Produits produits = table.getSelectionModel().getSelectedItem();
        if (produits != null) {
            List<String> typesPossibles = Arrays.asList("voiture", "maison");
            txttype.setItems(FXCollections.observableArrayList(typesPossibles));
            txttype.setValue(produits.getType());

            List<String> status = Arrays.asList("Available", "Not Available");
            txtstatus.setItems(FXCollections.observableArrayList(status));
            txtstatus.setValue(produits.getStatus());

            txtdescription.setText(produits.getDescription());
            txtlabelle.setText(produits.getLabelle());
            txtprix.setText(String.valueOf(produits.getPrix()));
            txtperiodeGarentie.setText(String.valueOf(produits.getPeriodeGarentie()));
            // Load and display the image
            String imagePath = "src/main/resources/cssProduits/cars/" + produits.getPhoto();
            Image image = new Image(new File(imagePath).toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(50);
            // Assuming you have a placeholder in your UI to display the image, replace 'placeholderImageView' with the actual ID of the ImageView in your FXML.
            placeholderImageView.setImage(imageView.getImage());
        }
    }


    @FXML
    void PrintPDF(ActionEvent event) {
        // Get data from your tableview or any other source
        List<Produits> produits = table.getItems();

        // Create a new PDF document
        Document document = new Document();

        try {
            // Write PDF content to a file
            try {
                PdfWriter.getInstance(document, new FileOutputStream("Produit.pdf"));
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
            document.open();

            // Add content to the PDF document
            for (Produits p : produits) {
                document.add(new Paragraph("Labelle: " + p.getLabelle()));
                document.add(new Paragraph("prix " + p.getPrix()));
                document.add(new Paragraph("description " + p.getDescription()));
                // Add more content if needed
                document.add(new Paragraph("\n")); // Add space between recettes
            }

            document.close();
            System.out.println("PDF file generated successfully!");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void modifier(Produits event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceProduit/ModifierProduits.fxml"));
            Parent root = loader.load();

            ModifierProduitsController c = loader.getController();

            // Assurez-vous d'appeler populateUserComboBox() avant initData

            c.initData(event.getIdProduit());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Produit");
            stage.showAndWait();
            showProduits();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void supprimer(Produits event) {

        ProduitsService ps = new ProduitsService();
        ps.delete(event.getIdProduit());
        showProduits();
    }


    public void showProduits() {
        ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsService.readAll());
        table.setItems(observableList);
       // colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
        coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
        colprix.setCellValueFactory(new PropertyValueFactory<Produits, Float>("prix"));
        collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
        colstatus.setCellValueFactory(new PropertyValueFactory<Produits, String>("status"));
        colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarentie"));
        colphoto.setCellFactory(column -> new TableCell<Produits, String>() {
            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);
                if (empty || imageName == null) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView();
                    String imagePath = "src/main/resources/cssProduits/cars/" + imageName;
                    Image image = new Image(new File(imagePath).toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);


                }
            }
        });

        colUser.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getId().getIdUtilisateur();
            String userName = ms.readById(userId).getNomUtilisateur();
            return new SimpleStringProperty(userName);
        });

    }

    private void buttonModifier() {
        modifyTC.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Charger l'image avec les dimensions spécifiées
                    Image image = new Image("cssPartenaire/modifier.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    modifyButton.setGraphic(imageView);
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                        Produits pm = getTableView().getItems().get(getIndex());
                        modifier(pm);
                    });
                }
            }
        });
    }

    private void buttonSupprimer() {
        deleteTC.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Charger l'image avec les dimensions spécifiées
                    Image image = new Image("cssPartenaire/supprimer.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    deleteButton.setGraphic(imageView);
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Produits pm = getTableView().getItems().get(getIndex());
                        supprimer(pm);
                    });
                }
            }
        });
    }private void buttonDetails() {
        detailsTC.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24; // Largeur de l'icône
            private final double iconHeight = 24; // Hauteur de l'icône

            Stage stage = new Stage();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    // Charger l'image avec les dimensions spécifiées
                    Image image = new Image("cssPartenaire/modifier.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    detailsButton.setGraphic(imageView);
                    setGraphic(detailsButton);
                    detailsButton.setOnAction(event -> {
                        Produits pm = getTableView().getItems().get(getIndex());
                        showDetailsPopup(pm);
                    });
                }
            }

            private void showDetailsPopup(Produits produits) {
                stage.setTitle("Détails du produit");

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));

                // ImageView pour afficher la photo du produit
                ImageView productImageView = new ImageView();
                productImageView.setFitWidth(150); // Largeur souhaitée
                productImageView.setFitHeight(150); // Hauteur souhaitée
                productImageView.setPreserveRatio(true); // Conserver les proportions de l'image

                try {
                    String imageName = produits.getPhoto();
                    if (imageName != null && !imageName.isEmpty()) {
                        String imagePath = "src/main/resources/cssProduits/cars/" + imageName;
                        Image productImage = new Image(new File(imagePath).toURI().toString());
                        productImageView.setImage(productImage);
                    } else {
                        // Utiliser une image par défaut si aucune image n'est spécifiée
                        Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                        productImageView.setImage(defaultImage);
                    }
                } catch (Exception e) {
                    // En cas d'erreur, afficher une image par défaut
                    Image defaultImage = new Image(getClass().getResourceAsStream("/interfaceEnchere/Capture.png"));
                    productImageView.setImage(defaultImage);
                }

                Label titleLabel = new Label("Type: " + produits.getType());
                Label descriptionLabel = new Label("Description : " + produits.getDescription());
                Label labelLabel = new Label("Label : " + produits.getLabelle());
                Label prixMaxLabel = new Label("Prix  : " + produits.getPrix());

                // Ajouter les éléments à la VBox
                vbox.getChildren().addAll(productImageView, titleLabel, descriptionLabel, labelLabel, prixMaxLabel);

                Scene scene = new Scene(vbox, 400, 500); // Taille de la scène
                stage.setScene(scene);
                stage.show();
            }
        });
    }
 @FXML
    void Clientbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/Client2.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            showProduits();
            stage.setTitle("Ajouter Produit");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }
}
