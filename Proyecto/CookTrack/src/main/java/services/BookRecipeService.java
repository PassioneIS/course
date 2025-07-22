package services;

import dao.impl.*;
import dao.interfaces.*;
import infrastructure.SessionManager;
import models.*;

import java.util.List;
import java.util.stream.Collectors;

public class BookRecipeService {

    private final RecipeDao recipeDao;
    private final RecipeIngredientDao recipeIngredientDao;
    private final RecipeStepDao recipeStepDao;
    private final RecipeBookDao recipeBookDao;
    private final RecipeBookRecipeDao recipeBookRecipeDao;

    public BookRecipeService() {
        this.recipeDao = new RecipeDaoImpl();
        this.recipeIngredientDao = new RecipeIngredientDaoImpl();
        this.recipeStepDao = new RecipeStepDaoImpl();
        this.recipeBookDao = new RecipeBookDaoImpl();
        this.recipeBookRecipeDao = new RecipeBookRecipeDaoImpl();
    }

    public List<Recipe> getRecipes(User user) {
        List<RecipeBook> recipeBooks = recipeBookDao.findByUserId(user.getId());
        if (recipeBooks == null || recipeBooks.isEmpty()) {
            return List.of();
        }

        RecipeBook recipeBook = recipeBooks.get(0);
        if (recipeBook.getRecipeBookRecipes() == null) return List.of();
        return recipeBook.getRecipeBookRecipes().stream()
                .map(RecipeBookRecipe::getRecipe)
                .collect(Collectors.toList());
    }

    public void createRecipe(String name, Integer prepTime, List<Ingredient> ingredientsList, List<Short> listIngredientsAmount, List<String> stepsList, List<String> tags) {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) return;

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPreptime(prepTime);
        recipeDao.save(recipe);

        saveIngredientsAndSteps(recipe, ingredientsList, listIngredientsAmount, stepsList);

        RecipeBook recipeBook = recipeBookDao.findByUserId(currentUser.getId()).get(0);
        RecipeBookRecipe rbr = new RecipeBookRecipe();
        rbr.setRecipeBook(recipeBook);
        rbr.setRecipe(recipe);
        rbr.setNametag(tags);
        recipeBookRecipeDao.save(rbr);
    }

    public void updateRecipe(Recipe recipe, String newName, Integer newPrepTime, List<Ingredient> newIngredients, List<Short> newAmounts, List<String> newSteps, List<String> newTags) {
        recipe.setName(newName);
        recipe.setPreptime(newPrepTime);
        recipeDao.update(recipe);

        recipeIngredientDao.deleteByRecipeId(recipe.getId());
        recipeStepDao.deleteByRecipeId(recipe.getId());

        saveIngredientsAndSteps(recipe, newIngredients, newAmounts, newSteps);

        RecipeBookRecipe rbr = recipeBookRecipeDao.findByRecipeId(recipe.getId());
        if (rbr != null) {
            rbr.setNametag(newTags);
            recipeBookRecipeDao.update(rbr);
        }
    }

    private void saveIngredientsAndSteps(Recipe recipe, List<Ingredient> ingredients, List<Short> amounts, List<String> steps) {
        for (int i = 0; i < ingredients.size(); i++) {
            RecipeIngredient ri = new RecipeIngredient();
            ri.setRecipe(recipe);
            ri.setIngredient(ingredients.get(i));
            ri.setAmount(amounts.get(i));
            recipeIngredientDao.save(ri);
        }

        for (short i = 0; i < steps.size(); i++) {
            RecipeStep rs = new RecipeStep();
            rs.setRecipe(recipe);
            rs.setPosition((short) (i + 1));
            rs.setText(steps.get(i));
            recipeStepDao.save(rs);
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipeDao.findAll();
    }

    public void addRecipe(Recipe recipe) {}
    public void deleteRecipe(Recipe recipe) {}
    public void markAsFavorite(Recipe recipe) {}
}