package dao.interfaces;

import dao.DAO;
import models.Recipe;

import java.util.List;

public interface RecipeDao extends DAO<Recipe,Integer> {
    void save(Recipe recipe);
    Recipe findById(Integer integer);
    List<Recipe> findAll();
}
