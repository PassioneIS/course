package controllers.recipesViewControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Recipe;
import models.RecipeIngredient;
import models.RecipeStep;
import services.IngredientService;

import java.util.List;

public class seeRecipeController {

    @FXML
    private Button btnGoBack;

    @FXML
    private Label recipeNameLb;

    @FXML
    private Label prepTimeLb;

    @FXML
    private VBox ingredientsVbox;

    @FXML
    private VBox stepsVbox;

    @FXML
    private VBox tagsVbox;

    @FXML
    private void initialize() {
        //viewRecipe();

        btnGoBack.setOnAction(event -> {
            onGoBack(event);
        });

    }

    @FXML
    public void viewRecipe(Recipe recipe) {
        recipeNameLb.setText("Nombre de la receta" + recipe.getName());
        prepTimeLb.setText("Tiempo de preparación:" + (recipe.getPreptime()).toString());

        IngredientService ingredientService = new IngredientService();

        List<RecipeIngredient> recipeIngredients = ingredientService.getRecipeIngredients(recipe);

        for (RecipeIngredient recipeIngredient : recipeIngredients) {

            String ingredientName = (ingredientService.getIngredientById(recipeIngredient.getIngredient().getId())).getName();

            int amount = recipeIngredient.getAmount();

            Label newLabel = new Label("Ingredient:" + ingredientName + ", cantidad:" + amount);

            ingredientsVbox.getChildren().add(newLabel);
        }

        List<RecipeStep> recipeStepList = ingredientService.getRecipeStepbyRecipe(recipe);

        System.out.println("El tamaño es de recipe step list es:" + recipeStepList.size());

        for (RecipeStep recipeStep : recipeStepList) {
            int numStep = recipeStep.getPosition();
            String text = recipeStep.getText();

            System.out.println("Paso:" + numStep + ", es:" + text);

            Label newLabel = new Label("Paso numero:" + numStep + ", es:" + text);

            stepsVbox.getChildren().add(newLabel);
        }
    }


    @FXML
    public void onGoBack(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/recipesView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual y cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMaximized(true);
            stage.show();
            System.out.println("onGoBack");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}