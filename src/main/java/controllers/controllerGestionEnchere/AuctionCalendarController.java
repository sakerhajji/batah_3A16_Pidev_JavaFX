package controllers.controllerGestionEnchere;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.YearMonth;

public class AuctionCalendarController {
    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        // Get the current year and month
        LocalDate currentDate = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(currentDate);

        // Get the number of days in the month
        int daysInMonth = yearMonth.lengthOfMonth();

        // Get the first day of the month
        LocalDate firstOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), 1);

        // Find the day of the week for the first day of the month
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // Fill in the grid with day labels
        int row = 0;
        int col = startDayOfWeek;
        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(Integer.toString(day));
            calendarGrid.add(dayLabel, col, row);
            GridPane.setHalignment(dayLabel, HPos.CENTER);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }
}
