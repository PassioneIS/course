package services;

import dao.impl.RecipeDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.impl.RecipeStepDaoImpl;
import models.*;

import java.util.List;

public class BookRecipeService {

    public List<Recipe> getRecipes(User user) {
        return null;
    }

    public void createRecipe(String name, Integer prepTime, List<Ingredient> ingredientsList, List<Short> listIngredientsAmount, List<String> stepsList) {

        RecipeDaoImpl recipeDao = new RecipeDaoImpl();

        Recipe recipe = recipeDao.createRecipe(name, prepTime);

        recipeDao.save(recipe);

        for (int i = 0; i < ingredientsList.size(); i++) {

            RecipeIngredientDaoImpl recipeIngredientDao = new RecipeIngredientDaoImpl();

            short amount = listIngredientsAmount.get(i);
            Ingredient ingredientOfList = ingredientsList.get(i);

            RecipeIngredient recipeIngredient = recipeIngredientDao.createRecipeIngredient(recipe, ingredientOfList, amount);
            recipeIngredientDao.save(recipeIngredient);

        }

        RecipeStepDaoImpl recipeStepDao = new RecipeStepDaoImpl();

        for (short i = 0; i < stepsList.size(); i++) {
            short position = (short) (i + 1);
            RecipeStep recipeStep = recipeStepDao.createRecipeStep(recipe, position, stepsList.get(i));
            recipeStepDao.save(recipeStep);

        }

        //System.out.println("Creacion de la receta:"+ name + " prepTime: " + prepTime + "ingrediets:" + ingredientsList + " ,ingredients amount " + listIngredientsAmount + " , pasos:" + stepsList);

    }

    public List<Recipe> getAllRecipes() {

        RecipeDaoImpl recipeDao = new RecipeDaoImpl();
        List<Recipe> recipesList = recipeDao.findAll();
        return recipesList;
    }

    public void addRecipe(Recipe recipe) {

    }

    public void exportRecipePDF(Recipe recipe) {

    }

    public void updateRecipe(Recipe recipe) {

    }

    public void deleteRecipe(Recipe recipe) {

    }

    public void addLabel(Recipe recipe) {

    }

    public void markAsFavorite(Recipe recipe) {

    }

}
