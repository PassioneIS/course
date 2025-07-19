package services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dao.interfaces.IngredientDao;
import dao.interfaces.RecipeIngredientDao;
import infrastructure.SessionManager;
import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import services.ShoppingListService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

class ShoppingListServiceTest {

    private RecipeIngredientDao recipeIngredientDaoMock;
    private IngredientDao ingredientDaoMock;
    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        recipeIngredientDaoMock = mock(RecipeIngredientDao.class);
        ingredientDaoMock = mock(IngredientDao.class);
        shoppingListService = new ShoppingListService(recipeIngredientDaoMock, ingredientDaoMock);
    }

    @Test
    void testGetShoppingList() {
        User mockUser = new User();
        mockUser.setName("Juan");

        LocalDate start = LocalDate.of(2025, 7, 1);
        LocalDate end = LocalDate.of(2025, 7, 10);

        Ingredient i = new Ingredient();
        i.setName("Chicken Pollo");
        RecipeIngredient ri = new RecipeIngredient();
        ri.setAmount((short) 2);
        ri.setIngredient(i);

        // Mock SessionManager
        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            SessionManager sessionManagerMock = mock(SessionManager.class);
            sm.when(SessionManager::getInstance).thenReturn(sessionManagerMock);
            when(sessionManagerMock.getCurrentUser()).thenReturn(mockUser);

            // Mock DAO
            when(recipeIngredientDaoMock.getRecipeIngredientsByRangeOfDate(eq(mockUser), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(List.of(ri));

            // Test --------------
            List<RecipeIngredient> result = shoppingListService.getShoppingList(start, end);

            // Verificacion
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(2, result.getFirst().getAmount());
            assertEquals(i, result.getLast().getIngredient());
        }
    }

    /*@Test
    void testGetIngredientsbyName() {
        Ingredient i = new Ingredient();
        i.setName("Tomate");

        when(ingredientDaoMock.getIngredientsLikeName("Tom")).thenReturn(List.of(i));

        List<Ingredient> result = shoppingListService.getIngredientsbyName("Tom");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tomate", result.getFirst().getName());
    }*/
}
