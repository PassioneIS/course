package controllers.shoppingListControllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Ingredient;
import services.ShoppingListService;

import java.util.List;

public class AddIngredientController {
    @FXML
    private ComboBox<Ingredient> ingredientComboBox;

    @FXML
    private TextField amountField;

    //To store last input
    private Ingredient ingredient;
    private Short amount;

    @FXML
    private void initialize() {
        // Restringir amountField a números enteros (short)
        amountField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,5}")) { // short max value 32767, 5 digits
                amountField.setText(oldValue);
            }
        });

        initComboBox();
    }

    @FXML
    private void handleAdd() {
        ingredient = ingredientComboBox.getValue();

        if (ingredient == null) {
            System.out.println("Ingredient is null");
            return;
        }

        if (!amountField.getText().isEmpty()) {
            try {
                amount = Short.parseShort(amountField.getText());
            } catch (NumberFormatException e) {
                amount = null; // No es un short válido
            }
        }else{
            //Return
            System.out.println("amount is null");
            return;
        }



        // Cierra la ventana
        Stage stage = (Stage) ingredientComboBox.getScene().getWindow();
        stage.close();
    }



    @FXML
    private void initComboBox() {
        // Inicializa lista y filtered list
        ObservableList<Ingredient> allIngredients = FXCollections.observableArrayList(
                ShoppingListService.getInstance().getIngredients()
        );
        FilteredList<Ingredient> filteredIngredients = new FilteredList<>(allIngredients, p -> true);
        ingredientComboBox.setItems(filteredIngredients);
        ingredientComboBox.setEditable(true);

        // Filtrado dinámico
        ingredientComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                filteredIngredients.setPredicate(p -> true);
            } else {
                String lower = newVal.toLowerCase();
                filteredIngredients.setPredicate(ing -> ing.getName().toLowerCase().contains(lower));
            }
            if (!ingredientComboBox.isShowing() && !filteredIngredients.isEmpty()) {
                ingredientComboBox.show();
            }
        });

        //Configurar que se muestra del objeto
        ingredientComboBox.setConverter(new StringConverter<Ingredient>() {
            @Override
            public String toString(Ingredient ingredient) {
                return ingredient != null ? ingredient.getName() : "";
            }

            @Override
            public Ingredient fromString(String string) {
                for (Ingredient ing : ingredientComboBox.getItems()) {
                    if (ing.getName().equals(string)) {
                        return ing;
                    }
                }
                return null;
            }
        });

    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Short getAmount() {
        return amount;
    }

}