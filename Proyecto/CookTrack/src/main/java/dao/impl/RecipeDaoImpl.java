package dao.impl;

import dao.interfaces.RecipeDao;
import infrastructure.DataBaseConnection;
import models.Recipe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RecipeDaoImpl implements RecipeDao {
    static Session session = DataBaseConnection.getSession();

    @Override
    public void save(Recipe recipe) {
        Transaction transaction = session.beginTransaction();
        session.persist(recipe);
        transaction.commit();
    }

    @Override
    public Recipe findById(Integer integer) {
        Query<Recipe> query = session.createQuery("FROM Recipe WHERE id = :integer", Recipe.class);
        query.setParameter("id", integer);
        return query.uniqueResult();
    }

    @Override
    public List<Recipe> findAll(){
        Query<Recipe> query = session.createQuery("FROM Recipe", Recipe.class);
        return query.list();
    }

    public void update(Recipe entity){

    }
    public void delete(Recipe entity){

    }
}