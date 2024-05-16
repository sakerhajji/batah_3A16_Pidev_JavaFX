package controllers.controllerPartenaire;

import Entity.entitiesPartenaire.Partenaire;
import Services.servicePartenaire.partenaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Ajouter_PartenaireController {

    @FXML
    private TextField adresse;

    @FXML
    private TextField nom;

    @FXML
    private TextField tel;

    @FXML
    private TextField email;

    @FXML
    private ImageView imageLogo;

    @FXML
    private ComboBox<?> typeComboBox;

    private String logoFileName;

    @FXML
    void addPerson(ActionEvent event) {
        String ad = adresse.getText();
        String nm = nom.getText();
        String tp = (String) typeComboBox.getValue();
        String eml = email.getText();
        String telText = tel.getText();

        boolean isValid = true;
        if (nm.isEmpty()) {
            setInvalid(nom);
            isValid = false;
        } else {
            setValid(nom);
        }

        if (ad.isEmpty()) {
            setInvalid(adresse);
            isValid = false;
        } else {
            setValid(adresse);
        }

        if (tp == null) {
            setInvalid(typeComboBox);
            isValid = false;
        } else {
            setValid(typeComboBox);
        }

        if (eml.isEmpty() || !eml.contains("@")) {
            setInvalid(email);
            isValid = false;
        } else {
            setValid(email);
        }

        if (!telText.matches("\\d{8}")) {
            setInvalid(tel);
            isValid = false;
        } else {
            setValid(tel);
        }

        if (isValid) {
            int tl = Integer.parseInt(telText);
            Partenaire p = new Partenaire(nm, tp, ad, tl, eml, logoFileName);
            partenaireService ps = new partenaireService();
            ps.add(p);

            try {
                Stage stage = (Stage) adresse.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File initialDirectory = new File("E:\\fac\\3eme\\web\\BatahApp_Symfony_3A16\\public\\image\\uploads\\partenaires\\");
        fileChooser.setInitialDirectory(initialDirectory);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String imageName = selectedFile.getName();
            Image image = new Image(selectedFile.toURI().toString());
            imageLogo.setImage(image);
            logoFileName = imageName;
        }
    }


    private void setValid(TextField field) {
        field.setStyle("-fx-border-color: green;");
    }

    private void setInvalid(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }

    private void setValid(ComboBox<?> comboBox) {
        comboBox.setStyle("-fx-border-color: green;");
    }

    private void setInvalid(ComboBox<?> comboBox) {
        comboBox.setStyle("-fx-border-color: red;");
    }
}
