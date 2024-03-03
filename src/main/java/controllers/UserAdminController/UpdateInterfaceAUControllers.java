package controllers.UserAdminController;

import Entity.ControleDeSaisieClass.ControleDeSaisieClass;
import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.ResourceBundle;


public class UpdateInterfaceAUControllers implements Initializable {

    private ControleDeSaisieClass controle= new ControleDeSaisieClass() ;
    private String img=null;

    private Admin admin ;
    @FXML
    private TextField nomUtilisateur;
    @FXML
    private TextField prenomUtilisateur;
    @FXML
    private TextField adresseEmail;
    @FXML
    private DatePicker dateDeNaissance;
    @FXML
    private TextField numeroCin;
    @FXML
    private TextField numeroTelephone;
    @FXML
    private Circle profile;

    @FXML
    private Text IdUtlisateur;



    @FXML
    void MisAJourClicked(ActionEvent event) {

        if(controle.checkText(nomUtilisateur.getText()) &&
                controle.checkText(prenomUtilisateur.getText())&&
                controle.isValidEmail(adresseEmail.getText())&&
                controle.chekNumero(numeroTelephone.getText())&&
                controle.chekNumero(numeroCin.getText())&&
                controle.isDateValidAndOver18(dateDeNaissance.getValue())
        ) {
            LocalDate localDate = dateDeNaissance.getValue();
            Date date = java.sql.Date.valueOf(localDate);
            AdminService adminService = new AdminService();
            admin.setNomUtilisateur(nomUtilisateur.getText());
            admin.setPrenomUtilisateur(prenomUtilisateur.getText());
            admin.setMailUtilisateur(adresseEmail.getText());
            admin.setNumUtilisateur(numeroTelephone.getText());
            admin.setDateDeNaissance(date);
            admin.setCinUtilisateur(numeroCin.getText());
            admin.setAvatar(img);
            System.out.println(admin);
            adminService.updateCard(admin);
            System.out.println("done");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));
            //AccueilAdminController c = loader.getController();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Formulaire d'inscription invalide");
            alert.setContentText("Veuillez vérifier vos informations et assurez-vous de remplir tous les champs correctement.");
            alert.showAndWait();
        }




    }
    private   String getFileName(String filePath) {

        int lastIndex = filePath.lastIndexOf('/') != -1 ? filePath.lastIndexOf('/') : filePath.lastIndexOf('\\');

        if (lastIndex == -1) {
            return filePath;
        }

        return filePath.substring(lastIndex + 1);
    }

    @FXML
    void UpdateImg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File initialDirectory = new File("src/main/resources/images");
        fileChooser.setInitialDirectory(initialDirectory);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String imageName = selectedFile.getName();
            setProfile(selectedFile.toURI().toString());
            Image image = new Image(selectedFile.toURI().toString());
            profile.setFill(new ImagePattern(image));
            img=selectedFile.toURI().toString();
            img=getFileName(img);
            System.out.println(img);
        }

    }
    @FXML
    void fermerClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomUtilisateur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.checkText(newValue))
                nomUtilisateur.setStyle("-fx-border-color: transparent");
            else
                nomUtilisateur.setStyle("-fx-border-color: red");
        });
        prenomUtilisateur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.checkText(newValue))
                prenomUtilisateur.setStyle("-fx-border-color: transparent");
            else
                prenomUtilisateur.setStyle("-fx-border-color: red");
        });
        adresseEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.isValidEmail(newValue))
                adresseEmail.setStyle("-fx-border-color: transparent");
            else
                adresseEmail.setStyle("-fx-border-color: red");
        });
        numeroTelephone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.chekNumero(newValue))
                numeroTelephone.setStyle("-fx-border-color: transparent");
            else
                numeroTelephone.setStyle("-fx-border-color: red");
        });
        numeroCin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (controle.chekNumero(newValue))
                numeroCin.setStyle("-fx-border-color: transparent");
            else
                numeroCin.setStyle("-fx-border-color: red");
        });
        dateDeNaissance.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (controle.isDateValidAndOver18(newValue))
                dateDeNaissance.setStyle("-fx-border-color: transparent");
            else
                dateDeNaissance.setStyle("-fx-border-color: red");
        });

    }

    public TextField getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail.setText(adresseEmail);
    }

    public DatePicker getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {

        this.dateDeNaissance.setValue(dateDeNaissance.toLocalDate());
    }

    public TextField getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur.setText(nomUtilisateur);
    }

    public TextField getNumeroCin() {
        return numeroCin;
    }

    public void setNumeroCin(String numeroCin) {
        this.numeroCin.setText(numeroCin);
    }

    public TextField getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone.setText(numeroTelephone);
    }

    public TextField getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur.setText(prenomUtilisateur);
    }
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Circle getProfile() {
        return profile;
    }

    public Text getIdUtlisateur() {
        return IdUtlisateur;
    }

    public void setIdUtlisateur(String idUtlisateur) {
        this.IdUtlisateur.setText(idUtlisateur);
    }

    private static boolean search(File directory, String pictureName) {

        File[] files = directory.listFiles();

        if (files != null) {

            for (File file : files) {
                if (file.isDirectory()) {

                    if (search(file, pictureName)) {
                        return true;
                    }
                } else if (file.getName().equalsIgnoreCase(pictureName)) {

                    System.out.println("Picture found: " + file.getAbsolutePath());
                    return true;
                }
            }
        }


        return false;
    }
    public static boolean searchPicture(String directoryPath, String pictureName) {
        File directory = new File(directoryPath);

        // Check if the directory exists
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist.");
            return false;
        }

        return search(directory, pictureName);
    }
    public void setProfile(String imagePath) {
        String directoryPath = "C:/Users/saker/Desktop/esprit/3eme/Pidev/batah_3A16_Pidev_JavaFX/src/main/resources/images";
        String pictureName = imagePath;
        boolean pictureExists = searchPicture(directoryPath, pictureName);
        System.out.println(pictureExists);
        if (pictureExists ) {
            System.out.println("donne");
            Image image = new Image("/images/"+imagePath);
            this.profile.setFill(new ImagePattern(image));
        }
    }


    @FXML
    void SupprimerClicked(ActionEvent event) {
        AdminService adminService =new AdminService() ;
        admin.setIdUtilisateur( Integer.parseInt(IdUtlisateur.getText()));
        adminService.delete(admin);
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }


}

