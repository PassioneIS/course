package dao.interfaces;

import dao.DAO;
import models.RecipeBookRecipe;
import models.RecipeBook;
import models.Recipe;

import java.util.List;

public interface RecipeBookRecipeDao extends DAO<RecipeBookRecipe, Integer> {
    List<RecipeBookRecipe> findByRecipeBookId(int recipeBookId);

    RecipeBookRecipe findByRecipeId(int recipeId);

    List<RecipeBookRecipe> findFavoritesByRecipeBookId(int recipeBookId);

    RecipeBookRecipe findRecipeBookRecipeByRecipe(Recipe recipe);
    RecipeBookRecipe createRecipeBookRecipe(RecipeBook recipeBook, Recipe recipe, List<String> nametag);
    void save(RecipeBookRecipe recipeBookRecipe);
}