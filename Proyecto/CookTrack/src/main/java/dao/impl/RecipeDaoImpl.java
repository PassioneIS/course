package dao.impl;

import dao.interfaces.RecipeDao;
import models.Recipe;
import java.util.List;

public class RecipeDaoImpl extends DaoImpl<Recipe, Integer> implements RecipeDao {

    public RecipeDaoImpl() {
        super(Recipe.class);
    }

    @Override
    public List<Recipe> findByName(String name) {
        return List.of();
    }

    @Override
    public List<Recipe> findByPreptimeLessThan(int minutes) {
        return List.of();
    }
}
