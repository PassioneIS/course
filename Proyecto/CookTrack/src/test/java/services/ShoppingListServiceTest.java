package services;

import dao.interfaces.IngredientDao;
import dao.interfaces.RecipeIngredientDao;
import infrastructure.SessionManager;
import models.Ingredient;
import models.RecipeIngredient;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingListServiceTest {

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
    void testGetShoppingList_withResults() {
        User mockUser = new User();
        mockUser.setName("Juan");

        LocalDate start = LocalDate.of(2025, 7, 1);
        LocalDate end = LocalDate.of(2025, 7, 19);

        Ingredient i = new Ingredient();
        i.setName("Chicken Pollo");
        RecipeIngredient ri = new RecipeIngredient();
        ri.setAmount((short) 2);
        ri.setIngredient(i);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            SessionManager sessionManagerMock = mock(SessionManager.class);
            sm.when(SessionManager::getInstance).thenReturn(sessionManagerMock);
            when(sessionManagerMock.getCurrentUser()).thenReturn(mockUser);

            when(recipeIngredientDaoMock.getRecipeIngredientsByRangeOfDate(eq(mockUser), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(ri));

            // Test
            List<RecipeIngredient> result = shoppingListService.getShoppingList(start, end);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(2, result.getFirst().getAmount());
            assertEquals("Chicken Pollo", result.getFirst().getIngredient().getName());
        }
    }

    @Test
    void testGetShoppingList_empty() {
        User mockUser = new User();
        mockUser.setName("Juan");

        LocalDate start = LocalDate.of(2025, 7, 1);
        LocalDate end = LocalDate.of(2025, 7, 10);

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            SessionManager sessionManagerMock = mock(SessionManager.class);
            sm.when(SessionManager::getInstance).thenReturn(sessionManagerMock);
            when(sessionManagerMock.getCurrentUser()).thenReturn(mockUser);

            // Mock DAO retorna lista vacía
            when(recipeIngredientDaoMock.getRecipeIngredientsByRangeOfDate(eq(mockUser), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Collections.emptyList());

            // Test
            List<RecipeIngredient> result = shoppingListService.getShoppingList(start, end);

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void testGetShoppingList_nullDates() {
        User mockUser = new User();
        mockUser.setName("Juan");

        try (MockedStatic<SessionManager> sm = Mockito.mockStatic(SessionManager.class)) {
            SessionManager sessionManagerMock = mock(SessionManager.class);
            sm.when(SessionManager::getInstance).thenReturn(sessionManagerMock);
            when(sessionManagerMock.getCurrentUser()).thenReturn(mockUser);

            assertThrows(NullPointerException.class, () -> shoppingListService.getShoppingList(null, null));
        }
    }
}
