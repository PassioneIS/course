package dao.interfaces;


import dao.DAO;
import models.*;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipeIngredientDao extends DAO<RecipeIngredient, RecipeIngredientId> {

    RecipeIngredient createRecipeIngredient(Recipe recipe, Ingredient ingredient, short Amount);

    void save(RecipeIngredient recipeIngredient);

    List<RecipeIngredient> findByRecipeId(int recipeId);

    List<RecipeIngredient> findByIngredientId(int ingredientId);

    List<RecipeIngredient> findRecipeIngredientByRecipeId(Recipe recipe);

    List<RecipeIngredient> getRecipeIngredientsByRangeOfDate(User user, LocalDateTime start, LocalDateTime end);

}