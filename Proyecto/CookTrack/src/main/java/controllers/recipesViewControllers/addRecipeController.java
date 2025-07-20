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
import models.Ingredient;
import services.BookRecipeService;
import services.IngredientService;

import java.util.ArrayList;
import java.util.List;

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

    private final List<ComboBox> ingredientList = new ArrayList<>();
    private final List<TextField> ingredientAmountList = new ArrayList<>();

    @FXML
    private VBox stepContainer;

    @FXML
    private Button btnAddStep;

    private final List<TextField> recipeStepsList = new ArrayList<>();

    @FXML
    private VBox tagContainer;

    @FXML
    private Button btnAddTag;

    private final List<TextField> tagsList = new ArrayList<>();

    private final IngredientService ingredientService = new IngredientService();

    @FXML
    private Button btnCreateRecipe;

    @FXML
    public void initialize() {

        btnGoBack.setOnAction(event -> {
            onGoBack(event);
        });

        btnAddIngredient.setOnAction(event -> {
            onAddIngredient(event);
        });

        btnAddStep.setOnAction(event -> {
            onAddStep(event);
        });

        btnAddTag.setOnAction(event -> {
            onAddTag(event);
        });

        btnCreateRecipe.setOnAction(event -> {
            boolean validRecipe = onValidRecipe(event);

            if (validRecipe) {
                System.out.println("La receta es valida");
                onSaveRecipe(event);
            } else {
                System.out.println("La receta NO es valida :c");
            }
        });

    }

    @FXML
    private void onListRecipe(ComboBox combobox) {

        List<Ingredient> ingredients = ingredientService.getIngredients();

        if (ingredients.size() > 0) {
            System.out.println("La lista de ingredientes NO esta vacia! :D");

            combobox.getItems().addAll(ingredients);
        } else {
            System.out.println("La lista de ingredientes esta vacia! :c");
        }
    }

    @FXML
    private void onAddIngredient(Event event) {
        ComboBox newIngredient = new ComboBox();
        newIngredient.setPromptText("Seleccione un ingrediente");

        TextField newIngredientAmount = new TextField();
        newIngredientAmount.setPromptText("Ingrese la cantidad del ingrediente");

        recipeContainer.getChildren().add(newIngredient);
        recipeContainer.getChildren().add(newIngredientAmount);

        onListRecipe(newIngredient);

        ingredientList.add(newIngredient);
        ingredientAmountList.add(newIngredientAmount);


        System.out.println("Creado nuevo combobox!");
    }

    @FXML
    private void onAddStep(Event event) {
        TextField newStep = new TextField();
        newStep.setPromptText("Ingrese el nuevo paso");

        stepContainer.getChildren().add(newStep);
        recipeStepsList.add(newStep);

        System.out.println("Creado nuevo paso!");
    }

    @FXML
    private void onAddTag(Event event) {
        TextField newTag = new TextField();
        newTag.setPromptText("Ingrese la etiqueta");

        tagContainer.getChildren().add(newTag);
        tagsList.add(newTag);

        System.out.println("Creada nueva etiqueta!");
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


    @FXML
    private boolean onValidRecipe(Event event) {

        boolean validRecipe;

        try {

            boolean validRecipeName = txtRecipeName.getText().length() > 0;

            boolean validRecipeTime = Integer.valueOf(txtRecipeTime.getText()) >= 1;

            boolean validRecipeIngredients = ingredientAmountList.size() >= 1;
            for (int i = 0; i < ingredientList.size(); i++) {
                if (ingredientList.get(i).getValue().toString().length() <= 0) {
                    validRecipeIngredients = false;
                }
            }

            boolean validRecipeIngredientsAmount = ingredientAmountList.size() >= 1;
            for (int i = 0; i < ingredientAmountList.size(); i++) {
                if (Integer.valueOf(ingredientAmountList.get(i).getText()) <= 0) {
                    validRecipeIngredientsAmount = false;
                }
            }

            boolean validRecipeSteps = recipeStepsList.size() >= 1;
            for (int i = 0; i < recipeStepsList.size(); i++) {
                if (recipeStepsList.get(i).getText().length() <= 0) {
                    validRecipeIngredients = false;
                }
            }

            boolean validRecipeTags = tagsList.size() >= 1;
            for (int i = 0; i < tagsList.size(); i++) {
                if (tagsList.get(i).getText().length() <= 0) {
                    validRecipeTags = false;
                }
            }

            validRecipe = validRecipeName &&
                    validRecipeTime &&
                    validRecipeIngredients &&
                    validRecipeIngredientsAmount &&
                    validRecipeSteps &&
                    validRecipeTags;

            return validRecipe;

        } catch (java.lang.Exception ex) {
            System.err.println("Dilijenciar todos campos del formulario y hacerlo correctamente: " + ex);

            validRecipe = false;

            return validRecipe;

        }

    }

    @FXML
    private void onSaveRecipe(Event event) {

        String recipeName = txtRecipeName.getText();
        System.out.println("Nombre de la receta:" + recipeName);

        Integer recipeTime = Integer.valueOf(txtRecipeTime.getText());
        System.out.println("Tiempo de preparacion:" + recipeTime);

        System.out.println("Ingredientes");

        List<Ingredient> listIngredients = new ArrayList<>();
        List<Short> listIngredientsAmount = new ArrayList<>();

        for (int i = 0; i < ingredientList.size(); i++) {
            String ingredientName = ingredientList.get(i).getValue().toString();
            Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
            System.out.println("    Ingrediente:" + ingredient.toString());
            listIngredients.add(ingredient);

            short ingredientAmount = Short.valueOf(ingredientAmountList.get(i).getText());
            System.out.println("        Cantidad:" + ingredientAmount);
            listIngredientsAmount.add(ingredientAmount);

        }

        System.out.println("Pasos");
        List<String> listSteps = new ArrayList<>();

        for (int i = 0; i < recipeStepsList.size(); i++) {
            System.out.println("    Paso posición:" + i);

            String step = recipeStepsList.get(i).getText();
            System.out.println("    Paso:" + step);
            listSteps.add(step);
        }

        System.out.println("Tags");
        List<String> listTags = new ArrayList<>();

        for (int i = 0; i < tagsList.size(); i++) {

            String tag = tagsList.get(i).getText();
            System.out.println("    Tag:" + tag);
            listTags.add(tag);
        }

        //Creacion de los modelos

        BookRecipeService bookRecipeService = new BookRecipeService();

        bookRecipeService.createRecipe(recipeName, recipeTime, listIngredients, listIngredientsAmount, listSteps, listTags);

        //(String name, Integer prepTime, List<Ingredient> ingredientsList, List<Short> listIngredientsAmount, List<String> stepsList)

    }

}