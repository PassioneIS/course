//package dao.impl;

import dao.impl.RecipeDaoImpl;
import models.Recipe;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import infrastructure.DataBaseConnection;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDaoImplTest {

    private RecipeDaoImpl recipeDao;

    private List<Recipe> recipesTest = new ArrayList<>();


    @BeforeEach
    void setUp() {
        recipeDao = new RecipeDaoImpl();
    }

    @Test
    void testCreateAndSaveRecipe_successfullyPersists() {
        // 1. Crear receta
        Recipe recipe = recipeDao.createRecipe("Tortilla", 25);
        recipesTest.add(recipe);

        assertNull(recipe.getId(), "La receta aún no debería tener ID antes de guardar");

        // 2. Guardar receta
        recipeDao.save(recipe);

        assertNotNull(recipe.getId(), "La receta debería tener un ID después de guardar");

        // 3. Buscar receta por ID
        Recipe loaded = recipeDao.findById(recipe.getId());

        //assertNotNull(loaded, "La receta debería haberse recuperado de la base de datos");
        assertEquals("Tortilla", loaded.getName());
        assertEquals(25, loaded.getPreptime());
    }

    @AfterEach
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