package controllers;

import controllers.recipesViewControllers.recipesController; // Importación corregida
import controllers.shoppingListControllers.ShoppingListController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CalendarRecipe;
import models.Recipe;
import models.RecipeIngredient;
import services.CalendarService;
import services.ShoppingListService;

import java.io.IOException;
import java.time.DayOfWeek;
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

    private MainController mainController;

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

                VBox dayCell = new VBox(10);
                dayCell.setPadding(new Insets(5));
                dayCell.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 1;");
                GridPane.setMargin(dayCell, new Insets(5));
                dayCell.setAlignment(Pos.TOP_LEFT);
                dayCell.setPrefSize(120, 100);
                dayCell.setStyle(
                        "-fx-border-color: #ccc;" +
                                "-fx-background-color: #fdfdfd;" +
                                "-fx-border-width: 1;" +
                                "-fx-background-radius: 6;" +
                                "-fx-padding: 10;" +
                                "-fx-border-radius: 5;"
                );

                if(day < LocalDate.now().getDayOfMonth()){
                    dayCell.setStyle(dayCell.getStyle() + "-fx-background-color: #d5d5d5;");
                }

                Label dayLabel = new Label(String.valueOf(day));
                dayLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
                dayLabel.setTextFill(Color.DARKSLATEGRAY);
                dayLabel.setFont(Font.font("System Bold", 20));

                //hoy
                if (LocalDate.now().getDayOfMonth() == day &&
                    LocalDate.now().getMonth() == currentYearMonth.getMonth() &&
                    LocalDate.now().getYear() == currentYearMonth.getYear()) {
                    dayCell.setStyle(dayCell.getStyle() + "-fx-background-color: #a9e6e8;");
                    dayLabel.setText(dayLabel.getText() + " - Hoy");
                }

                dayCell.getChildren().add(dayLabel);

                if (recipesByDay.containsKey(day)) {
                    for (String recipeName : recipesByDay.get(day)) {
                        Label recipeLabel = new Label(recipeName);
                        recipeLabel.setFont(Font.font("System Bold", 20));
                        recipeLabel.setWrapText(true);

                        recipeLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
                        recipeLabel.setTextFill(Color.DARKGREEN);
                        recipeLabel.setStyle(
                                "-fx-border-color: #cccccc;" +
                                        "-fx-border-width: 1;" +
                                        "-fx-background-color: #f9f9f9;" +
                                        "-fx-padding: 1 2 1 2;" +
                                        "-fx-background-radius: 5;" +
                                        "-fx-border-radius: 5;"
                        );

                        dayCell.getChildren().add(recipeLabel);
                    }
                }

                final int currentDay = day;
                dayCell.setOnMouseClicked(event -> handleDayClick(currentDay));
                calendarGrid.add(dayCell, col, row);
                day++;
            }
        }
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
    private void generateShoppingList(Event event) {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            List<RecipeIngredient> shoppingList = shoppingListService.getShoppingList(startDate, endDate);

            if (shoppingList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Lista de Compras");
                alert.setHeaderText(null);
                alert.setContentText("No existen recetas en los dias seleccionados");
                alert.showAndWait();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lista de Compras Generada");
            alert.setHeaderText(null);
            alert.setContentText("Se generó una lista de compras con " + shoppingList.size() + " ingredientes.");
            alert.showAndWait();

            //Acceder a modulo de ShoppingList y agregar la lista
            mainController.changeScene("/views/ShoppingListViews/shoppingList.fxml");
            ShoppingListController shoppingListController = (ShoppingListController) mainController.getController("/views/ShoppingListViews/shoppingList.fxml");
            shoppingListController.modifyList(startDate, endDate);


        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en Fechas");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un rango de fechas válido.");
            alert.showAndWait();
        }


    }

    public void importCalendarAction(ActionEvent actionEvent) {
        calendarService.importCalendar();
        drawCalendar();
    }

    public void exportCalendarAction(ActionEvent actionEvent) {
        calendarService.exportCalendar();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}