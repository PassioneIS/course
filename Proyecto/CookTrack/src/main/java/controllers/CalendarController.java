package controllers;

import controllers.recipesViewControllers.AddRecipeToCalendarController;
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
        calendarGrid.getChildren().clear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "ES"));
        monthYearLabel.setText(currentYearMonth.format(formatter));

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        LocalDate startDate = firstOfMonth;
        LocalDate endDate = currentYearMonth.atEndOfMonth();

        List<CalendarRecipe> calendarRecipesThisMonth = calendarService.getCalendarRecipesByDate(startDate, endDate);

        Map<Integer, List<String>> recipesByDay = calendarRecipesThisMonth.stream()
                .collect(Collectors.groupingBy(
                        cr -> cr.getDate().getDayOfMonth(),
                        Collectors.mapping(cr -> cr.getRecipe().getName(), Collectors.toList())
                ));

        int day = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 0 && col < firstDayOfWeek - 1) {
                    continue;
                }
                if (day > currentYearMonth.lengthOfMonth()) {
                    break;
                }

                VBox dayCell = new VBox(5);
                dayCell.setPadding(new Insets(5));
                dayCell.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 1;");

                Label dayLabel = new Label(String.valueOf(day));
                dayLabel.setFont(Font.font("System Bold", 14));
                dayCell.getChildren().add(dayLabel);

                if (recipesByDay.containsKey(day)) {
                    for (String recipeName : recipesByDay.get(day)) {
                        Label recipeLabel = new Label(recipeName);
                        recipeLabel.setWrapText(true);
                        dayCell.getChildren().add(recipeLabel);
                    }
                }

                final int currentDay = day;
                dayCell.setOnMouseClicked(event -> handleDayClick(currentDay));

                calendarGrid.add(dayCell, col, row);
                day++;
            }
        }
    }

    private void handleDayClick(int day) {
        try {
            LocalDate clickedDate = currentYearMonth.atDay(day);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/AddRecipeToCalendarDialog.fxml"));
            Scene scene = new Scene(loader.load());
            AddRecipeToCalendarController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Añadir Receta al Calendario");
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
}