package services;

import dao.impl.IngredientDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.interfaces.IngredientDao;
import dao.interfaces.RecipeIngredientDao;
import infrastructure.SessionManager;
import models.Ingredient;
import models.RecipeIngredient;
import models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ShoppingListService {
    private static ShoppingListService shoppingListService;
    private final RecipeIngredientDao recipeIngredientDao;
    private final IngredientDao ingredientDao;

    private ShoppingListService() {
        this.recipeIngredientDao = new RecipeIngredientDaoImpl();
        this.ingredientDao = new IngredientDaoImpl();
    }

    //For testing only
    public ShoppingListService(RecipeIngredientDao recipeIngredientDao, IngredientDao ingredientDao) {
        this.recipeIngredientDao = recipeIngredientDao;
        this.ingredientDao = ingredientDao;
    }

    //Singleton
    public static ShoppingListService getInstance() {
        if (shoppingListService == null) {
            shoppingListService = new ShoppingListService();
        }
        return shoppingListService;
    }

    public List<RecipeIngredient> getShoppingList(LocalDate start, LocalDate end) {
        User user = SessionManager.getInstance().getCurrentUser();
        LocalDateTime s = start.atStartOfDay();
        LocalDateTime e = end.atTime(LocalTime.MAX);
        return recipeIngredientDao.getRecipeIngredientsByRangeOfDate(user, s, e);
    }

    public List<Ingredient> getIngredients() {
        return ingredientDao.findAll();
    }

}
