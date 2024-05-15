package controllers.ServiceApresVente;

import Entity.entitiesServiceApresVente.Forum;
import Services.ServiceApresVentS.ForumService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.time.LocalDate;
import javafx.stage.Stage;

public class AjouterForum {

    @FXML
    private Label addMessage;

    @FXML
    private Button btnAddForum;

    @FXML
    private TextArea contentEntry;

    @FXML
    private Label contentError;

    @FXML
    private TextField titleEntry;

    @FXML
    private Label titleError;

    @FXML
    private TextField usernameEntry;

    @FXML
    private Label usernameError;

    @FXML
    private TextField prixEntry;

    @FXML
    private Label prixError;

    @FXML
    private Button goBack;

    @FXML
    void addForum(ActionEvent event) {
        System.out.println("addForum method called");  // Debug print

        String title = titleEntry.getText();
        String content = contentEntry.getText();
        String username = usernameEntry.getText();
        String prixText = prixEntry.getText();
        double prix = 0;

        // Resetting error messages
        titleError.setText("");
        contentError.setText("");
        usernameError.setText("");
        prixError.setText("");
        addMessage.setText("");

        boolean hasError = false;
        StringBuilder errorMessages = new StringBuilder();

        if (title.isEmpty()) {
            titleError.setText("Title is required.");
            hasError = true;
        }

        if (content.isEmpty()) {
            contentError.setText("Content is required.");
            hasError = true;
        }

        if (username.isEmpty()) {
            usernameError.setText("Username is required.");
            hasError = true;
        }

        try {
            prix = Double.parseDouble(prixText);
        } catch (NumberFormatException e) {
            prixError.setText("Invalid price format.");
            hasError = true;
        }

        if (hasError) {
            return; // Stop execution if there are any errors
        }

        // Create forum object and add to database
        Forum forum = new Forum();
        forum.setTitle(title);
        forum.setContent(content);
        forum.setUsername(username);
        forum.setPrix(prix);
        forum.setCreatedAt(LocalDate.now());

        ForumService forumService = new ForumService();
        forumService.create(forum);

        // Display success message in green
        addMessage.setText("Forum added successfully");
        addMessage.setTextFill(javafx.scene.paint.Color.GREEN);

        // Clear input fields after successful addition
        titleEntry.clear();
        contentEntry.clear();
        usernameEntry.clear();
        prixEntry.clear();
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/AfficherForum.fxml"));
            Parent root = loader.load();

            // Get the stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("View Forums");

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
