package services;

import models.Recipe;
import models.User;
import models.Ingredient;
import models.RecipeStep;
import models.RecipeIngredient;
import models.RecipeBook;
import models.RecipeBookRecipe;
import models.*;

import dao.impl.RecipeDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.impl.RecipeStepDaoImpl;
import dao.impl.RecipeBookDaoImpl;
import dao.impl.RecipeBookRecipeDaoImpl;

import dao.interfaces.RecipeBookDao;
import dao.interfaces.RecipeDao;
import dao.interfaces.RecipeIngredientDao;
import dao.interfaces.RecipeStepDao;

import java.util.List;
import java.util.stream.Collectors;

import infrastructure.SessionManager;


public class BookRecipeService {

    private final RecipeDao recipeDao;
    private final RecipeIngredientDao recipeIngredientDao;
    private final RecipeStepDao recipeStepDao;
    private final RecipeBookDao recipeBookDao;

    public BookRecipeService() {
        this.recipeDao = new RecipeDaoImpl();
        this.recipeIngredientDao = new RecipeIngredientDaoImpl();
        this.recipeStepDao = new RecipeStepDaoImpl();
        this.recipeBookDao = new RecipeBookDaoImpl();
    }

    public List<Recipe> getRecipes(User user) {
        List<RecipeBook> recipeBooks = recipeBookDao.findByUserId(user.getId());
        if (recipeBooks == null || recipeBooks.isEmpty()) {
            return List.of();
        }

        RecipeBook recipeBook = recipeBooks.get(0);
        return recipeBook.getRecipeBookRecipes().stream()
                .map(RecipeBookRecipe::getRecipe)
                .collect(Collectors.toList());
    }
  
    public void createRecipe(String name, Integer prepTime, List<Ingredient> ingredientsList, List<Short> listIngredientsAmount, List<String> stepsList, List<String> nameTags) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPreptime(prepTime);
        recipeDao.save(recipe);


        for(int i=0 ; i < ingredientsList.size();i++){
            //RecipeIngredientDaoImpl recipeIngredientDao = new RecipeIngredientDaoImpl();
            short amount = listIngredientsAmount.get(i);
            Ingredient ingredientOfList = ingredientsList.get(i);

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredientOfList);
            recipeIngredient.setAmount(amount);
            recipeIngredientDao.save(recipeIngredient);
        }

        //RecipeStepDaoImpl recipeStepDao = new RecipeStepDaoImpl();

        for(short i=0 ;i<stepsList.size();i++){
            short position = (short)(i+1);
            RecipeStep recipeStep = recipeStepDao.createRecipeStep(recipe, position, stepsList.get(i));
            recipeStepDao.save(recipeStep);
        }

        //RecipeBookDaoImpl recipeBookDao = new RecipeBookDaoImpl();

        User user = SessionManager.getInstance().getCurrentUser();

        RecipeBook recipeBook = recipeBookDao.findByUser(user);

        if(recipeBook == null){
            recipeBook = recipeBookDao.createRecipeBook(user);
            recipeBookDao.saveRecipeBook(recipeBook);
        }

        RecipeBookRecipeDaoImpl recipeBookRecipeDaoImpl = new RecipeBookRecipeDaoImpl();

        RecipeBookRecipe recipeBookRecipe = recipeBookRecipeDaoImpl.createRecipeBookRecipe(recipeBook, recipe, nameTags);

        recipeBookRecipeDaoImpl.save(recipeBookRecipe);

        //System.out.println("Creacion de la receta:"+ name + " prepTime: " + prepTime + "ingrediets:" + ingredientsList + " ,ingredients amount " + listIngredientsAmount + " , pasos:" + stepsList);
    }

    public RecipeBookRecipe findByRecipe(Recipe recipe){
      
        RecipeBookRecipeDaoImpl recipeBookRecipeDao = new RecipeBookRecipeDaoImpl();
      
        RecipeBookRecipe recipeBookRecipe = recipeBookRecipeDao.findRecipeBookRecipeByRecipe(recipe);

        return recipeBookRecipe;
    }

    public void changePublic(RecipeBookRecipe recipeBookRecipe){
        RecipeBookRecipeDaoImpl recipeBookRecipeDaoImpl = new RecipeBookRecipeDaoImpl();

        recipeBookRecipe.setPublic(!recipeBookRecipe.isPublic());
        recipeBookRecipeDaoImpl.update(recipeBookRecipe);
    }

    public void changeFavorite(RecipeBookRecipe recipeBookRecipe){
        RecipeBookRecipeDaoImpl recipeBookRecipeDaoImpl = new RecipeBookRecipeDaoImpl();

        recipeBookRecipe.setFavorite(!recipeBookRecipe.isFavorite());
        recipeBookRecipeDaoImpl.update(recipeBookRecipe);
    }

    public List<Recipe> getAllRecipes(){
        //RecipeDaoImpl recipeDao = new RecipeDaoImpl();
        List<Recipe> recipesList = recipeDao.findAll();
        return recipesList;
    }

    public void addRecipe(Recipe recipe){

    }

    public void exportRecipePDF(Recipe recipe){

    }

    public void updateRecipe(Recipe recipe){

    }

    public void deleteRecipe(Recipe recipe){

    }

    public void addLabel(Recipe recipe){

    }

    public void markAsFavorite(Recipe recipe){
      
    }
}