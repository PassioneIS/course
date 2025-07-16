package dao.interfaces;

import dao.DAO;
import models.Ingredient;

public interface IngredientDao extends DAO<Ingredient,Integer> {
   Ingredient findByName(String name);
}
