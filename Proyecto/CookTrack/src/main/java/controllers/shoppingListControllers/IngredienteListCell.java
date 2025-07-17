package controllers.shoppingListControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import models.RecipeIngredient;

public class IngredienteListCell extends ListCell<RecipeIngredient> {
    private FXMLLoader loader;

    @Override
    protected void updateItem(RecipeIngredient item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/views/ShoppingListViews/IngredientCell.fxml"));
                try {
                    loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            IngredientCellController controller = loader.getController();
            controller.setIngrediente(item);
            setGraphic(loader.getRoot());
        }
    }
}