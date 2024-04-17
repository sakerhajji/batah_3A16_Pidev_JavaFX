package controllers.ControllerProduits;

import Entity.entitiesProduits.Produits;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private GridPane carContainer;
    @FXML
    private HBox cardLayout;
    private List<Produits> recentlyAdded;
    private List<Produits> recommended;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*recentlyAdded = new ArrayList<>(recentlyAdedd());
        recommended = new ArrayList<>(produits());
        cardLayout.getChildren().clear();
        try {
            // Ajoutez les produits récemment ajoutés dans la HBox
            for (Produits value : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/interfaceProduit/Cars.fxml"));
                HBox carsBox = fxmlLoader.load();
                CarsController carsController = fxmlLoader.getController();
                carsController.setData(value);
                cardLayout.getChildren().add(carsBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }

            // Afficher les produits recommandés dans le GridPane
            int column = 0;
            int row = 1;
            for (Produits produits : recommended) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/interfaceProduit/Produits.fxml"));
                VBox prodbox = fxmlLoader.load();
                ProduitsController produitsController = fxmlLoader.getController();
                produitsController.setData(produits);

                if (column == 6) {
                    column = 0;
                    ++row;
                }
                carContainer.add(prodbox, column++, row);
                GridPane.setMargin(prodbox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }*/
    }


    /*private List<Produits> recentlyAdedd(){
        List<Produits> list=new ArrayList<>();
        list.add(createProduit("sofienne 106", "src/main/resources/cssProduits/106.jpg", "hahahhahahah  hahahah"));
        list.add(createProduit("mercedes", "src/main/resources/cssProduits/mercedes", "zeinnnnnneb"));
        list.add(createProduit("bmw", "src/main/resources/cssProduits/mercedesGclass", "sakeeeeeeeer"));
        return list;
    }

    private List<Produits> produits(){
        List<Produits> list=new ArrayList<>();
        list.add(createProduit("sofienne 106", "src/main/resources/cssProduits/106.jpg", "hahahhahahah  hahahah"));
        list.add(createProduit("mercedes", "src/main/resources/cssProduits/mercedes", "zeinnnnnneb"));
        list.add(createProduit("bmw", "src/main/resources/cssProduits/mercedesGclass", "sakeeeeeeeer"));
        return list;
    }

    private Produits createProduit(String labelle, String photo, String description) {
        return new Produits(labelle, photo, description);
    }*/
}
























