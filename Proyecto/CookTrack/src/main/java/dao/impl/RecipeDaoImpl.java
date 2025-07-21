package dao.impl;

import dao.interfaces.RecipeDao;
import infrastructure.DataBaseConnection;
import models.Recipe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RecipeDaoImpl extends DaoImpl<Recipe, Integer> implements RecipeDao {

    public RecipeDaoImpl() {
        super(Recipe.class);
    }

    @Override
    public void save(Recipe recipe) {
        Transaction transaction = null;
        try (Session session = DataBaseConnection.getSession()) {
            transaction = session.beginTransaction();
            session.persist(recipe);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Recipe> findByName(String name) {
        try (Session session = DataBaseConnection.getSession()) {
            String hql = "FROM Recipe r WHERE r.name = :recipeName";
            Query<Recipe> query = session.createQuery(hql, Recipe.class);
            query.setParameter("recipeName", name);
            return query.list();
        }
    }

    @Override
    public Recipe findById(Integer id) {
        try (Session session = DataBaseConnection.getSession()) {
            return session.find(Recipe.class, id);
        }
    }

    @Override
    public List<Recipe> findByPreptimeLessThan(int minutes) {
        return List.of();
    }

    @Override
    public List<Recipe> findAll() {
        try (Session session = DataBaseConnection.getSession()) {
            return session.createQuery("FROM Recipe", Recipe.class).list();
        }
    }
}