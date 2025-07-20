package dao.interfaces;

import dao.DAO;
import models.RecipeBookRecipe;

import java.util.List;

public interface RecipeBookRecipeDao extends DAO<RecipeBookRecipe, Integer> {
    List<RecipeBookRecipe> findByRecipeBookId(int recipeBookId);

    List<RecipeBookRecipe> findFavoritesByRecipeBookId(int recipeBookId);
}