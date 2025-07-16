package dao.impl;

import dao.interfaces.RecipeStepDao;
import models.RecipeStep;
import java.util.List;

public class RecipeStepDaoImpl extends DaoImpl<RecipeStep, Integer> implements RecipeStepDao {

    public RecipeStepDaoImpl() {
        super(RecipeStep.class);
    }

    @Override
    public List<RecipeStep> findByRecipeIdOrderByPosition(int recipeId) {
        return List.of();
    }
}
