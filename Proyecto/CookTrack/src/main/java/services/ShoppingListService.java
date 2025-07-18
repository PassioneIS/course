package services;

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
    private final RecipeIngredientDao recipeIngredientDao;

    public ShoppingListService(RecipeIngredientDao recipeIngredientDao) {
        this.recipeIngredientDao = recipeIngredientDao;
    }

    public List<RecipeIngredient> getShoppingList(LocalDate start, LocalDate end){
        User user = SessionManager.getInstance().getCurrentUser();
        LocalDateTime s = start.atStartOfDay();
        LocalDateTime e = end.atTime(LocalTime.MAX);
        return recipeIngredientDao.getRecipeIngredientsByRangeOfDate(user,s,e);
    }

    public void checkIngredient(Ingredient ing){
        //TODO
        //Modify bd to persist shoppingList
    }

    public void addIngredient(List<Ingredient> shoppingList, Ingredient ingredient){

    }

    public void removeIngredient(List<Ingredient> shoppingList,Ingredient ingredient){

    }

}
