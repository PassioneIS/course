package dao.impl;

import dao.interfaces.RecipeIngredientDao;
import infrastructure.DataBaseConnection;
import infrastructure.SessionManager;
import models.RecipeIngredient;
import models.RecipeIngredientId;
import models.User;
import org.hibernate.Session;

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

        recipeIngredient.setId(new RecipeIngredientId());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setAmount(Amount);

        System.out.println("Creacion del ingrediente:" + recipe + " - " + ingredient +  " - " + Amount);

        return recipeIngredient;
    }


    @Override
    public void save(RecipeIngredient recipeIngredient){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            System.out.println("Persistiendo: " + recipeIngredient);
            //recipeIngredient.setId(new RecipeIngredientId());
            session.persist(recipeIngredient);
            transaction.commit();

            System.out.println("Se guardo el recipeIngredient:" + recipeIngredient);
        }
        catch (Exception e) {
            System.err.println("Error al guardar el recipeIngredient:" + recipeIngredient);
            e.printStackTrace();
        }
    }


    @Override
    public List<RecipeIngredient> findRecipeIngredientByRecipeId(Recipe recipe) {
        try(Session session = DataBaseConnection.getSessionFactory().openSession()){
            Query<RecipeIngredient> query = session.createQuery("FROM RecipeIngredient WHERE recipe = :recipe", RecipeIngredient.class);
            query.setParameter("recipe", recipe);
            return query.list();
        }
        catch (Exception e) {
            System.err.println("Error consultar los ingredientes de la receta con ID:" + recipe);
            e.printStackTrace();
        }
        return null;
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
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            List<RecipeIngredient> resultList;
            short userId = user.getId();
            resultList = session.createQuery("""
                            SELECT ri
                            from RecipeIngredient ri
                            join ri.recipe r
                            join ri.ingredient i
                            join r.calendarRecipes cr
                            join cr.calendar c
                            join c.user u
                            
                            where u.user_id = :userId and
                            cr.date BETWEEN :start AND :end
                            """, RecipeIngredient.class)
                    .setParameter("userId", userId)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();

            return resultList;
        }catch (Exception e){
            e.printStackTrace();
            return List.of();
        }
    }
}
