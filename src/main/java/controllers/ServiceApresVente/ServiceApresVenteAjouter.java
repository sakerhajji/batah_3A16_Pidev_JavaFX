package controllers.ServiceApresVente;
import Entity.entitiesServiceApresVente.ServiceApresVente;
import Services.ServiceApresVentS.ServiceApresVentS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class ServiceApresVenteAjouter {
    @FXML
    private Button addService;

    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private ComboBox<?> type;

    ServiceApresVentS sav=new ServiceApresVentS();

    @FXML
    void addService(ActionEvent event) {
        LocalDate dat= date.getValue();
        Date date = java.sql.Date.valueOf(dat);
        String descript=description.getText();
        String tp = (String) type.getValue();
        ServiceApresVente service=new ServiceApresVente(descript,tp,date);
        sav.add(service);

    }
}
