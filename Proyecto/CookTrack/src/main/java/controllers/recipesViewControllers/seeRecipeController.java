package controllers.recipesViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

import models.Recipe;
import models.RecipeIngredient;
import models.RecipeStep;


import services.IngredientService;

import java.util.List;

public class seeRecipeController{
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
    private void initialize(){
        //viewRecipe();
    }

    @FXML
    public void viewRecipe(Recipe recipe){
        recipeNameLb.setText("Nombre de la receta" + recipe.getName());
        prepTimeLb.setText("Tiempo de preparación:" +(recipe.getPreptime()).toString());

        IngredientService ingredientService = new IngredientService();

        List<RecipeIngredient> recipeIngredients = ingredientService.getRecipeIngredients(recipe);

        for(RecipeIngredient recipeIngredient : recipeIngredients){

            String ingredientName = (ingredientService.getIngredientById( recipeIngredient.getIngredient().getId() ) ).getName();

            int amount = recipeIngredient.getAmount();

            Label newLabel = new Label("Ingredient:"+ ingredientName + ", cantidad:" + amount);

            ingredientsVbox.getChildren().add(newLabel);
        }

        List<RecipeStep> recipeStepList = ingredientService.getRecipeStepbyRecipe( recipe );

        System.out.println("El tamaño es de recipe step list es:" + recipeStepList.size());

        for(RecipeStep recipeStep: recipeStepList){
            int numStep = recipeStep.getPosition();
            String text = recipeStep.getText();

            System.out.println("Paso:" + numStep + ", es:" + text);

            Label newLabel = new Label("Paso numero:"+ numStep + ", es:" + text);

            stepsVbox.getChildren().add(newLabel);
        }



    }
}