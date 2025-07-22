package controllers.recipesViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
import services.BookRecipeService;

import java.io.IOException;
import java.util.List;

import dao.impl.RecipeBookRecipeDaoImpl;
import models.RecipeBookRecipe;

public class seeRecipeController {

    @FXML private Button btnGoBack;
    @FXML private Label recipeNameLb;
    @FXML private Label prepTimeLb;
    @FXML private VBox ingredientsVbox;
    @FXML private VBox stepsVbox;
    @FXML private VBox tagsVbox;
    @FXML private Button btnFavorite;
    @FXML private Button btnPublic;

    private final IngredientService ingredientService = new IngredientService();
    private final BookRecipeService bookRecipeService = new BookRecipeService();

    @FXML
    private void initialize(){
        //viewRecipe();

        btnGoBack.setOnAction(event -> {
            //onGoBack(event);
            onClose(event);
        });

    }

    @FXML
    public void viewRecipe(Recipe recipe) {
        recipeNameLb.setText("Nombre de la receta: " + recipe.getName());
        prepTimeLb.setText("Tiempo de preparación: " + recipe.getPreptime() + " minutos");

        // Limpiar vistas anteriores
        ingredientsVbox.getChildren().clear();
        stepsVbox.getChildren().clear();
        tagsVbox.getChildren().clear();

        // Mostrar ingredientes
        List<RecipeIngredient> recipeIngredients = ingredientService.getRecipeIngredients(recipe);
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            String ingredientName = ingredientService.getIngredientById(recipeIngredient.getIngredient().getId()).getName();
            int amount = recipeIngredient.getAmount();

            Label label = new Label("• " + ingredientName + " — Cantidad: " + amount);
            label.setStyle("-fx-text-fill: #333333; -fx-font-size: 13;");
            ingredientsVbox.getChildren().add(label);
        }

        // Mostrar pasos
        List<RecipeStep> recipeStepList = ingredientService.getRecipeStepbyRecipe(recipe);
        for (RecipeStep recipeStep : recipeStepList) {
            Label label = new Label("Paso " + recipeStep.getPosition() + ": " + recipeStep.getText());
            label.setStyle("-fx-text-fill: #333333; -fx-font-size: 13;");
            stepsVbox.getChildren().add(label);
        }

        // Mostrar etiquetas
        RecipeBookRecipe recipeBookRecipe = bookRecipeService.findByRecipe(recipe);
        List<String> nameTags = recipeBookRecipe.getNametag();

        for (String tag : nameTags) {
            Label tagLabel = new Label(tag);
            tagLabel.setStyle("-fx-text-fill: #555555; -fx-background-color: #eeeeee; -fx-padding: 4 8 4 8; -fx-background-radius: 5;");
            tagsVbox.getChildren().add(tagLabel);
        }

        // Estado de favorito
        if (!recipeBookRecipe.isFavorite()) {
            btnFavorite.setText("Añadir a favoritos");
        } else {
            Label favLabel = new Label("★ Esta receta está en favoritos");
            favLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            tagsVbox.getChildren().add(favLabel);
            btnFavorite.setText("Retirar de favoritos");
        }

        // Estado de público/privado
        if (!recipeBookRecipe.isPublic()) {
            Label privLabel = new Label("La receta es privada");
            privLabel.setStyle("-fx-text-fill: #FF5722; -fx-font-weight: bold;");
            tagsVbox.getChildren().add(privLabel);
            btnPublic.setText("Hacer receta publica");
        } else {
            Label pubLabel = new Label("La receta es publica");
            pubLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
            tagsVbox.getChildren().add(pubLabel);
            btnPublic.setText("Hacer receta privada");
        }

        // Botones
        btnPublic.setOnAction(event -> {
            onChangePublic(recipeBookRecipe.isPublic());
            bookRecipeService.changePublic(recipeBookRecipe);
        });

        btnFavorite.setOnAction(event -> {
            onChangeFavorite(recipeBookRecipe.isFavorite());
            bookRecipeService.changeFavorite(recipeBookRecipe);
        });
    }

    @FXML
    public void onGoBack(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/recipesView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            System.out.println("onGoBack");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClose(Event event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            System.out.println("OnClose seeRecipe");
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