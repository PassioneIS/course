package controllers.recipesViewControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import models.Recipe;
import services.BookRecipeService;


import java.util.HashMap;
import java.util.Map;
import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;


public class recipesController {

    /*
    @FXML
    private BorderPane mainScene;
     */

    @FXML
    private VBox recipesVbox;

    @FXML
    private Button btnAddRecipe;

    @FXML
    private Button btnGoBack;

    private Map<String, Parent> loadedViews = new HashMap<>();

    @FXML
    public void initialize() {

        listRecipes();

        btnAddRecipe.setOnAction(event -> {
            onAddRecipe(event);
        });

        btnGoBack.setOnAction(event -> {
            onGoBack(event);
        });

    }

    @FXML
    private void listRecipes() {

        BookRecipeService bookRecipeService = new BookRecipeService();
        List<Recipe> recipesList = bookRecipeService.getAllRecipes();

        for(Recipe recipe: recipesList) {

            HBox hbox = new HBox(10);
            hbox.setStyle("-fx-padding: 10; -fx-border-color: #cccccc; -fx-border-radius: 5;");

            Label nameLabel = new Label("Nombre:" + recipe.getName() );
            Label prepTimeLabel = new Label("Prep time:" + recipe.getPreptime() );
            Label idLabel = new Label("id:" + recipe.getId() );

            Button btnSeeMore = new Button("Ver más");

            btnSeeMore.setOnAction(e -> onSeeMore(recipe, e) );

            hbox.getChildren().addAll(nameLabel, prepTimeLabel, idLabel, btnSeeMore);

            recipesVbox.getChildren().add(hbox);
        }
    }

    @FXML
    private void onSeeMore(Recipe recipe, Event event){
        System.out.println("onSeeMore" + recipe.getName() );

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/seeRecipeView.fxml"));
            Scene scene = new Scene(loader.load());

            seeRecipeController controller = loader.getController();
            controller.viewRecipe(recipe);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMaximized(true);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddRecipe(Event event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/addRecipeView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual y cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMaximized(true);
            stage.show();
            System.out.println("onAddRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onGoBack(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual y cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setMaximized(true);
            stage.show();
            System.out.println("onGoBack to Main");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}