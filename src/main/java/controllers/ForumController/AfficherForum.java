package controllers.ForumController;

import Entity.entitiesServiceApresVente.Forum;
import Services.ServiceApresVentS.ForumService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

public class AfficherForum {

    @FXML
    private VBox forumListVBox; // This corresponds to the VBox inside the ScrollPane in your FXML

    @FXML
    private Button goToAdd;

    @FXML
    private Pagination pagination; // Pagination control

    private final ForumService forumService = new ForumService();
    private static final int ITEMS_PER_PAGE = 5; // Adjust as needed

    public void initialize() {
        setupPagination();
    }

    private void setupPagination() {
        int totalForums = forumService.getForumCount(); // You need to implement this method
        int pageCount = (totalForums + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private VBox createPage(int pageIndex) {
        List<Forum> forums = forumService.getForums(pageIndex * ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        VBox box = new VBox(10); // Spacing between nodes
        for (Forum forum : forums) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/LigneForum.fxml"));
                Node forumNode = loader.load();
                LigneForumController ligneForumController = loader.getController();
                ligneForumController.setForumData(forum);
                box.getChildren().add(forumNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return box;
    }

    @FXML
    void goToAddOn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/AjouterForum.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add New Forum");
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setOnHidden((WindowEvent e) -> setupPagination()); // Refresh pagination on window close

            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
