package dao.impl;

import dao.interfaces.IngredientDao;
import infrastructure.DataBaseConnection;
import models.Ingredient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class IngredientDaoImpl extends DaoImpl<Ingredient,Integer> implements IngredientDao{

    public IngredientDaoImpl() {
        super(Ingredient.class);
    }

    //static Session session = DataBaseConnection.getSession();


    @Override
    public void save(Ingredient ingredient){

    }

    @Override
    public Ingredient findById(Integer integer){
        return null;
    }

    @Override
    public Ingredient findByName(String name) {
        try (Session session = DataBaseConnection.getSession()){
            Query<Ingredient> query = session.createQuery("FROM Ingredient WHERE name = :name", Ingredient.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Ingredient> findAll(){
        try (Session session = DataBaseConnection.getSession()) {
            Query<Ingredient> query = session.createQuery("FROM Ingredient", Ingredient.class);
            return query.list();
        }
    }

    public void update(Ingredient entity){

    }
    public void delete(Ingredient entity){

    }
}
