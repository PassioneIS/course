package dao.impl;

import dao.interfaces.RecipeIngredientDao;
import models.RecipeIngredient;
import models.RecipeIngredientId;
import java.util.List;

public class RecipeIngredientDaoImpl extends DaoImpl<RecipeIngredient, RecipeIngredientId> implements RecipeIngredientDao {

    public RecipeIngredientDaoImpl() {
        super(RecipeIngredient.class);
    }

    @Override
    public List<RecipeIngredient> findByRecipeId(int recipeId) {
        return List.of();
    }

    @Override
    public List<RecipeIngredient> findByIngredientId(int ingredientId) {
        return List.of();
    }
}
