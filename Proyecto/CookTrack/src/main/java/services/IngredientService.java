package services;

import dao.interfaces.IngredientDao;
import dao.impl.IngredientDaoImpl;
import models.Ingredient;

import java.util.List;

public class IngredientService {

    static IngredientDao ingredientDao = new IngredientDaoImpl();

    public Ingredient getIngredientByName(String name){
        Ingredient ingredient = ingredientDao.findByName(name);
        return ingredient;
    }

    public List<Ingredient> getIngredients(){
        List<Ingredient> ingredients = ingredientDao.findAll();
        return ingredients;
    }

}