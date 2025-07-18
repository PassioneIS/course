package dao.impl;

import dao.interfaces.RecipeStepDao;
import models.RecipeStep;
import models.Recipe;

import infrastructure.DataBaseConnection;
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
    public RecipeStep createRecipeStep(Recipe recipe, short position, String text){
        RecipeStep recipeStep = new RecipeStep();

        recipeStep.setRecipe(recipe);
        recipeStep.setPosition(position);
        recipeStep.setText(text);

        System.out.println("Creacion del paso:" + recipe + " - " + position +  " - " + text);

        return recipeStep;
    }


    @Override
    public void Save(RecipeStep recipeStep){
        try (Session session = DataBaseConnection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(recipeStep);
            transaction.commit();
            System.out.println("Se guardo el paso:" + recipeStep);
        }
    }

    @Override
    public List<RecipeStep> findByRecipeIdOrderByPosition(int recipeId) {
        return List.of();
    }
}
