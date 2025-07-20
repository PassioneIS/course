package dao.impl;

import dao.interfaces.RecipeBookRecipeDao;
import models.RecipeBookRecipe;

import java.util.List;

public class RecipeBookRecipeDaoImpl extends DaoImpl<RecipeBookRecipe, Integer> implements RecipeBookRecipeDao {

    public RecipeBookRecipeDaoImpl() {
        super(RecipeBookRecipe.class);
    }

    @Override
    public List<RecipeBookRecipe> findByRecipeBookId(int recipeBookId) {
        return List.of();
    }

    @Override
    public List<RecipeBookRecipe> findFavoritesByRecipeBookId(int recipeBookId) {
        return List.of();
    }
}
