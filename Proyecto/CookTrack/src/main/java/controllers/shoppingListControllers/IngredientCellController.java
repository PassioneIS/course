package controllers.shoppingListControllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import models.RecipeIngredient;

public class IngredientCellController {

    @FXML
    private CheckBox checkBox;

    @FXML
    private Text cantidadText;

    private RecipeIngredient ingrediente;

    public void setIngrediente(RecipeIngredient ingrediente) {
        this.ingrediente = ingrediente;

        if (ingrediente != null) {
            checkBox.setText(ingrediente.getIngredient().getName());
            cantidadText.setText(ingrediente.getAmount() + " g/u");
            checkBox.setSelected(false);

            checkBox.setOnAction(e -> {
                ingrediente.setChecked(checkBox.isSelected());
            });
        }
    }
}