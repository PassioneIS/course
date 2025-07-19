import dao.impl.IngredientDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.impl.UserDaoImpl;
import dao.interfaces.IngredientDao;
import dao.interfaces.RecipeIngredientDao;
import dao.interfaces.UserDao;
import models.Ingredient;
import models.RecipeIngredient;
import models.User;

public class db {
    public static void main(String[] args) {
        System.out.println("Ejemplo uso USER DAO");

        UserDao userDao = new UserDaoImpl();

        for( User u: userDao.findAll()){
            System.out.println(u.getName());
        }

        System.out.println("Ejemplo uso Ingredient DAO");

        IngredientDao ingredientDao = new IngredientDaoImpl();
        for( Ingredient i: ingredientDao.findAll()){
            System.out.println(i.getName());
        }

        System.out.println("Ejemplo uso RecipeIngredient DAO");

        RecipeIngredientDao recipeIngredientDao = new RecipeIngredientDaoImpl();
        for( RecipeIngredient i: recipeIngredientDao.findAll()){
            System.out.println(i.getIngredient().getName() + " for "+ i.getRecipe().getName());
        }
    }
}