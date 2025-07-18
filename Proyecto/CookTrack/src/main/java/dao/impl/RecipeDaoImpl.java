package dao.impl;

import dao.interfaces.RecipeDao;
import infrastructure.DataBaseConnection;
import models.Recipe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.ArrayList;

public class RecipeDaoImpl extends DaoImpl<Recipe, Integer> implements RecipeDao {

    public RecipeDaoImpl() {
        super(Recipe.class);
    }

    //static Session session = DataBaseConnection.getSession();

    public Recipe createRecipe(String name, Integer prepTime){
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPreptime(prepTime);

        return recipe;
    }

    @Override
    public void save(Recipe recipe) {
        try (Session session = DataBaseConnection.getSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Persistiendo: " + recipe);

            session.persist(recipe);
            transaction.commit();
        }
        catch (Exception e) {
            System.err.println("Error al guardar RecipeIngredient:");
            e.printStackTrace();
        }
    }

    @Override
    public List<Recipe> findByName(String name) {
        return List.of();
    }


    @Override
    public Recipe findById(Integer integer) {
        try (Session session = DataBaseConnection.getSession()) {
            Query<Recipe> query = session.createQuery("FROM Recipe WHERE id = :integer", Recipe.class);
            query.setParameter("id", integer);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Recipe> findByPreptimeLessThan(int minutes) {
        return List.of();
    }

    @Override
    public List<Recipe> findAll(){
        try (Session session = DataBaseConnection.getSession()) {
            Query<Recipe> query = session.createQuery("FROM Recipe", Recipe.class);
            return query.list();
        }
    }


    public void update(Recipe entity){

    }
    public void delete(Recipe entity){

    }
}