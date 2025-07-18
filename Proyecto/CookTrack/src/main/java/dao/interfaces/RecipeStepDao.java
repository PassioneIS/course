package dao.interfaces;

import dao.DAO;
import models.RecipeStep;
import models.Recipe;

import java.util.List;

public interface RecipeStepDao extends DAO<RecipeStep, Integer> {

    RecipeStep createRecipeStep(Recipe recipe, short position, String text);
    void Save(RecipeStep recipeStep);
    List<RecipeStep> findByRecipeIdOrderByPosition(int recipeId);

}