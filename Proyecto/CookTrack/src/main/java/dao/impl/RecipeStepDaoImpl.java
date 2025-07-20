package dao.impl;

import dao.interfaces.RecipeStepDao;
import infrastructure.DataBaseConnection;
import models.Recipe;
import models.RecipeStep;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RecipeStepDaoImpl extends DaoImpl<RecipeStep, Integer> implements RecipeStepDao {

    public RecipeStepDaoImpl() {
        super(RecipeStep.class);
    }

    //static Session session = DataBaseConnection.getSession();

    @Override
    public RecipeStep createRecipeStep(Recipe recipe, short position, String text) {
        RecipeStep recipeStep = new RecipeStep();

        recipeStep.setRecipe(recipe);
        recipeStep.setPosition(position);
        recipeStep.setText(text);

        System.out.println("Creacion del paso:" + recipe + " - " + position + " - " + text);

        return recipeStep;
    }


    @Override
    public void save(RecipeStep recipeStep) {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            System.out.println("Persistiendo: " + recipeStep);
            session.persist(recipeStep);
            transaction.commit();
            System.out.println("Se guardo el paso:" + recipeStep);
        } catch (Exception e) {
            System.err.println("Error al guardar el paso:" + recipeStep);
            e.printStackTrace();
        }
    }

    @Override
    public List<RecipeStep> findByRecipeIdOrderByPosition(Recipe recipe) {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Query<RecipeStep> query = session.createQuery("FROM RecipeStep WHERE recipe= :recipe", RecipeStep.class);
            query.setParameter("recipe", recipe);
            return query.list();
        }

        //return List.of();
    }
}
