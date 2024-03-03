package controllers.ServiceApresVente;

import Entity.entitiesPartenaire.Partenaire;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import Services.servicePartenaire.partenaireService;
import controllers.controllerPartenaire.emailPartenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AffectationController implements Initializable {

    @FXML
    private TableColumn<Partenaire, String> nomPartenaireColumn;
    @FXML
    private TableColumn<Partenaire, Integer> idPartenaireColumn;

    @FXML
    private TableColumn<Partenaire, Void> affectationColumn;

    @FXML
    private TableView<Partenaire> tableAff;

private int idService;

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
public void init(int idService)
{
    setIdService(idService);
}


    private partenaireService partenaireService = new partenaireService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonAffecter();
        afficherDonnees();
    }


    private void afficherDonnees() {

        partenaireService ps=new partenaireService();
        List<Partenaire> p=ps.readPartenaire();

        ObservableList<Partenaire> list=FXCollections.observableArrayList(p);
        tableAff.setItems(list);
        idPartenaireColumn.setCellValueFactory(new PropertyValueFactory<Partenaire,Integer>("id"));
        nomPartenaireColumn.setCellValueFactory(new PropertyValueFactory<Partenaire,String>("nom"));


    }
public void Affecter(ServiceApresVente s, Partenaire p)
{
    ServiceApresVentS sa= new ServiceApresVentS();
    sa.Affecter(s,p);
    emailPartenaire email=new emailPartenaire();
    String mail="vous avez effectuer pour la reclamation Numero:"+s.getIdService();
    email.sendEmail(p.getEmail(),"Affectation",mail);
    partenaireService.updatePoints(p);
    sa.updateStatus(s);

}


    private void buttonAffecter() {
        affectationColumn.setCellFactory(param -> new TableCell<>() {
            private final Button affecterButton = new Button("Affecter"); // Créez un bouton avec le texte "Affecter"

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(affecterButton);
                    affecterButton.setOnAction(event -> {
                        // Votre logique lorsque le bouton "Affecter" est cliqué
                        // Par exemple, récupérer l'objet associé à cette ligne
                        Partenaire par = getTableView().getItems().get(getIndex());
                        Partenaire p=partenaireService.readById(par.getId());
                        ServiceApresVentS s=new ServiceApresVentS();
                        ServiceApresVente service=s.readById(getIdService());


                        Affecter(service,p);
                        Stage stage= (Stage) affecterButton.getScene().getWindow();
                        stage.close();

                    });
                }
            }
        });
    }
}
