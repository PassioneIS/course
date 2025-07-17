package dao.impl;

import dao.interfaces.RecipeIngredientDao;
import infrastructure.DataBaseConnection;
import infrastructure.SessionManager;
import models.RecipeIngredient;
import models.RecipeIngredientId;
import models.User;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeIngredientDaoImpl extends DaoImpl<RecipeIngredient, RecipeIngredientId> implements RecipeIngredientDao {

    public RecipeIngredientDaoImpl() {
        super(RecipeIngredient.class);
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
