package controllers;

import controllers.recipesViewControllers.recipesController; // Importación corregida
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CalendarRecipe;
import models.Recipe;
import models.RecipeIngredient;
import services.CalendarService;
import services.ShoppingListService;

import java.io.IOException;
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

    private void handleDayClick(int day) {
        try {
            LocalDate clickedDate = currentYearMonth.atDay(day);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/recipesView.fxml"));
            Scene scene = new Scene(loader.load());
            recipesController controller = loader.getController();
            controller.setSelectionMode(true);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Seleccionar Receta para Añadir");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

            Recipe selectedRecipe = controller.getSelectedRecipe();
            if (selectedRecipe != null) {
                calendarService.assignRecipeToDate(selectedRecipe, clickedDate);
                drawCalendar();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lista de Compras Generada");
            alert.setHeaderText(null);
            alert.setContentText("Se generó una lista de compras con " + shoppingList.size() + " ingredientes.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en Fechas");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un rango de fechas válido.");
            alert.showAndWait();
        }
    }

    public void importCalendarAction(ActionEvent actionEvent) {
    }

    public void exportCalendarAction(ActionEvent actionEvent) {
    }
}