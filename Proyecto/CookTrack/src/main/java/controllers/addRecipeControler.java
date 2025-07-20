package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.event.Event;

import java.util.ArrayList;
import java.util.List;

import services.IngredientService;
import models.Ingredient;

public class addRecipeControler {

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

    private final List<TextField> recipeSteps = new ArrayList<>();

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
            onVerReceta(event);
        });

    }

    @FXML
    private void onListRecipe(ComboBox combobox) {

        List<Ingredient> ingredients = ingredientService.getIngredients();

        if(ingredients.size() > 0){
            System.out.println("La lista de ingredientes NO esta vacia! :D");

            combobox.getItems().addAll(ingredients);
        }
        else{
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
        recipeSteps.add(newStep);

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
    private void onVerReceta(Event event) {

        List<Ingredient> ingredients = ingredientService.getIngredients();

        String recipeName = txtRecipeName.getText();
        System.out.println("Nombre de la receta:" + recipeName );

        Integer recipeTime = Integer.valueOf( txtRecipeTime.getText() );
        System.out.println("Tiempo de preparacion:" + recipeTime );

        List<Integer> listIngredientsId = new ArrayList<>();
        List<Integer> listIngredientsAmount = new ArrayList<>();

        System.out.println("Ingredientes");

        for(int i=0; i < ingredientList.size(); i++){

            String ingredient = ingredientList.get(i).getValue().toString();
            System.out.println("    Ingrediente:" + ingredient);

            Integer ingredientAmount = Integer.valueOf(ingredientAmountList.get(i).getText());
            listIngredientsAmount.add(ingredientAmount);
            System.out.println("        Cantidad:" + ingredientAmount);

            int nonValidId = -1;
            int ingredientId = nonValidId;

            for(int j=0; j < ingredients.size(); j++){
                if(ingredients.get(j).toString() == ingredient){
                    ingredientId = ingredients.get(j).getId();
                }
            }

            listIngredientsId.add(ingredientId);
            System.out.println("        Ingrediente ID:" + ingredientId );

        }

        System.out.println("Pasos");

        for(int i=0; i < recipeSteps.size(); i++){
            System.out.println("    Paso position:" + i);

            String step = recipeSteps.get(i).getText();
            System.out.println("    Paso:" + step);
        }

        System.out.println("Tags");

        for(int i=0; i < tagsList.size(); i++){

            String tag = tagsList.get(i).getText();
            System.out.println("    Tag:" + tag);
        }

        //Creacion de las entidades a subir

        //Recipe recipe = new recipe;
        //recipe.setName(txtRecipeName.getText());

        //recipe.setPreptime(  )

    }

}