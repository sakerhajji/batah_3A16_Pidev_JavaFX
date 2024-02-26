package controllers.ControllerProduits;

import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Produits;
import Services.ServiceProduit.ProduitsService;
import Services.UserAdmineServices.MembreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AjouterProduitsController {

    @FXML
    private ImageView imageLogo;

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
    private ChoiceBox<Integer> idUser;


    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtlabelle;

    @FXML
    private TextField txtperiodeGarentie;
    @FXML
    private TableColumn<?, ?> logo;
    @FXML
    private TextField txtphoto;

    @FXML
    private TextField txtprix;

    @FXML
    private TextField txtstatus;

    @FXML
    private ComboBox<?> typeComboBox;
    private String logoFileName;

    private ProduitsService produitsService = new ProduitsService();
    @FXML
    void initialize() {
        MembreService userService = new MembreService();
        populateUserComboBox();
        addInputRestrictions();

    }
    private void addInputRestrictions() {


      /*  // Contrôle de saisie pour le champ txtstatus (TextField)
        txtstatus.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[01]")) {
                txtstatus.setText(oldValue);
            }
        });

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
        });*/
        txtstatus.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[01]")) {
                txtstatus.setText(oldValue);
                txtstatus.setStyle("-fx-border-color: red;");

            } else {
                txtstatus.setStyle("-fx-border-color: inherit;");
            }
        });

        // Contrôle de saisie pour le champ txtprix (TextField)
        txtprix.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtprix.setText(newValue.replaceAll("[^\\d]", ""));
                txtprix.setStyle("-fx-border-color: red;");
            } else {
                txtprix.setStyle("-fx-border-color: inherit;");
            }
        });

        // Contrôle de saisie pour le champ txtperiodeGarentie (TextField)
        txtperiodeGarentie.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtperiodeGarentie.setText(newValue.replaceAll("[^\\d]", ""));
                txtperiodeGarentie.setStyle("-fx-border-color: red;");
            } else {
                txtperiodeGarentie.setStyle("-fx-border-color: inherit;");
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
        int status = Integer.parseInt(txtstatus.getText());
        String type = (String) typeComboBox.getValue();
        float prix = Float.parseFloat(txtprix.getText());
        int periodeGarantie = Integer.parseInt(txtperiodeGarentie.getText());


        Integer selectedUserid = idUser.getValue();
        if (selectedUserid == null) {
            System.out.println("Error: No user selected");
            return;
        }
MembreService ms=new MembreService();
            Membre selectedUser = ms.readById(selectedUserid);

        Produits newProduit = new Produits( type,description,  prix,labelle, status,periodeGarantie,logoFileName, selectedUser);
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
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/interfaceProduit/AfficherProduits.fxml"));
            try {
                Parent root = loader.load();
                txtdescription.getScene().setRoot(root);
            } catch (IOException e) {

                System.out.println(e.getMessage());
            }


    }
    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File initialDirectory = new File("src/main/resources/images/imagesPartenaire");
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
