package dao.impl;

import dao.interfaces.RecipeBookRecipeDao;
import models.RecipeBookRecipe;

import java.util.List;

import infrastructure.SessionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import infrastructure.DataBaseConnection;

import models.Recipe;
import models.RecipeBook;

public class RecipeBookRecipeDaoImpl extends DaoImpl<RecipeBookRecipe,Integer> implements RecipeBookRecipeDao {

    public RecipeBookRecipeDaoImpl() {
        super(RecipeBookRecipe.class);
    }

    @Override
    public List<RecipeBookRecipe> findByRecipeBookId(int recipeBookId) {
        return List.of();
    }

    @Override
    public List<RecipeBookRecipe> findFavoritesByRecipeBookId(int recipeBookId) {
        return List.of();
    }

    @Override
    public RecipeBookRecipe findRecipeBookRecipeByRecipe(Recipe recipe){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Query<RecipeBookRecipe> query = session.createQuery("FROM RecipeBookRecipe WHERE recipe = :recipe", RecipeBookRecipe.class);
            query.setParameter("recipe", recipe);
            return query.uniqueResult();
        }
    }

    @Override
    public RecipeBookRecipe createRecipeBookRecipe(RecipeBook recipeBook, Recipe recipe, List<String> nametag){
        RecipeBookRecipe recipeBookRecipe = new RecipeBookRecipe();
        recipeBookRecipe.setRecipeBook(recipeBook);
        recipeBookRecipe.setRecipe(recipe);
        recipeBookRecipe.setFavorite(false);
        recipeBookRecipe.setPublic(false);

        for(String tag : nametag){
            recipeBookRecipe.addNametag(tag);
        }
        return recipeBookRecipe;
    }

    @Override
    public void save(RecipeBookRecipe recipeBookRecipe){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Persistiendo: " + recipeBookRecipe);

            session.persist(recipeBookRecipe);
            transaction.commit();

            System.out.println("Se guardo la recipeBookrecipe:" + recipeBookRecipe);
        }
        catch (Exception e) {
            System.err.println("Error al guardar la recipeBookrecipe:" + recipeBookRecipe);
            e.printStackTrace();
        }
    }

    @Override
    public void update(RecipeBookRecipe recipeBookRecipe){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Persistiendo: " + recipeBookRecipe);

            session.merge(recipeBookRecipe);
            transaction.commit();

            System.out.println("Se guardo la recipeBookrecipe:" + recipeBookRecipe);
        }
        catch (Exception e) {
            System.err.println("Error al guardar la recipeBookrecipe:" + recipeBookRecipe);
            e.printStackTrace();
        }
    }

}
