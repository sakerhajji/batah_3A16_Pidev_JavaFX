package controllers.ControllerProduits;

import Entity.UserAdmin.Membre;
import Entity.entitiesProduits.Command;
import Services.ServiceProduit.ServiceCommand;
import Services.UserAdmineServices.MembreService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class GuiCommandController {

    @FXML
    private TableView<Command> commandHistoryTable;

    @FXML
    private TableColumn<Command, Integer> idColumn;

    @FXML
    private TableColumn<Command, Integer> idClientColumn;

    @FXML
    private TableColumn<Command, String> comDateColumn;

    @FXML
    private TableColumn<Command, Float> totalCostColumn;

    @FXML
    private TableColumn<Command, String> payMethodColumn;

    @FXML
    private TableColumn<Command, String> livMethodColumn;

    @FXML
    private TableColumn<Command, String> etatCommandeColumn;

    @FXML
    private TableColumn<Command, String> adresseColumn;

    private final ServiceCommand serviceCommand = new ServiceCommand();


    public static Membre membre=new Membre() ;
    MembreService membreService = new MembreService() ;
    @FXML
    public void initialize() {
        membre = membre.convertToMembre(membre.loadJsonFromBinFile());
        membre = membreService.readById(membre.getIdUtilisateur());
        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idClientColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        comDateColumn.setCellValueFactory(new PropertyValueFactory<>("comDate"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        payMethodColumn.setCellValueFactory(new PropertyValueFactory<>("payMethod"));
        livMethodColumn.setCellValueFactory(new PropertyValueFactory<>("livMethod"));
        etatCommandeColumn.setCellValueFactory(new PropertyValueFactory<>("etatCommande"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        // Populate the table with command history of user ID 5
        List<Command> commandHistory = serviceCommand.afficherCommands(membre.getIdUtilisateur());
        commandHistoryTable.getItems().addAll(commandHistory);
    }
}
