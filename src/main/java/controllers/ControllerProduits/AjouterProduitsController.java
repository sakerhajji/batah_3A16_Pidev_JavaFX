package controllers.ControllerProduits;

import Entity.entitiesProduits.Produits;
import Entity.UserAdmin.Membre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import Services.ServiceProduit.ProduitsService;
import Services.UserAdmineServices.MembreService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterProduitsController {

    @FXML
    private ImageView imageLogo;
    @FXML
    private TableColumn<Produits, String> colphoto;
    @FXML
    private TableColumn<?, ?> logo;
    private String logoFileName;

    @FXML
    private TextField txtphoto;
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

   /* @FXML
    private TableColumn<Produits, String> colphoto;*/

    @FXML
    private TableColumn<Produits, Float> colprix;

    @FXML
    private TableColumn<Produits, Integer> colstatus;

    @FXML
    private TableColumn<Produits, String> coltype;
    @FXML
    private TableColumn<Produits, String> collocalisation;

    @FXML
    private ChoiceBox<Integer> idUser;


    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtlocalisation;
    @FXML
    private TextField txtlabelle;

    @FXML
    private TextField txtperiodeGarentie;

   /* @FXML
    private TextField txtphoto;*/

    @FXML
    private TextField txtprix;

    @FXML
    private TextField txtstatus;

    @FXML
    private ComboBox<?> typeComboBox;
    @FXML
    private ComboBox<String> StatusComboBox2;

    private ProduitsService produitsService = new ProduitsService();

    @FXML
    void initialize() {
        MembreService userService = new MembreService();
        populateUserComboBox();
        addInputRestrictions();

    }

    private void addInputRestrictions() {


        // Contrôle de saisie pour le champ txtstatus (TextField)
        /*txtstatus.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[01]")) {
                txtstatus.setText(oldValue);
            }
        });*/

        // Contrôle de saisie pour le champ txtprix (TextField)
        txtprix.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtprix.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Contrôle de saisie pour le champ txtperiodeGarentie (TextField)
        txtperiodeGarentie.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtperiodeGarentie.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void populateUserComboBox() {

        MembreService u = new MembreService();
        List<Membre> users = null;

        users = u.readAll();

        ObservableList<Integer> userIds = FXCollections.observableArrayList();

        for (Membre user : users) {
            userIds.add(user.getIdUtilisateur());
        }

        idUser.setItems(userIds);
    }


    @FXML
    void addProduits(ActionEvent event) {
        String description = txtdescription.getText();
        String labelle = txtlabelle.getText();
        String type = (String) typeComboBox.getValue();
        String status = (String) StatusComboBox2.getValue();
        float prix = Float.parseFloat(txtprix.getText());
        int periodeGarantie = Integer.parseInt(txtperiodeGarentie.getText());
        String localisation = txtlocalisation.getText();


        Integer selectedUserid = idUser.getValue();
        if (selectedUserid == null) {
            System.out.println("Error: No user selected");
            return;
        }
        MembreService ms = new MembreService();
        Membre selectedUser = ms.readById(selectedUserid);

        Produits newProduit = new Produits(type, description, prix, labelle, status, periodeGarantie, logoFileName,localisation, selectedUser);
        produitsService.add(newProduit);
        try {
            Stage stage = (Stage) txtdescription.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void ViewAllProduct(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/AfficherProduits.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.showAndWait();
           // showProduits();
            stage.setTitle("Ajouter Partenaire");


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }
        @FXML
        void chooseImage(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            File initialDirectory = new File("src/main/resources/cssProduits/cars/");
            fileChooser.setInitialDirectory(initialDirectory);
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                String imageName = selectedFile.getName();
                Image image = new Image(selectedFile.toURI().toString());
                imageLogo.setImage(image);
                // Enregistrer le nom de l'image
                logoFileName = imageName;
            }
        }

    }

