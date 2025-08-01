package dao.interfaces;

import dao.DAO;
import models.Ingredient;

import java.util.List;

public interface IngredientDao extends DAO<Ingredient, Integer> {
    List<Ingredient> findAll();
    Ingredient findByName(String name);
}