package controllers.recipesViewControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Recipe;
import services.BookRecipeService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.stage.Modality;

import services.PDFService;

public class recipesController {

    @FXML
    private VBox recipesVbox;

    @FXML
    private Button btnAddRecipe;

    @FXML
    private Button btnGoBack;

    private final Map<String, Parent> loadedViews = new HashMap<>();

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

        for (Recipe recipe : recipesList) {

            HBox hbox = new HBox(10);
            hbox.setStyle("-fx-background-color: #ffffff;" + "-fx-padding: 15;" + "-fx-background-radius: 8;" + "-fx-border-radius: 8;" + "-fx-border-color: #dddddd;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 4, 0, 0, 2);");

            Label nameLabel = new Label("Nombre:" + recipe.getName());
            nameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");

            Label prepTimeLabel = new Label("Tiempo de preparacion:" + recipe.getPreptime());
            prepTimeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 13;");

            Label idLabel = new Label("id:" + recipe.getId());
            idLabel.setStyle("-fx-text-fill: #999999; -fx-font-size: 12;");

            Button btnSeeMore = new Button("Ver más");
            btnSeeMore.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;");
            btnSeeMore.setOnAction(e -> onSeeMore(recipe, e));

            Button btnPDF = new Button("Descargar");
            btnPDF.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;");
            btnPDF.setOnAction(e -> ondownloadPDF(recipe, e));


            hbox.getChildren().addAll(nameLabel, prepTimeLabel, idLabel, btnSeeMore, btnPDF);

            recipesVbox.getChildren().add(hbox);
        }
    }

    @FXML
    private void onSeeMore(Recipe recipe, Event event) {
        System.out.println("onSeeMore" + recipe.getName());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/seeRecipeView.fxml"));
            Scene scene = new Scene(loader.load());

            seeRecipeController controller = loader.getController();
            controller.viewRecipe(recipe);

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.centerOnScreen();
            newStage.setTitle("Receta");

            //bloquear la ventana anterior hasta que esta se cierre
            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ondownloadPDF(Recipe recipe, Event event){
        System.out.println("ondownloadPDF" + recipe.getName());

        PDFService pdfService = new PDFService();

        pdfService.downloadPDF(recipe);

        System.out.println("Se descargo la receta " + recipe.getName());

    }

    @FXML
    private void onAddRecipe(Event event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/addRecipeView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.centerOnScreen();
            newStage.setTitle("Agregar Receta");

            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.show();

            System.out.println("onAddRecipe");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onGoBack(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));
            Scene scene = new Scene(loader.load());

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