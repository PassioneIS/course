package dao.interfaces;

import dao.DAO;
import models.RecipeStep;

import java.util.List;

public interface RecipeStepDao extends DAO<RecipeStep, Integer> {
    List<RecipeStep> findByRecipeIdOrderByPosition(int recipeId);
}