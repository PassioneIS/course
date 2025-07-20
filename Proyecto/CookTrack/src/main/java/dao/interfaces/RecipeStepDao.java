package dao.interfaces;

import dao.DAO;
import models.Recipe;
import models.RecipeStep;

import java.util.List;

public interface RecipeStepDao extends DAO<RecipeStep, Integer> {

    RecipeStep createRecipeStep(Recipe recipe, short position, String text);

    void save(RecipeStep recipeStep);

    List<RecipeStep> findByRecipeIdOrderByPosition(Recipe recipe);

}