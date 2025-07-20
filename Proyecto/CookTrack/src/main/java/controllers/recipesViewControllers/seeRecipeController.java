package controllers.recipesViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
//import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

import models.Recipe;
import models.RecipeIngredient;
import models.RecipeStep;

import javafx.event.Event;

import services.IngredientService;

import java.util.List;

import dao.impl.RecipeBookRecipeDaoImpl;
import models.RecipeBookRecipe;

import services.BookRecipeService;

public class seeRecipeController{

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
    private Button btnFavorite;

    @FXML
    private Button btnPublic;

    @FXML
    private void initialize(){
        //viewRecipe();

        btnGoBack.setOnAction(event -> {
            onGoBack(event);
        });

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

        BookRecipeService bookRecipeService = new BookRecipeService();

        RecipeBookRecipe recipeBookRecipe = bookRecipeService.findByRecipe(recipe);

        List<String> nameTags = recipeBookRecipe.getNametag();

        for(String tag : nameTags){
            System.out.println("Tag:" + tag);

            Label newLabel = new Label(tag);

            tagsVbox.getChildren().add(newLabel);
        }

        if(recipeBookRecipe.isFavorite() == false){
            btnFavorite.setText("Añadir a favoritos");
        }
        else{
            Label newLabel = new Label("La receta esta en favoritos");
            tagsVbox.getChildren().add(newLabel);

            btnFavorite.setText("Retirar de favoritos");
        }

        if(recipeBookRecipe.isPublic() == false){
            Label newLabel = new Label("La receta es privada");
            tagsVbox.getChildren().add(newLabel);

            btnPublic.setText("Hacer receta publica");
        }
        else{
            Label newLabel = new Label("La receta es publica");
            tagsVbox.getChildren().add(newLabel);

            btnPublic.setText("Hacer receta privada");
        }

        btnPublic.setOnAction( event -> {
            onChangePublic(recipeBookRecipe.isPublic());

            bookRecipeService.changePublic(recipeBookRecipe);
        });

        btnFavorite.setOnAction( event -> {
            onChangeFavorite(recipeBookRecipe.isFavorite());

            bookRecipeService.changeFavorite(recipeBookRecipe);
        });

    }


    @FXML
    public void onGoBack(Event event){
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

    @FXML
    public void onChangePublic(boolean isPublic){
        if(isPublic == false){
            btnPublic.setText("Hacer receta privada");
        }
        else{
            btnPublic.setText("Hacer receta publica");
        }
    }

    @FXML
    public void onChangeFavorite(boolean isFavorite){
        if(isFavorite == false){
            btnFavorite.setText("Retirar de favoritos");
        }
        else{
            btnFavorite.setText("Añadir a favoritos");
        }
    }

}