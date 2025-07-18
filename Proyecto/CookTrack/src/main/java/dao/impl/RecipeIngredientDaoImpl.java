package dao.impl;

import dao.interfaces.RecipeIngredientDao;
import infrastructure.DataBaseConnection;
import infrastructure.SessionManager;
import models.RecipeIngredient;
import models.RecipeIngredientId;
import models.User;
import java.time.LocalDateTime;
import java.util.List;

import models.Recipe;
import models.Ingredient;

import services.BookRecipeService;

import infrastructure.DataBaseConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RecipeIngredientDaoImpl extends DaoImpl<RecipeIngredient, RecipeIngredientId> implements RecipeIngredientDao {

    public RecipeIngredientDaoImpl() {
        super(RecipeIngredient.class);
    }

    //static Session session = DataBaseConnection.getSession();

    @Override
    public RecipeIngredient createRecipeIngredient(Recipe recipe, Ingredient ingredient, short Amount){

        RecipeIngredient recipeIngredient = new RecipeIngredient();

        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setAmount(Amount);

        System.out.println("Creacion del ingrediente:" + recipe + " - " + ingredient +  " - " + Amount);

        return recipeIngredient;
    }


    @Override
    public void Save(RecipeIngredient recipeIngredient){
        try (Session session = DataBaseConnection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(recipeIngredient);
            transaction.commit();
        }
    }


    @Override
    public List<RecipeIngredient> findByRecipeId(int recipeId) {
        return List.of();
    }

    @Override
    public List<RecipeIngredient> findByIngredientId(int ingredientId) {
        return List.of();
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredientsByRangeOfDate(User user, LocalDateTime start, LocalDateTime end) {
        return DataBaseConnection.getSession().createQuery("""
                SELECT ri
                from RecipeIngredient ri
                join ri.recipe r
                join ri.ingredient i
                join r.calendarRecipes cr
                join cr.calendar c
                join c.user u
                
                where u.user_id = :userId and
                cr.date BETWEEN :start AND :end
                """,RecipeIngredient.class)
                .setParameter("userId", SessionManager.getInstance().getCurrentUser().getId())
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
}
