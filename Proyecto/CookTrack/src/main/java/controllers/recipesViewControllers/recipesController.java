package controllers.recipesViewControllers;

import infrastructure.SessionManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Recipe;
import services.BookRecipeService;
import services.PDFService;

import java.io.IOException;
import java.util.List;

public class recipesController {

    @FXML
    private VBox recipesVbox;
    @FXML
    private Button btnAddRecipe;
    @FXML
    private Button btnGoBack;

    private boolean selectionMode = false;
    private Recipe selectedRecipe = null;
    private final BookRecipeService bookRecipeService;

    public recipesController() {
        this.bookRecipeService = new BookRecipeService();
    }

    @FXML
    public void initialize() {
        listRecipes();
        btnAddRecipe.setOnAction(this::onAddRecipe);
        btnGoBack.setOnAction(this::onGoBack);
    }

    public void setSelectionMode(boolean mode) {
        this.selectionMode = mode;
        btnAddRecipe.setVisible(!mode);
        btnGoBack.setVisible(!mode);
        listRecipes();
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    private void listRecipes() {
        //List<Recipe> recipesList = bookRecipeService.getAllRecipes();
        List<Recipe> recipesList = bookRecipeService.getRecipes(SessionManager.getInstance().getCurrentUser());

        recipesVbox.getChildren().clear();
        for (Recipe recipe : recipesList) {
            HBox hbox = new HBox(10);
            hbox.setStyle("-fx-background-color: #ffffff;" + "-fx-padding: 15;" + "-fx-background-radius: 8;" + "-fx-border-radius: 8;" + "-fx-border-color: #dddddd;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 4, 0, 0, 2);");

            Label nameLabel = new Label("Nombre: " + recipe.getName());
            nameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");
            Label prepTimeLabel = new Label("Tiempo de preparación: " + recipe.getPreptime());
            prepTimeLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 13;");

            HBox buttonsBox = new HBox(5);
            if (selectionMode) {
                Button selectButton = new Button("Seleccionar");
                selectButton.setOnAction(e -> handleRecipeAction(recipe, e));
            } else {
                Button seeMoreButton = new Button("Ver más");
                seeMoreButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;");
                seeMoreButton.setOnAction(e -> onSeeMore(recipe, e));

                Button modifyButton = new Button("Modificar");
                modifyButton.setOnAction(e -> onModifyRecipe(recipe, e));

                Button exportButton = new Button("Descargar PDF");
                exportButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-weight: bold;");
                exportButton.setOnAction(e -> onDownloadPDF(recipe, e));

                buttonsBox.getChildren().addAll(seeMoreButton, modifyButton, exportButton);
            }

            hbox.getChildren().addAll(nameLabel, prepTimeLabel, buttonsBox);
            recipesVbox.getChildren().add(hbox);
        }
    }

    private void handleRecipeAction(Recipe recipe, Event event) {
        if (selectionMode) {
            this.selectedRecipe = recipe;
            closeWindow(event);
        }
    }

    private void onModifyRecipe(Recipe recipe, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/addRecipeView.fxml"));
            Scene scene = new Scene(loader.load());
            addRecipeController controller = loader.getController();
            controller.setRecipeToEdit(recipe);

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.centerOnScreen();
            newStage.setTitle("Receta: " + recipe.getName());
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSeeMore(Recipe recipe, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/seeRecipeView.fxml"));
            Scene scene = new Scene(loader.load());
            seeRecipeController controller = loader.getController();
            controller.viewRecipe(recipe);

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.centerOnScreen();
            newStage.setTitle("Receta: " + recipe.getName());
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onDownloadPDF(Recipe recipe, Event event) {
        PDFService pdfService = new PDFService();
        pdfService.downloadPDF(recipe);
    }

    private void onAddRecipe(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/addRecipeView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.centerOnScreen();
            newStage.setTitle("Agregar Receta");
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
            listRecipes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onGoBack(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}