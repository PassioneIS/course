package services;

import dao.impl.IngredientDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.impl.RecipeStepDaoImpl;
import dao.interfaces.IngredientDao;
import dao.interfaces.RecipeIngredientDao;
import dao.interfaces.RecipeStepDao;
import models.Ingredient;
import models.Recipe;
import models.RecipeIngredient;
import models.RecipeStep;

import java.util.List;

public class IngredientService {

    private final IngredientDao ingredientDao = new IngredientDaoImpl();
    private final RecipeIngredientDao recipeIngredientDao = new RecipeIngredientDaoImpl();
    private final RecipeStepDao recipeStepDao = new RecipeStepDaoImpl();

    public Ingredient getIngredientById(Integer ingredientId) {
        return ingredientDao.findById(ingredientId);
    }

    public Ingredient getIngredientByName(String name) {
        return ingredientDao.findByName(name);
        /*if (ingredients != null && !ingredients.isEmpty()) {
            return ingredients.get(0);
        }
        return null;*/
    }

    public List<Ingredient> getIngredients() {
        return ingredientDao.findAll();
    }

    public List<RecipeIngredient> getRecipeIngredients(Recipe recipe) {
        return recipeIngredientDao.findRecipeIngredientByRecipeId(recipe);
    }

    public List<RecipeStep> getRecipeStepbyRecipe(Recipe recipe) {
        return recipeStepDao.findByRecipeIdOrderByPosition(recipe);
    }
}