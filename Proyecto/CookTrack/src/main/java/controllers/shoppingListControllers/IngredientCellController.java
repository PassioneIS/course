package controllers.shoppingListControllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import models.RecipeIngredient;

public class IngredientCellController {

    @FXML
    private CheckBox checkBox;
    @FXML
    private Label nameLabel;
    @FXML
    private Label amountLabel;

    private RecipeIngredient recipeIngredient;

    public void setData(RecipeIngredient recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
        nameLabel.setText(recipeIngredient.getIngredient().getName());
        amountLabel.setText(String.valueOf(recipeIngredient.getAmount()));
        checkBox.setSelected(recipeIngredient.isChecked());
    }

    @FXML
    private void onCheckBoxAction() {
        if (recipeIngredient != null) {
            recipeIngredient.setChecked(checkBox.isSelected());
        }
    }
}