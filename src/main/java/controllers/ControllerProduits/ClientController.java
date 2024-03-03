package controllers.ControllerProduits;

import Entity.entitiesProduits.Produits;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private HBox cardLayout;
    private List<Produits> recentlyAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded=new ArrayList<>(recentlyAdedd());
        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/interfaceProduit/Cars.fxml"));


                HBox carsBox = fxmlLoader.load();
                CarsController carsController=fxmlLoader.getController();
                carsController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(carsBox);
            }
        }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    private List<Produits> recentlyAdedd(){
        List<Produits> list=new ArrayList<>();
        Produits produits=new Produits();
        produits.setLabelle("sofienne 106");
        produits.setPhoto("src/main/resources/cssProduits/106.jpg");
        produits.setDescription("hahahhahahah  hahahah");
        list.add(produits);

         produits=new Produits();
        produits.setLabelle("mercedes");
        produits.setPhoto("src/main/resources/cssProduits/mercedes");
        produits.setDescription("zeinnnnnneb");
        list.add(produits);

         produits=new Produits();
        produits.setLabelle("bmw");
        produits.setPhoto("src/main/resources/cssProduits/mercedesGclass");
        produits.setDescription("sakeeeeeeeer");
        list.add(produits);

        return list;
    }


}
























