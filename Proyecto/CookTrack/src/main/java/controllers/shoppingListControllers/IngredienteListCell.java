package controllers.shoppingListControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import models.RecipeIngredient;

import java.io.IOException;

public class IngredienteListCell extends ListCell<RecipeIngredient> {
    private HBox graphic;
    private IngredientCellController controller;

    public IngredienteListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ShoppingListViews/IngredientCell.fxml"));
            graphic = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(RecipeIngredient item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            controller.setData(item);
            setGraphic(graphic);
        }
    }
}