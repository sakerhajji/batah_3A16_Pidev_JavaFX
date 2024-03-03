package controllers.UserAdminController;

import Entity.UserAdmin.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LigneController implements Initializable {
    private Admin admin ;

    @FXML
    private Circle Profile;

    Image image ;
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @FXML
    private Label DateNaissance;

    @FXML
    private Label Email;

    @FXML
    private Label Nom;

    @FXML
    private Label Prenom;

    @FXML
    private HBox itemC;
    private double xOffset , yOffset;

    public Label getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        DateNaissance.setText(dateNaissance);
    }

    public Label getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email.setText(email);
    }

    public Label getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom.setText(nom);
    }

    public Label getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom.setText(prenom);
    }

    public HBox getItemC() {
        return itemC;
    }

    public void setItemC(HBox itemC) {
        this.itemC = itemC;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    void DetailleClick(ActionEvent event) throws IOException {
        System.out.println(admin);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceUserAdmin/UpdateInterfaceAU.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            UpdateInterfaceAUControllers c = loader.getController();

            // Check if admin is not null before setting it
            if (admin != null) {
                c.setAdmin(admin);

                // Check if admin's nomUtilisateur is not null before setting it
                if (admin.getNomUtilisateur() != null) {
                    c.setNomUtilisateur(admin.getNomUtilisateur());
                }

                // Check if admin's prenomUtilisateur is not null before setting it
                if (admin.getPrenomUtilisateur() != null) {
                    c.getPrenomUtilisateur().setText(admin.getPrenomUtilisateur());
                }

                // Check if admin's mailUtilisateur is not null before setting it
                if (admin.getMailUtilisateur() != null) {
                    c.setAdresseEmail(admin.getMailUtilisateur());
                }

                // Check if admin's dateDeNaissance is not null before setting it
                if (admin.getDateDeNaissance() != null) {
                    c.setDateDeNaissance(admin.getDateDeNaissance());
                }

                // Check if admin's numUtilisateur is null before setting it
                if(admin.getNumUtilisateur()==null){
                    c.setNumeroTelephone("svp taper votre numero");
                } else {
                    c.setNumeroTelephone(admin.getNumUtilisateur());
                }

                // Check if admin's cinUtilisateur is null before setting it
                if(admin.getCinUtilisateur()==null){
                    c.setNumeroCin("svp taper votre numero cin");
                } else {
                    c.setNumeroCin(admin.getCinUtilisateur());
                }

                // Check if admin's avatar is not null before setting it
                if(admin.getAvatar()!=null) {
                    String imagePath = admin.getAvatar();
                    c.setProfile(imagePath);
                }

                c.setIdUtlisateur(String.valueOf(admin.getIdUtilisateur()));
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            root.setOnMousePressed((MouseEvent events) -> {
                xOffset = events.getSceneX();
                yOffset = events.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent events) -> {
                stage.setX(events.getScreenX() - xOffset);
                stage.setY(events.getScreenY() - yOffset);
            });
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            return false;
        }

        return search(directory, pictureName);
    }
    public void setProfile(String imagePath) {
        String directoryPath = "C:\\Users\\saker\\Desktop\\esprit\\3eme\\Pidev\\batah_3A16_Pidev_JavaFX\\src\\main\\resources\\images";
        String pictureName = imagePath;
        boolean pictureExists = searchPicture(directoryPath, pictureName);

        if (pictureExists ) {
            Image image = new Image("/images/"+imagePath);
            this.Profile.setFill(new ImagePattern(image));
        }
    }


}
