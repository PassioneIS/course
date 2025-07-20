package dao.interfaces;

import dao.DAO;
import models.Recipe;

import java.util.List;

public interface RecipeDao extends DAO<Recipe, Integer> {
    void save(Recipe recipe);
    List<Recipe> findByName(String name);
    List<Recipe> findByPreptimeLessThan(int minutes);
    Recipe findById(Integer integer);
    List<Recipe> findAll();
}