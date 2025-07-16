package dao.interfaces;


import dao.DAO;
import models.RecipeIngredient;
import models.RecipeIngredientId;

import java.util.List;

public interface RecipeIngredientDao extends DAO<RecipeIngredient, RecipeIngredientId> {
    List<RecipeIngredient> findByRecipeId(int recipeId);
    List<RecipeIngredient> findByIngredientId(int ingredientId);
}