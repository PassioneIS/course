package controllers.shoppingListControllers;

import dao.impl.RecipeIngredientDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.RecipeIngredient;
import services.ShoppingListService;
import java.time.LocalDate;

public class ShoppingListController {

    private final ShoppingListService shoppingListService = new ShoppingListService(new RecipeIngredientDaoImpl());

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ListView<RecipeIngredient> listViewIngredientes;

    @FXML
    private Button createListButton;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;


    @FXML
    public void initialize() {
        listViewIngredientes.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.5));

        listViewIngredientes.setCellFactory(param -> new IngredienteListCell());

        startDate.setOnAction(e -> {
            setDatePickerRange(endDate,startDate.getValue(),LocalDate.MAX);
        });

        endDate.setOnAction(e -> {
            setDatePickerRange(startDate,LocalDate.MIN,endDate.getValue());
        });

        createListButton.setOnAction(e -> {
            handleGenerateList();
        });

        deleteButton.setOnAction(e -> {
            handleDeletion();
        });
    }

    private void handleGenerateList(){

        LocalDate startDate = this.startDate.getValue();
        LocalDate endDate = this.endDate.getValue();

        ObservableList<RecipeIngredient> ingredients = FXCollections.observableArrayList(
                shoppingListService.getShoppingList(startDate,endDate)
        );

        listViewIngredientes.setItems(ingredients);
    }

    private  void handleDeletion(){
        listViewIngredientes.getItems().removeIf(RecipeIngredient::isChecked);
    };

    private void setDatePickerRange(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker picker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate) || item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        });
    }
}
