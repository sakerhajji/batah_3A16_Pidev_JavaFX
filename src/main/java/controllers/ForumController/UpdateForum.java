package controllers.ForumController;

import Entity.entitiesServiceApresVente.Forum;
import Services.ServiceApresVentS.ForumService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateForum {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea contentArea;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField prixField;

    // Inject the ForumService
    private final ForumService forumService = new ForumService();

    private Forum forumToUpdate; // Store the forum to be updated

    // Setter method to set the forum to be updated
    public void setForumToUpdate(Forum forum) {
        this.forumToUpdate = forum;
        // Populate the fields with the current values of the forum
        if (forum != null) {
            titleField.setText(forum.getTitle());
            contentArea.setText(forum.getContent());
            usernameField.setText(forum.getUsername());
            prixField.setText(String.valueOf(forum.getPrix()));
        }
    }

    @FXML
    void updateForum(ActionEvent event) {
        if (forumToUpdate != null) {
            // Update the forum details
            forumToUpdate.setTitle(titleField.getText());
            forumToUpdate.setContent(contentArea.getText());
            forumToUpdate.setUsername(usernameField.getText());
            try {
                double prix = Double.parseDouble(prixField.getText());
                forumToUpdate.setPrix(prix);
                forumService.update(forumToUpdate); // Update the forum
                showConfirmation("Update Successful", "The forum post has been updated successfully.");
                closeStage(event); // Close the window after update
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Price must be a valid number.");
            }
        }
    }

    @FXML
    void deleteForum(ActionEvent event) {
        if (forumToUpdate != null) {
            forumService.delete(forumToUpdate.getId()); // Delete the forum
            showConfirmation("Deletion Successful", "The forum post has been deleted successfully.");
            closeStage(event); // Close the window after deletion
        }
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to show confirmation dialogs
    private void showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to close the stage
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
