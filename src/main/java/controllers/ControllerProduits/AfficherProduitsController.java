package controllers.ControllerProduits;

import Entity.entitiesProduits.Produits;
import Services.ServiceProduit.ProduitsService;
import Services.UserAdmineServices.MembreService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherProduitsController  implements Initializable{

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
    private TableColumn<Produits, Integer> colstatus;

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
    private TextField txtstatus;

    @FXML
    private ChoiceBox<String> txttype;
    @FXML
    private TableColumn<Produits, Void> deleteTC;

    @FXML
    private TableColumn<Produits, Void> modifyTC;

    @FXML
    private TableColumn<Produits, String> logo;


    private ProduitsService produitsService = new ProduitsService();
    MembreService ms=new MembreService();
    private final ObservableList<Produits> produits = FXCollections.observableArrayList();

   /* @FXML
    void initialize() {
        try {
            ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsService.readAll());
            table.setItems(observableList);
            colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
            coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
            coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
            colprix.setCellValueFactory(new PropertyValueFactory<Produits, Float>("prix"));
            collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
            colstatus.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("status"));
            colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarantie"));
            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getId();
                String userName = produitsService.getUserById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProduits();
        buttonModifier();
        buttonSupprimer();
        txtrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                rechercherProduitParNom(newValue.trim());
            }else { showProduits();}
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

            // Lier les colonnes à la classe Produits (ajustez les noms des méthodes selon votre modèle)
            colUser.setCellValueFactory(cellData -> {
                int userId = cellData.getValue().getId().getIdUtilisateur();
                String userName = ms.readById(userId).getNomUtilisateur();
                return new SimpleStringProperty(userName);
            });
            // ... (liens des autres colonnes)
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    void addProduits(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/AjouterProduits.fxml"));
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
            txtdescription.setText(produits.getDescription());
            txtlabelle.setText(produits.getLabelle());
            txtstatus.setText(String.valueOf(produits.getStatus()));
            txtprix.setText(String.valueOf(produits.getPrix()));
            txtperiodeGarentie.setText(String.valueOf(produits.getPeriodeGarentie()));
        }
    }
    @FXML
    void ExitToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceUserAdmin/AccueilAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void PrintPDF(ActionEvent event) {
        try {
            // Create a new PDF with a page size of A4
            Document document = new Document(PageSize.A4);

            // Create a new PDF writer and associate it with the document
            String outputFile = "produits.pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));

            // Open the document for writing
            document.open();

            // Create a new table with 8 columns
            PdfPTable table = new PdfPTable(8);

            // Set the table width to 100% of the page width
            table.setWidthPercentage(100);

            // Add column headers
            table.addCell("ID Produit");
            table.addCell("Type");
            table.addCell("Description");
            table.addCell("Prix");
            table.addCell("Labelle");
            table.addCell("Status");
            table.addCell("Periode Garantie");
            table.addCell("User");

            // Add data from the table in your JavaFX application
            for (Produits produit : produits) {
                table.addCell(String.valueOf(produit.getIdProduit()));
                table.addCell(produit.getType());
                table.addCell(produit.getDescription());
                table.addCell(String.valueOf(produit.getPrix()));
                table.addCell(produit.getLabelle());
                table.addCell(String.valueOf(produit.getStatus()));
                table.addCell(String.valueOf(produit.getPeriodeGarentie()));

                // Get the user name
                int userId = produit.getId().getIdUtilisateur();
                String userName = ms.readById(userId).getNomUtilisateur();
                table.addCell(userName);
            }

            // Add the table to the document
            document.add(table);

            // Close the document
            document.close();

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF generated");
            alert.setHeaderText(null);
            alert.setContentText("The PDF has been generated successfully: " + outputFile);
            alert.showAndWait();

        } catch (DocumentException | IOException e) {
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error generating PDF");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while generating the PDF: " + e.getMessage());
            alert.showAndWait();
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

        ProduitsService ps=new ProduitsService();
        ps.delete(event.getIdProduit());
        showProduits();
    }

    @FXML
    void ajouter(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/AjouterProduits.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
            showProduits();
            stage.setTitle("Ajouter Produits");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }



    public void showProduits () {
        ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsService.readAll());
        table.setItems(observableList);
        colidProduit.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("idProduit"));
        coltype.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
        colprix.setCellValueFactory(new PropertyValueFactory<Produits, Float>("prix"));
        collabelle.setCellValueFactory(new PropertyValueFactory<Produits, String>("labelle"));
        colstatus.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("status"));
        colperiodeGarentie.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("periodeGarentie"));
        logo.setCellFactory(column -> new TableCell<Produits, String>() {
            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);
                if (empty || imageName == null) {
                    setGraphic(null);
                } else {
                    ImageView imageView = new ImageView();
                    String imagePath = "src/main/resources/images/imagesPartenaire/" + imageName;
                    Image image = new Image(new File(imagePath).toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(30);
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


            {
                // Initialiser le bouton et l'image une seule fois
                Image image = new Image("cssPartenaire/modifier.png", iconWidth, iconHeight, true, true);
                imageView.setImage(image);
                modifyButton.setGraphic(imageView);

                // Attacher l'événement uniquement lorsque le bouton est cliquable
                modifyButton.setOnAction(event -> {
                    Produits pm = getTableView().getItems().get(getIndex());
                    modifier(pm);
                });
            }

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
    }



}
