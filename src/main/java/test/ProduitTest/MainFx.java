package test.ProduitTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.image.Image;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }
private  double x,y  ;
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader=new FXMLLoader(getClass()
                .getResource("/interfaceProduit/AfficherProduits.fxml"));
        try {
            Parent root= loader.load();
            Scene scene=new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("Inscription");
            Image logo=new Image("/cssProduits/batahlogo.png");
            primaryStage.getIcons().add(logo);
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {

                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);

            });
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
