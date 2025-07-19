package services;

import dao.interfaces.IngredientDao;
import dao.impl.IngredientDaoImpl;
import models.Ingredient;

import models.Recipe;
import models.RecipeIngredient;
import dao.interfaces.RecipeIngredientDao;
import dao.impl.RecipeIngredientDaoImpl;

import models.RecipeStep;
import dao.interfaces.RecipeStepDao;
import dao.impl.RecipeStepDaoImpl;

import java.util.List;

public class IngredientService {

    static IngredientDao ingredientDao = new IngredientDaoImpl();

    static RecipeIngredientDao recipeIngredientDao = new RecipeIngredientDaoImpl();

    static RecipeStepDao recipeStepDao = new RecipeStepDaoImpl();

    public Ingredient getIngredientById(Integer ingredientId){
        Ingredient ingredient = ingredientDao.findById(ingredientId);
        return ingredient;
    }

    public Ingredient getIngredientByName(String name){
        Ingredient ingredient = ingredientDao.findByName(name);
        return ingredient;
    }

    public List<Ingredient> getIngredients(){
        List<Ingredient> ingredients = ingredientDao.findAll();
        return ingredients;
    }

    public List<RecipeIngredient> getRecipeIngredients(Recipe recipe){
        List<RecipeIngredient> recipeIngredients = recipeIngredientDao.findRecipeIngredientByRecipeId( recipe );
        return recipeIngredients;
    }

    public List<RecipeStep> getRecipeStepbyRecipe(Recipe recipe){
        List<RecipeStep> recipeSteps = recipeStepDao.findByRecipeIdOrderByPosition(recipe);
        return recipeSteps;
    }


}