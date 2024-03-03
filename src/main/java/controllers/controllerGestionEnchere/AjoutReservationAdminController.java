package controllers.controllerGestionEnchere;



import Entity.UserAdmin.Membre;
import Entity.entitiesEncheres.Enchere;
import Entity.entitiesEncheres.ReservationEnchere;
import Services.EnchereService.EnchereService;
import Services.EnchereService.ReservationEnchereService;
import Services.UserAdmineServices.MembreService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.List;

public class AjoutReservationAdminController {

    @FXML
    private ComboBox<Integer> enchereComboBox;

    @FXML
    private ComboBox<Integer> userComboBox;

    @FXML
    private DatePicker reservationDate;

    @FXML
    private CheckBox confirmationCheckBox;

    private final ReservationEnchereService reservationEnchereService;

    public AjoutReservationAdminController() {
        this.reservationEnchereService = new ReservationEnchereService();
    }

    @FXML
    private void initialize() {
        // Initialisez les ComboBox avec les ID des enchères
        EnchereService enchereService = new EnchereService();
        List<Enchere> encheres = enchereService.readAll();
        for (Enchere enchere : encheres) {
            enchereComboBox.getItems().add(enchere.getIdEnchere());
        }

        // Initialisez les ComboBox avec les ID des membres
        MembreService membreService = new MembreService();
        List<Membre> membres = membreService.readAll();
        for (Membre membre : membres) {
            userComboBox.getItems().add(membre.getIdUtilisateur());
        }
    }

    @FXML
    private void ajouterReservation() {
        Integer enchereId = enchereComboBox.getValue();
        Integer userId = userComboBox.getValue();
        LocalDate date = reservationDate.getValue();
        boolean confirmation = confirmationCheckBox.isSelected();

        if (enchereId != null && userId != null && date != null) {
            // Créez une nouvelle réservation avec les données sélectionnées
            Enchere enchere = new Enchere();
            enchere.setIdEnchere(enchereId);
            Membre membre = new Membre();
            membre.setIdUtilisateur(userId);
            ReservationEnchere reservationEnchere = new ReservationEnchere(enchere, membre, date, confirmation);

            // Ajoutez la réservation via le service
            reservationEnchereService.add(reservationEnchere);

            // Affichez un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La réservation a été ajoutée avec succès !");
            alert.showAndWait();

            // Vous pouvez ajouter d'autres traitements ou mises à jour de l'interface utilisateur ici

        } else {
            // Affichez un message d'erreur si tous les champs ne sont pas sélectionnés
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner toutes les informations nécessaires !");
            alert.showAndWait();
        }
    }

//    public void redirectToReservationList() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\BAZ INFO\\Desktop\\batah1\\src\\main\\resources\\interfaceEnchere.fxml"));
//            Parent root = loader.load();
//            ListeReservationEnchereController controller = loader.getController();
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//            // Fermer la fenêtre actuelle si nécessaire
//            Stage currentStage = (Stage) enchereComboBox.getScene().getWindow();
//            currentStage.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



}
