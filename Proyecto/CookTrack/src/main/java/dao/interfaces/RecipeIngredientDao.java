package dao.interfaces;


import dao.DAO;
import models.RecipeIngredient;
import models.RecipeIngredientId;
import models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipeIngredientDao extends DAO<RecipeIngredient, RecipeIngredientId> {
    List<RecipeIngredient> findByRecipeId(int recipeId);
    List<RecipeIngredient> findByIngredientId(int ingredientId);

    List<RecipeIngredient> getRecipeIngredientsByRangeOfDate(User user, LocalDateTime start, LocalDateTime end);
}