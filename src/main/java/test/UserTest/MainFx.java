package test.UserTest;

import Entity.UserAdmin.Membre;
import controllers.UserAdminController.AccueilUserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainFx extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    public static Membre Session =new Membre() ;

    @Override
    public void start(Stage stage) {
        try {                                                                                   //AccueilAdmin
            Parent root = null ;
            System.out.println(Session.isBinFileEmpty());

            if(!Session.isBinFileEmpty())
            {
                Session = Session.convertToMembre(Session.loadJsonFromBinFile()) ;
                if (Session.getRoleUtilisateur()=='A'){
                root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));}
                else {
                    root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));

                }
            }
            else { root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/AccueilAdmin.fxml"));
                Session=Session.convertToMembre(Session.loadJsonFromBinFile()) ;
            }

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
