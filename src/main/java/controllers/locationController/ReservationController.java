package controllers.locationController;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Entity.UserAdmin.Admin;
import Entity.location.Location;
import Entity.location.Reservation;
import Services.UserAdmineServices.AdminService;
import Services.locationService.LocationService;
import Services.locationService.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;


public class ReservationController {

    public ComboBox userComboBox;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea notesTextArea;

    @FXML
    private Button reserveButton;

    @FXML
    private DatePicker startDatePicker;

    private int locationId;
    private AdminService adminService;

    @FXML
    void handleReservation(ActionEvent event) {
        // Capture the selected user from the ComboBox
        String selectedUser = userComboBox.getSelectionModel().getSelectedItem().toString();

        // Split the selected user string to get the first and last names
        String[] userNames = selectedUser.split(" ");
        String firstName = userNames[0];
        String lastName = userNames[1];

        // Assuming you have a method to fetch the user by first and last names
        // from the AdminService, retrieve the user object
        Admin selectedAdmin = null;
        try {
            selectedAdmin = adminService.getUserByName(firstName, lastName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Capture the selected dates from the DatePicker controls
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        // Fetch the Location object corresponding to the locationId
        LocationService locationService = new LocationService();
        Location location = locationService.readById(locationId);

        // Capture the notes from the TextArea
        String notes = notesTextArea.getText();

        // Create a new Reservation object and set its attributes
        Reservation reservation = new Reservation();
        reservation.setDateDebut(Date.valueOf(startDate));
        reservation.setDateFin(Date.valueOf(endDate));
        reservation.setUtilisateur(selectedAdmin); // Assuming setAdmin is now setUtilisateur
        reservation.setLocation(location); // Assuming setLocationId is now setLocation
        reservation.setNotes(notes);

        // Call the addReservation function from the ReservationService
        ReservationService reservationService = new ReservationService();
        reservationService.addReservation(reservation);
        // Update the availability of the location

        locationService.updateAvailability(location.getIdLocation(), false);
    }





    @FXML
    void initialize(int locationId) {
        this.locationId = locationId;
        System.out.println(locationId);
        adminService = new AdminService();
        populateUserComboBox(adminService);
    }


    private void populateUserComboBox(AdminService userService) {
        // Fetch users from the database
        List<Admin> users = userService.readAll();

        // Create an ObservableList to store user names
        ObservableList<String> userNames = FXCollections.observableArrayList();

        // Populate the ObservableList with user names
        for (Admin user : users) {
            userNames.add(user.getNomUtilisateur() + " " + user.getPrenomUtilisateur());
        }

        // Set the items of the ComboBox to the user names
        userComboBox.setItems(userNames);
    }

}