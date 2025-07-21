package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.CalendarRecipe;
import models.Recipe;
import models.RecipeIngredient;
import services.CalendarService;
import services.ShoppingListService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CalendarController {

    @FXML
    private Label monthYearLabel;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private YearMonth currentYearMonth;
    private final CalendarService calendarService;
    private final ShoppingListService shoppingListService;

    public CalendarController() {
        this.calendarService = new CalendarService();
        this.shoppingListService = ShoppingListService.getInstance();
    }

    @FXML
    public void initialize() {
        currentYearMonth = YearMonth.now();
        drawCalendar();
    }

    private void drawCalendar() {
        // ... (el código para dibujar el calendario que ya teníamos)
    }

    @FXML
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        drawCalendar();
    }

    @FXML
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        drawCalendar();
    }

    @FXML
    private void generateShoppingList() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            List<RecipeIngredient> shoppingList = shoppingListService.getShoppingList(startDate, endDate);

            System.out.println("Se generó una lista de compras con " + shoppingList.size() + " ingredientes.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lista de Compras Generada");
            alert.setHeaderText(null);
            alert.setContentText("Se generó una lista de compras con " + shoppingList.size() + " ingredientes.\n\n(Aquí se mostraría la lista en su respectiva vista)");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en Fechas");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un rango de fechas válido.");
            alert.showAndWait();
        }
    }
}