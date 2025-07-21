package controllers.recipesViewControllers;

import infrastructure.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Recipe;
import models.User;
import services.BookRecipeService;

import java.util.List;

public class AddRecipeToCalendarController {

    @FXML
    private ListView<Recipe> recipesListView;

    private BookRecipeService bookRecipeService;
    private Recipe selectedRecipe = null;

    @FXML
    public void initialize() {
        this.bookRecipeService = new BookRecipeService();
        User currentUser = SessionManager.getInstance().getCurrentUser();

        if (currentUser != null) {
            List<Recipe> userRecipes = bookRecipeService.getRecipes(currentUser);
            ObservableList<Recipe> observableRecipes = FXCollections.observableArrayList(userRecipes);
            recipesListView.setItems(observableRecipes);
        }
    }

    @FXML
    private void addRecipe() {
        selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
        closeWindow();
    }

    @FXML
    private void cancel() {
        selectedRecipe = null;
        closeWindow();
    }

    public Recipe getSelectedRecipe() {
        return selectedRecipe;
    }

    private void closeWindow() {
        Stage stage = (Stage) recipesListView.getScene().getWindow();
        stage.close();
    }
}