package dao.interfaces;

import dao.DAO;
import models.Recipe;

import java.util.List;

public interface RecipeDao extends DAO<Recipe, Integer> {
    List<Recipe> findByName(String name);
    List<Recipe> findByPreptimeLessThan(int minutes);
}