package dao.impl;

import dao.interfaces.RecipeBookRecipeDao;
import infrastructure.DataBaseConnection;
import models.Recipe;
import models.RecipeBook;
import models.RecipeBookRecipe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RecipeBookRecipeDaoImpl extends DaoImpl<RecipeBookRecipe, Integer> implements RecipeBookRecipeDao {

    public RecipeBookRecipeDaoImpl() {
        super(RecipeBookRecipe.class);
    }

    @Override
    public List<RecipeBookRecipe> findByRecipeBookId(int recipeBookId) {
        // Lógica futura...
        return List.of();
    }

    @Override
    public List<RecipeBookRecipe> findFavoritesByRecipeBookId(int recipeBookId) {
        // Lógica futura...
        return List.of();
    }

    @Override
    public RecipeBookRecipe findByRecipeId(int recipeId) {
        try (Session session = DataBaseConnection.getSession()) {
            String hql = "FROM RecipeBookRecipe rbr WHERE rbr.recipe.id = :recipeId";
            Query<RecipeBookRecipe> query = session.createQuery(hql, RecipeBookRecipe.class);
            query.setParameter("recipeId", recipeId);
            return query.uniqueResult();
        }
    }

    public RecipeBookRecipe findRecipeBookRecipeByRecipe(Recipe recipe){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Query<RecipeBookRecipe> query = session.createQuery("FROM RecipeBookRecipe WHERE recipe = :recipe", RecipeBookRecipe.class);
            query.setParameter("recipe", recipe);
            return query.uniqueResult();
        }
    }

    public RecipeBookRecipe createRecipeBookRecipe(RecipeBook recipeBook, Recipe recipe, List<String> nametag){
        RecipeBookRecipe recipeBookRecipe = new RecipeBookRecipe();
        recipeBookRecipe.setRecipeBook(recipeBook);
        recipeBookRecipe.setRecipe(recipe);
        recipeBookRecipe.setFavorite(false);
        recipeBookRecipe.setPublic(false);
        recipeBookRecipe.setNametag(nametag);
        return recipeBookRecipe;
    }

    @Override
    public void save(RecipeBookRecipe recipeBookRecipe){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(recipeBookRecipe);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RecipeBookRecipe recipeBookRecipe){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(recipeBookRecipe);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}