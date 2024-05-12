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
    import javafx.scene.control.TextField;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;

    import java.io.File;
    import java.io.IOException;
    import java.util.List;

    public class ModifierProduitsController  {
        @FXML
        private ChoiceBox<Integer> idUser;

        private String logoFileName;
        @FXML
        private TextField txtdescription;

        @FXML
        private TextField txtlabelle;
        @FXML
        private TextField txtlocalisation;

        @FXML
        private TextField txtperiodeGarentie;

        @FXML
        private TextField txtphoto;

        @FXML
        private TextField txtprix;

        @FXML
        private TextField txtstatus;

        @FXML
        private ComboBox<String> typeComboBox;

        @FXML
        private ImageView imageLogo;
        @FXML
        private ComboBox<String> StatusComboBox2;

    ///////////////

        private int id;
        public ModifierProduitsController() {
            // Initialiser idUser ici si nécessaire
            idUser = new ChoiceBox<>();
        }

        public void initData(int id) {
            this.id=id;

                ProduitsService ps = new ProduitsService();
                Produits produits = ps.fetchProduitById(id);

                typeComboBox.setValue(produits.getType());
                txtdescription.setText(produits.getDescription());
                txtprix.setText(String.valueOf(produits.getPrix()));
                idUser.setValue(produits.getId().getIdUtilisateur());
                txtlabelle.setText(produits.getLabelle());
            txtlocalisation.setText(produits.getLocalisation());
                StatusComboBox2.setValue(produits.getStatus());
                txtperiodeGarentie.setText(String.valueOf(produits.getPeriodeGarentie()));
                populateUserComboBox();


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
     void updateProduits(ActionEvent event) {

         String description = txtdescription.getText();
         String labelle = txtlabelle.getText();
         String localisation = txtlocalisation.getText();

         String type = typeComboBox.getValue();
         String status = StatusComboBox2.getValue();

         float prix = Float.parseFloat(txtprix.getText());
         int periodeGarantie = Integer.parseInt(txtperiodeGarentie.getText());
         Integer selectedUserid = idUser.getValue();

         if (logoFileName == null || logoFileName.isEmpty()) {
             // Handle the case where no file is selected, you may want to show an error message
             System.out.println("Please choose an image.");
             return;
         }

         ProduitsService produitsService = new ProduitsService();
         MembreService ms=new MembreService();
         Membre iduser = ms.readById(selectedUserid);

         Produits pr = new Produits(id,type, description, prix, labelle, status, periodeGarantie,logoFileName,localisation,iduser);
         produitsService.update(pr);

         try {
             // Fermez la fenêtre
             Stage stage = (Stage) txtdescription.getScene().getWindow();
             stage.close();

             // Actualisez la TableView dans le contrôleur principal
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceProduit/AfficherProduits.fxml"));
             Parent root = loader.load();
             AfficherProduitsController afficherProduitsController = loader.getController();/* obtenir une référence à votre contrôleur principal */;
             afficherProduitsController.refreshTableView();
         } catch (Exception e) {
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
                logoFileName = imageName;
            }
        }


        private void setValid(TextField field) {
            field.setStyle("-fx-border-color: green;");
        }


        private void setInvalid(TextField field) {
            field.setStyle("-fx-border-color: red;");
        }


        private void setValid(ChoiceBox<?>  choiceBox) {
            choiceBox.setStyle("-fx-border-color: green;");
        }


        private void setInvalid(ChoiceBox<?> choiceBox) {
            choiceBox.setStyle("-fx-border-color: red;");
        }

        @FXML
        void ViewAllProduct(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/AfficherProduits.fxml"));
            try {
                Parent root = loader.load();
                //je peut recupere la scene actuelle a traveres tous les composant graphiques
                txtdescription.getScene().setRoot(root);
            } catch (IOException e) {

                System.out.println(e.getMessage());
            }


        }







    }
