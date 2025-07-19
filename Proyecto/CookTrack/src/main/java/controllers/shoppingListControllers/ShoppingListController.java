package controllers.shoppingListControllers;

import dao.impl.RecipeIngredientDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Ingredient;
import models.Recipe;
import models.RecipeIngredient;
import services.ShoppingListService;
import java.time.LocalDate;

public class ShoppingListController {

    private final ShoppingListService shoppingListService = ShoppingListService.getInstance();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ListView<RecipeIngredient> listViewIngredientes;

    @FXML
    private Button createListButton;

    @FXML
    private Button deleteButton;

    @FXML
    public Button addButton;

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

        addButton.setOnAction(e -> {
            handleAddition();
        });
    }

    private void handleAddition() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShoppingListViews/addIngredient.fxml"));
            Parent root = loader.load();

            AddIngredientController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Modal window
            stage.setTitle("Añadir Ingrediente");
            stage.setScene(new Scene(root));

            stage.setResizable(false);
            stage.showAndWait(); // Espera hasta cerrar

            Ingredient newIngredient = controller.getIngredient();
            Short amount = controller.getAmount();

            if (newIngredient != null && amount != null){

                RecipeIngredient newIngredientRecipe = new RecipeIngredient();

                newIngredientRecipe.setIngredient(newIngredient);
                newIngredientRecipe.setAmount(amount);

                boolean already = false;
                for(RecipeIngredient recipeIngredient : listViewIngredientes.getItems()){
                    if (recipeIngredient.getIngredient().getId() == newIngredient.getId()) {
                        already = true;
                        recipeIngredient.setAmount((short) (recipeIngredient.getAmount() + newIngredientRecipe.getAmount()));
                        break;
                    }
                }
                if (!already){
                    listViewIngredientes.getItems().add(newIngredientRecipe);
                }
                listViewIngredientes.refresh();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGenerateList(){

        LocalDate startDate = this.startDate.getValue();
        LocalDate endDate = this.endDate.getValue();

        if(startDate == null || endDate == null){
            System.out.println("La lista de ingredientes no puede ser nula");
            return;
        }

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
