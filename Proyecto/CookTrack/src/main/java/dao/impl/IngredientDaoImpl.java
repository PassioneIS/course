package dao.impl;

import dao.interfaces.IngredientDao;
import infrastructure.DataBaseConnection;
import models.Ingredient;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class IngredientDaoImpl implements IngredientDao {

    static Session session = DataBaseConnection.getSession();

    @Override
    public void save(Ingredient ingredient){

    }

    @Override
    public Ingredient findById(Integer integer){
        return null;
    }

    @Override
    public List<Ingredient> findAll(){
        Query<Ingredient> query = session.createQuery("FROM Ingredient", Ingredient.class);
        return query.list();
    }

    public void update(Ingredient entity){

    }
    public void delete(Ingredient entity){

    }
}
