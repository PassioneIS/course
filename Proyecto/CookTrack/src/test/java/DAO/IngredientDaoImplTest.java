import dao.impl.IngredientDaoImpl;
import models.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import infrastructure.DataBaseConnection;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDaoImplTest {

    private IngredientDaoImpl ingredientDao;

    private List<Ingredient> ingredientsTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ingredientDao = new IngredientDaoImpl();
    }

    @Test
    void testFindByIdAndName_returnsCorrectIngredient() {
        // Crear y guardar ingrediente
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Queso costeño");

        ingredientsTest.add(ingredient);

        // Guardar ingrediente en la base de datos
        ingredientDao.save(ingredient);  // Este método debe existir

        // Verificar que tiene ID asignado
        assertTrue(ingredient.getId() > 0, "El ingrediente debería tener un ID después de guardar");

        // Buscar por ID
        Ingredient byId = ingredientDao.findById(ingredient.getId());
        assertNotNull(byId);
        assertEquals("Queso costeño", byId.getName());

        // Buscar por nombre
        Ingredient byName = ingredientDao.findByName("Queso costeño");
        assertNotNull(byName);
        assertEquals(ingredient.getId(), byName.getId());

    }

    @AfterEach
    void clearIngredients() {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            for (Ingredient ingredient : ingredientsTest) {
                session.remove(session.merge(ingredient));
            }
            tx.commit();
        }
        ingredientsTest.clear();
    }

}