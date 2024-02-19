package controller;

import entities.partenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.partenaireService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Afficher_personneController implements Initializable {

    @FXML
    private TableView<partenaire> table;

    @FXML
    private TableColumn<partenaire, Integer> colid;

    @FXML
    private TableColumn<partenaire, String> colnom;

    @FXML
    private TableColumn<partenaire, String> coltype;

    @FXML
    private TableColumn<partenaire, String> coladresse;

    @FXML
    private TableColumn<partenaire, Integer> coltel;

    @FXML
    private TableColumn<partenaire, String> colemail;

    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private Button ajouter;
    private int id=0;
    @FXML
    void getData(MouseEvent event) {
        partenaire p = table.getSelectionModel().getSelectedItem();
        if (p != null) {
            id = p.getId();
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        partenaire p = table.getSelectionModel().getSelectedItem();
        if (p != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier_personne.fxml"));
                Parent root = loader.load();


                modifier_personneController c = loader.getController();
                c.initData(id);


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {

            System.out.println("Aucune personne sélectionnée !");
        }
    }




    @FXML
    void supprimer(ActionEvent event) {
        partenaireService ps=new partenaireService();
        ps.delete(id);

        showPartenaires();
    }
    @FXML
    void ajouter(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/Ajouter_personne.fxml"));
        try {
            Parent root = loader.load();


            table.getScene().setRoot(root);
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPartenaires();
    }

        public void showPartenaires () {
            partenaireService ps = new partenaireService();
            List<partenaire> p = new ArrayList<>();
            p = ps.readAll();
            ObservableList<partenaire> list = FXCollections.observableArrayList(p);
            table.setItems(list);
            colid.setCellValueFactory(new PropertyValueFactory<partenaire, Integer>("id"));
            colnom.setCellValueFactory(new PropertyValueFactory<partenaire, String>("nom"));
            coltel.setCellValueFactory(new PropertyValueFactory<partenaire, Integer>("tel"));
            coladresse.setCellValueFactory(new PropertyValueFactory<partenaire,String>("adresse"));
            coltype.setCellValueFactory(new PropertyValueFactory<partenaire, String>("type"));
            colemail.setCellValueFactory(new PropertyValueFactory<partenaire, String>("email"));
        }



}
