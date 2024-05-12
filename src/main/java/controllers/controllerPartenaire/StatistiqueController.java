package controllers.controllerPartenaire;

import Entity.entitiesPartenaire.Partenaire;
import Services.servicePartenaire.partenaireService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StatistiqueController implements Initializable {

    @FXML
    private LineChart<String, Integer> lineChart;

    private List<Partenaire> partenaires;

    public void setPartenaires(List<Partenaire> partenaires) {
        this.partenaires = partenaires;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherCourbeStatistique();
        addAnimation();
    }

    private void afficherCourbeStatistique() {
        partenaireService ps = new partenaireService();
        partenaires = ps.readAll();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Points des partenaires");

        for (Partenaire partenaire : partenaires) {
            series.getData().add(new XYChart.Data<>(partenaire.getNom(), partenaire.getPoints()));
        }

        lineChart.getData().add(series);
    }

    private void addAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), lineChart);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
}
