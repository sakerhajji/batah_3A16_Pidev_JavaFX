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

public class ModifierReservationEnchereController {

    @FXML
    private ComboBox<Integer> enchereComboBox;

    @FXML
    private ComboBox<Integer> userComboBox;

    @FXML
    private DatePicker reservationDate;

    @FXML
    private CheckBox confirmationCheckBox;

    private ReservationEnchereService reservationEnchereService;
    private ReservationEnchere reservationEnchere;

    public void initialize() {
        reservationEnchereService = new ReservationEnchereService();

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

    public void initData(ReservationEnchere reservation) {
        this.reservationEnchere = reservation;

        // Pré-remplir les champs avec les données de la réservation
        enchereComboBox.setValue(reservation.getIdEnchere().getIdEnchere());
        userComboBox.setValue(reservation.getIdUser().getIdUtilisateur());
        reservationDate.setValue(reservation.getDateReservation());
        confirmationCheckBox.setSelected(reservation.getConfirmation());
    }

    @FXML
    private void enregistrerModification() {
        if (reservationEnchere != null) {
            // Récupérer les données des champs
            Integer enchereSelectionnee = enchereComboBox.getValue();
            Integer membreSelectionne = userComboBox.getValue();
            LocalDate dateReservation = reservationDate.getValue();
            boolean confirmation = confirmationCheckBox.isSelected();

            // Mettre à jour les informations de la réservation
            reservationEnchere.getIdEnchere().setIdEnchere(enchereSelectionnee);
            reservationEnchere.getIdUser().setIdUtilisateur(membreSelectionne);
            reservationEnchere.setDateReservation(dateReservation);
            reservationEnchere.setConfirmation(confirmation);

            // Mettre à jour la réservation via le service
            reservationEnchereService.update(reservationEnchere);

            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", "La réservation a été modifiée avec succès !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La réservation n'a pas pu être modifiée !");
        }
    }

    @FXML
    private void annulerModification() {
        // Fermer la fenêtre de modification
        // Vous pouvez implémenter cette méthode selon vos besoins
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
