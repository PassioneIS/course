package dao.interfaces;

import dao.DAO;
import models.RecipeBook;

import java.util.List;
import models.User;

public interface RecipeBookDao extends DAO<RecipeBook, Integer> {
    List<RecipeBook> findByUserId(int userId);
    RecipeBook findByUser(User user);
    RecipeBook createRecipeBook(User user);
    void saveRecipeBook(RecipeBook recipeBook);
}