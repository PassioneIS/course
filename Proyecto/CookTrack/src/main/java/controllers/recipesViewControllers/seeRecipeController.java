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
<<<<<<< HEAD

import javafx.event.Event;

import services.IngredientService;
=======
import services.BookRecipeService; // Asumiendo un servicio que pueda dar detalles
>>>>>>> aa05c3c (feat: Implementa la GUI completa para los módulos de Calendario y Recetario)

import java.io.IOException;
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
<<<<<<< HEAD
    private VBox tagsVbox;

    @FXML
    private Button btnFavorite;

    @FXML
    private Button btnPublic;

    @FXML
    private void initialize(){
        //viewRecipe();

        btnGoBack.setOnAction(event -> {
            //onGoBack(event);
            onClose(event);
        });

    }

    @FXML
    public void viewRecipe(Recipe recipe){
        recipeNameLb.setText("Nombre de la receta:" + recipe.getName());
        prepTimeLb.setText("Tiempo de preparación:" +(recipe.getPreptime()).toString());
=======
    private void initialize() {
        btnGoBack.setOnAction(event -> onGoBack(event));
    }

    public void viewRecipe(Recipe recipe) {
        recipeNameLb.setText(recipe.getName());
        prepTimeLb.setText("Tiempo de preparación: " + recipe.getPreptime() + " minutos");
>>>>>>> aa05c3c (feat: Implementa la GUI completa para los módulos de Calendario y Recetario)

        // Limpiar vistas anteriores
        ingredientsVbox.getChildren().clear();
        stepsVbox.getChildren().clear();

<<<<<<< HEAD
        List<RecipeIngredient> recipeIngredients = ingredientService.getRecipeIngredients(recipe);

        for(RecipeIngredient recipeIngredient : recipeIngredients){

            String ingredientName = (ingredientService.getIngredientById( recipeIngredient.getIngredient().getId() ) ).getName();

            int amount = recipeIngredient.getAmount();

            Label newLabel = new Label("• "+ ingredientName + "— Cantidad:" + amount);
            newLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 13;");

            ingredientsVbox.getChildren().add(newLabel);
        }

        List<RecipeStep> recipeStepList = ingredientService.getRecipeStepbyRecipe( recipe );

        System.out.println("El tamaño es de recipe step list es:" + recipeStepList.size());

        for(RecipeStep recipeStep: recipeStepList){
            int numStep = recipeStep.getPosition();
            String text = recipeStep.getText();

            System.out.println("Paso:" + numStep + ", es:" + text);

            Label newLabel = new Label("Paso "+ numStep + ":" + text);
            newLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 13;");

            stepsVbox.getChildren().add(newLabel);
=======
        // Lógica para obtener y mostrar ingredientes y pasos
        // (Esta parte puede necesitar un método en BookRecipeService)
        List<RecipeIngredient> recipeIngredients = recipe.getRecipeIngredients();
        for (RecipeIngredient ri : recipeIngredients) {
            Label ingredientLabel = new Label(ri.getIngredient().getName() + " - " + ri.getAmount());
            ingredientsVbox.getChildren().add(ingredientLabel);
        }

        List<RecipeStep> recipeSteps = recipe.getRecipeSteps();
        for (RecipeStep step : recipeSteps) {
            Label stepLabel = new Label("Paso " + step.getPosition() + ": " + step.getText());
            stepsVbox.getChildren().add(stepLabel);
>>>>>>> aa05c3c (feat: Implementa la GUI completa para los módulos de Calendario y Recetario)
        }

        BookRecipeService bookRecipeService = new BookRecipeService();

        RecipeBookRecipe recipeBookRecipe = bookRecipeService.findByRecipe(recipe);

        List<String> nameTags = recipeBookRecipe.getNametag();

        for(String tag : nameTags){
            System.out.println("🏷" + tag);

            Label newLabel = new Label(tag);
            newLabel.setStyle("-fx-text-fill: #555555; -fx-background-color: #eeeeee; -fx-padding: 4 8 4 8; -fx-background-radius: 5;");

            tagsVbox.getChildren().add(newLabel);
        }

        if(recipeBookRecipe.isFavorite() == false){
            btnFavorite.setText("Añadir a favoritos");
        }
        else{
            Label newLabel = new Label("★ Esta receta está en favoritos");
            newLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            tagsVbox.getChildren().add(newLabel);

            btnFavorite.setText("Retirar de favoritos");
        }

        if(recipeBookRecipe.isPublic() == false){
            Label newLabel = new Label("La receta es privada");
            newLabel.setStyle("-fx-text-fill: #FF5722; -fx-font-weight: bold;");
            tagsVbox.getChildren().add(newLabel);

            btnPublic.setText("Hacer receta publica");
        }
        else{
            Label newLabel = new Label("La receta es publica");
            newLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
<<<<<<< HEAD

            System.out.println("onGoBack");
=======
>>>>>>> aa05c3c (feat: Implementa la GUI completa para los módulos de Calendario y Recetario)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD

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

=======
>>>>>>> aa05c3c (feat: Implementa la GUI completa para los módulos de Calendario y Recetario)
}