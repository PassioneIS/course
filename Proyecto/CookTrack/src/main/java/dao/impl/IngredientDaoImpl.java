package dao.impl;

import dao.interfaces.IngredientDao;
import infrastructure.DataBaseConnection;
import models.Ingredient;
import org.hibernate.Session;
import java.time.LocalDate;
import java.util.List;

public class IngredientDaoImpl extends DaoImpl<Ingredient,Integer> implements IngredientDao{

    public IngredientDaoImpl() {
        super(Ingredient.class);
    }


    public List<Ingredient> getIngredientsFromUserCalendar(int userId, LocalDate start, LocalDate end) {
        Session session = DataBaseConnection.getSession();

        /*List<Ingredient> ingredients = session.createQuery("""
            SELECT DISTINCT i
            FROM Ingredient i
            JOIN RecipeIngredient ri ON ri.ingredient.id = i.id
            JOIN Recipe r ON ri.recipe.id = r.id
            JOIN CalendarRecipe cr ON cr.recipe.id = r.id
            JOIN Calendar c ON cr.calendar.id = c.id
            JOIN User u ON c.user.id = u.id
            WHERE u.id = :userId
            AND cr.date BETWEEN :start AND :end
            ORDER BY i.name
        """, Ingredient.class)
                .setParameter("userId", userId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();*/

        return session.createQuery("""
            SELECT i
            FROM Ingredient i
        """, Ingredient.class).getResultList();
    }

    @Override
    public Ingredient findByName(String name) {
        Session session = DataBaseConnection.getSession();
        return null;
    }
}
