package controllers.controllerPartenaire;

import Entity.entitiesPartenaire.partenaire;
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

public class modifier_personneController {

    @FXML
    private TextField adressee;

    @FXML
    private TextField nom;

    @FXML
    private TextField tel;

    @FXML
    private TextField type;

    @FXML
    private TextField email;

    @FXML
    private ImageView imageLogo;

    @FXML
    private ComboBox<String> typeComboBox;

    private int id;
    private String logoFileName;

    public void initData(int id) {
        this.id = id;

        partenaireService ps = new partenaireService();
        partenaire personne = ps.readById(id);

        adressee.setText(personne.getAdresse());
        nom.setText(personne.getNom());
        tel.setText(String.valueOf(personne.getTel()));
        typeComboBox.setValue(personne.getType());
        email.setText(personne.getEmail());

        // Charger l'image
        if (personne.getLogo() != null && !personne.getLogo().isEmpty()) {
            String imagePath = "E:/fac/3eme/java/batah_3A16_Pidev_JavaFX/src/main/resources/images/imagesPartenaire/" + personne.getLogo();
            Image image = new Image(new File(imagePath).toURI().toString());
            imageLogo.setImage(image);
        }
    }


    @FXML
    void modifier(ActionEvent event) {
        partenaireService ps = new partenaireService();
        String ad = adressee.getText();
        String nm = nom.getText();
        String telText = tel.getText();
        String tp = typeComboBox.getValue();
        String eml = email.getText();

        boolean isValid = true;
        if (nm.isEmpty()) {
            setInvalid(nom);
            isValid = false;
        } else {
            setValid(nom);
        }

        if (ad.isEmpty()) {
            setInvalid(adressee);
            isValid = false;
        } else {
            setValid(adressee);
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
            partenaire pr = new partenaire(id, nm, tp, ad, tl, eml, logoFileName);
            ps.update(pr);

            try {
                Stage stage = (Stage) adressee.getScene().getWindow();
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
        File initialDirectory = new File("E:/fac/3eme/java/batah_3A16_Pidev_JavaFX/src/main/resources/images/imagesPartenaire");
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
