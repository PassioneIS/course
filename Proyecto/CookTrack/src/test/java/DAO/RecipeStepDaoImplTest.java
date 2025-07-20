import dao.impl.RecipeDaoImpl;
import dao.impl.RecipeStepDaoImpl;
import models.Recipe;
import models.RecipeStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import infrastructure.DataBaseConnection;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeStepDaoImplTest {

    private RecipeStepDaoImpl recipeStepDao;
    private RecipeDaoImpl recipeDao;

    private List<Recipe> recipesTest = new ArrayList<>();
    private List<RecipeStep> stepTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        recipeStepDao = new RecipeStepDaoImpl();
        recipeDao = new RecipeDaoImpl();
    }

    @Test
    void testCreateSaveAndFindStepsByRecipe() {
        // Crear y guardar una receta
        Recipe recipe = recipeDao.createRecipe("Panqueques", 15);
        recipesTest.add(recipe);
        recipeDao.save(recipe);

        assertNotNull(recipe.getId(), "La receta debería tener un ID");

        // Crear y guardar pasos
        RecipeStep step1 = recipeStepDao.createRecipeStep(recipe, (short) 1, "Mezclar ingredientes");
        RecipeStep step2 = recipeStepDao.createRecipeStep(recipe, (short) 2, "Cocinar en sartén");

        stepTest.add(step1);
        stepTest.add(step2);

        recipeStepDao.save(step1);
        recipeStepDao.save(step2);

        assertNotNull(step1.getId());
        assertNotNull(step2.getId());

        // Buscar pasos por receta
        List<RecipeStep> steps = recipeStepDao.findByRecipeIdOrderByPosition(recipe);

        assertNotNull(steps);
        assertEquals(2, steps.size());

        assertEquals(1, steps.get(0).getPosition().intValue());
        assertEquals("Mezclar ingredientes", steps.get(0).getText());

        assertEquals(2, steps.get(1).getPosition().intValue());
        assertEquals("Cocinar en sartén", steps.get(1).getText());
    }

    @AfterEach
    void clearSteps() {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            for (RecipeStep step : stepTest) {
                session.remove(session.merge(step));
            }
            tx.commit();
        }
        recipesTest.clear();
    }

    void clearRecipes() {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            for (Recipe recipe : recipesTest) {
                session.remove(session.merge(recipe));
            }
            tx.commit();
        }
        recipesTest.clear();
    }
}