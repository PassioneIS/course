package controllers.recipesViewControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import services.BookRecipeService;
import services.IngredientService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class addRecipeController {

    @FXML
    private Button btnGoBack;
    @FXML
    private TextField txtRecipeName;
    @FXML
    private TextField txtRecipeTime;
    @FXML
    private VBox recipeContainer;
    @FXML
    private Button btnAddIngredient;
    @FXML
    private VBox stepContainer;
    @FXML
    private Button btnAddStep;
    @FXML
    private VBox tagContainer;
    @FXML
    private Button btnAddTag;
    @FXML
    private Button btnCreateRecipe;

    private final List<ComboBox<String>> ingredientComboBoxes = new ArrayList<>();
    private final List<TextField> ingredientAmountFields = new ArrayList<>();
    private final List<TextField> stepFields = new ArrayList<>();
    private final List<TextField> tagFields = new ArrayList<>();
    private final IngredientService ingredientService = new IngredientService();
    private final BookRecipeService bookRecipeService = new BookRecipeService();
    private Recipe recipeToEdit = null;

    @FXML
    public void initialize() {
        btnAddIngredient.setOnAction(this::onAddIngredient);
        btnAddStep.setOnAction(this::onAddStep);
        btnAddTag.setOnAction(this::onAddTag);
        btnGoBack.setOnAction(this::onGoBack);
        btnCreateRecipe.setOnAction(event -> handleSave());
    }

    public void setRecipeToEdit(Recipe recipe) {
        this.recipeToEdit = recipe;
        btnCreateRecipe.setText("Guardar Cambios");
        populateForm();
    }

    private void populateForm() {
        if (recipeToEdit == null) return;
        txtRecipeName.setText(recipeToEdit.getName());
        txtRecipeTime.setText(recipeToEdit.getPreptime().toString());
    }

    private void onAddIngredient(Event event) {
        addIngredientFields(null, "");
    }

    private void addIngredientFields(Ingredient ingredient, String amount) {
        ComboBox<String> newIngredientCombo = new ComboBox<>();
        newIngredientCombo.setPromptText("Seleccione ingrediente");

        List<Ingredient> ingredientList = ingredientService.getIngredients();
        List<String> ingredientsNames = new ArrayList<>();

        for (Ingredient ingredientInList : ingredientList) {
            ingredientsNames.add(ingredientInList.getName());
        }

        newIngredientCombo.getItems().addAll(ingredientsNames);
        if (ingredient != null) {
            newIngredientCombo.setValue(ingredient.getName());
        }
        TextField newAmountField = new TextField(amount);
        newAmountField.setPromptText("Cantidad");
        recipeContainer.getChildren().addAll(newIngredientCombo, newAmountField);
        ingredientComboBoxes.add(newIngredientCombo);
        ingredientAmountFields.add(newAmountField);
    }

    private void onAddStep(Event event) {
        addStepField("");
    }

    private void addStepField(String text) {
        TextField newStepField = new TextField(text);
        newStepField.setPromptText("Describa el paso");
        stepContainer.getChildren().add(newStepField);
        stepFields.add(newStepField);
    }

    private void onAddTag(Event event) {
        addTagField("");
    }

    private void addTagField(String text) {
        TextField newTagField = new TextField(text);
        newTagField.setPromptText("Ingrese la etiqueta");
        tagContainer.getChildren().add(newTagField);
        tagFields.add(newTagField);
    }

    private void handleSave() {
        // Aquí iría la lógica de validación de tu compañero, si quieres la añadimos después.
        String name = txtRecipeName.getText();
        Integer prepTime = Integer.parseInt(txtRecipeTime.getText());
        List<String> ingredientsName = ingredientComboBoxes.stream().map(ComboBox::getValue).collect(Collectors.toList());

        List<Ingredient> ingredients = new ArrayList<>();

        for (String ingredientNameInList :  ingredientsName) {
            ingredients.add(ingredientService.getIngredientByName(ingredientNameInList));
        }

        List<Short> amounts = ingredientAmountFields.stream().map(tf -> Short.parseShort(tf.getText())).collect(Collectors.toList());
        List<String> steps = stepFields.stream().map(TextField::getText).collect(Collectors.toList());
        List<String> tags = tagFields.stream().map(TextField::getText).collect(Collectors.toList());

        if (recipeToEdit != null) {
            bookRecipeService.updateRecipe(recipeToEdit, name, prepTime, ingredients, amounts, steps, tags);
        } else {
            bookRecipeService.createRecipe(name, prepTime, ingredients, amounts, steps, tags);
        }
        onGoBack(null);
    }

    private void onGoBack(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipesViews/recipesView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnGoBack.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}