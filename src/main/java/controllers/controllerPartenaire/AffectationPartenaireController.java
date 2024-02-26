package controllers.controllerPartenaire;

import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.servicePartenaire.partenaireService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AffectationPartenaireController  implements Initializable{

    @FXML
    private TableColumn<ServiceApresVente, Date> date;

    @FXML
    private TableColumn<ServiceApresVente, String> description;

    @FXML
    private TableColumn<ServiceApresVente, Integer> idAchat;

    @FXML
    private TableColumn<ServiceApresVente, Void> delete;

    @FXML
    private TableColumn<ServiceApresVente, Integer> idReclamtion;
    @FXML
    private TableColumn<ServiceApresVente, String> type;

    @FXML
    private TableView<ServiceApresVente> tableAff;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void init(int id)
    {
       setId(id);
        ServiceApresVentS sav = new ServiceApresVentS();
        List<ServiceApresVente> s = sav.readByIdPartenaire(getId());

        ObservableList<ServiceApresVente> list = FXCollections.observableArrayList(s);
        tableAff.setItems(list);
        idReclamtion.setCellValueFactory(new PropertyValueFactory<ServiceApresVente, Integer>("idService"));
        date.setCellValueFactory(new PropertyValueFactory<ServiceApresVente, Date>("date"));
        description.setCellValueFactory(new PropertyValueFactory<ServiceApresVente, String>("description"));
        type.setCellValueFactory(new PropertyValueFactory<ServiceApresVente, String>("type"));
        idAchat.setCellValueFactory(cellData -> {
            int AchatId = cellData.getValue().getIdAchats().getIdAchats();
            return new SimpleIntegerProperty(AchatId).asObject();
        });

        


    }

    public void showAffectation () {
init(id);
    }
    void supprimer(ServiceApresVente event) {

        ServiceApresVentS ps=new ServiceApresVentS();
        ps.delete(event);
        showAffectation();
    }

    private void buttonSupprimer() {
        delete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final ImageView imageView = new ImageView();
            private final double iconWidth = 24;
            private final double iconHeight = 24;

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Image image = new Image("cssPartenaire/supprimer.png", iconWidth, iconHeight, true, true);
                    imageView.setImage(image);

                    deleteButton.setGraphic(imageView);
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        ServiceApresVente sa = getTableView().getItems().get(getIndex());
                        supprimer(sa);
                    });
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showAffectation();
        buttonSupprimer();
    }
}
