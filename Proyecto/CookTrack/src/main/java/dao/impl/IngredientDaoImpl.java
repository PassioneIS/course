package dao.impl;

import dao.interfaces.IngredientDao;
import infrastructure.DataBaseConnection;
import models.Ingredient;
import models.RecipeIngredient;
import org.hibernate.Session;
import java.time.LocalDate;
import java.util.List;

public class IngredientDaoImpl extends DaoImpl<Ingredient,Integer> implements IngredientDao{

    public IngredientDaoImpl() {
        super(Ingredient.class);
    }

    @Override
    public Ingredient findByName(String name) {
        return null;
    }
}
