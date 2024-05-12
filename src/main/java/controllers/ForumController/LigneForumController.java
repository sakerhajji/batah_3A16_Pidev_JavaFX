package controllers.ForumController;

import Entity.entitiesServiceApresVente.Forum;
import Services.ServiceApresVentS.ForumService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LigneForumController {
    private Forum forum;

    @FXML
    private Label titleLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextArea contentLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private HBox itemC;

    private ForumService forumService = new ForumService(); // Assuming you have a ForumService

    public void setForumData(Forum forum) {
        this.forum = forum;
        if (forum != null) {
            titleLabel.setText(forum.getTitle());
            usernameLabel.setText(forum.getUsername());
            contentLabel.setText(forum.getContent());
            priceLabel.setText(String.valueOf(forum.getPrix()));
        }
    }

    @FXML
    public void detailsClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/updateForum.fxml"));
            Parent root = loader.load();

            UpdateForum controller = loader.getController();
            controller.setForumToUpdate(forum);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Forum");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(this::refreshForumData);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshForumData(WindowEvent event) {
        if (forum != null) {
            setForumData(forum); // Reset the data on the UI components
        }
    }
}
