package services;

import dao.impl.RecipeBookDaoImpl;
import dao.impl.RecipeDaoImpl;
import dao.impl.RecipeIngredientDaoImpl;
import dao.impl.RecipeStepDaoImpl;
import dao.interfaces.RecipeBookDao;
import dao.interfaces.RecipeDao;
import dao.interfaces.RecipeIngredientDao;
import dao.interfaces.RecipeStepDao;
import models.*;

import java.util.List;
import java.util.stream.Collectors;

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

    public void createRecipe(String name, Integer prepTime, List<Ingredient> ingredientsList, List<Short> listIngredientsAmount, List<String> stepsList) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPreptime(prepTime);
        recipeDao.save(recipe);

        for (int i = 0; i < ingredientsList.size(); i++) {
            short amount = listIngredientsAmount.get(i);
            Ingredient ingredientOfList = ingredientsList.get(i);

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipe(recipe);
            recipeIngredient.setIngredient(ingredientOfList);
            recipeIngredient.setAmount(amount);
            recipeIngredientDao.save(recipeIngredient);
        }

        for (short i = 0; i < stepsList.size(); i++) {
            short position = (short) (i + 1);
            RecipeStep recipeStep = new RecipeStep();
            recipeStep.setRecipe(recipe);
            recipeStep.setPosition(position);
            recipeStep.setText(stepsList.get(i));
            recipeStepDao.save(recipeStep);
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.findAll();
    }

    public void addRecipe(Recipe recipe) {
    }

    public void exportRecipePDF(Recipe recipe) {
    }

    public void updateRecipe(Recipe recipe) {
    }

    public void deleteRecipe(Recipe recipe) {
    }

    public void addLabel(Recipe recipe) {
    }

    public void markAsFavorite(Recipe recipe) {
    }
}