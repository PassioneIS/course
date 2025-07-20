package services;

import dao.interfaces.CalendarDao;
import dao.interfaces.CalendarRecipeDao;
import infrastructure.SessionManager;
import models.Calendar;
import models.CalendarRecipe;
import models.Recipe;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalendarServiceTest {

    @Mock
    private CalendarDao calendarDao;

    @Mock
    private CalendarRecipeDao calendarRecipeDao;

    @Mock
    private SessionManager sessionManager;

    private CalendarService calendarService;

    private User testUser;
    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        calendarService = new CalendarService(sessionManager, calendarDao, calendarRecipeDao);

        testUser = new User("testuser", "password");
        testUser.setId((short) 1);

        testRecipe = new Recipe();
        testRecipe.setId(1);
        testRecipe.setName("Test Recipe");

        when(sessionManager.getCurrentUser()).thenReturn(testUser);
    }

    @Test
    void testAssignRecipeToDate_ShouldSaveCalendarRecipe() {
        LocalDate testDate = LocalDate.now();
        ArgumentCaptor<CalendarRecipe> captor = ArgumentCaptor.forClass(CalendarRecipe.class);

        when(calendarDao.findByUserId(testUser.getId())).thenReturn(List.of(new Calendar()));

        calendarService.assignRecipeToDate(testRecipe, testDate);

        verify(calendarRecipeDao).save(captor.capture());
        assertEquals(testRecipe.getName(), captor.getValue().getRecipe().getName());
    }

    @Test
    void testGetCalendarRecipesByDate_ShouldReturnRecipesInRange() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        Calendar testCalendar = new Calendar();
        testCalendar.setId((short) 1);

        when(calendarDao.findByUserId(testUser.getId())).thenReturn(List.of(testCalendar));
        when(calendarRecipeDao.findByDateRange(testCalendar.getId(), startDate, endDate))
                .thenReturn(List.of(new CalendarRecipe(), new CalendarRecipe()));

        List<CalendarRecipe> result = calendarService.getCalendarRecipesByDate(startDate, endDate);

        assertEquals(2, result.size());
    }

    @Test
    void testCopyDayMenu_ShouldAssignRecipesToNewDate() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.plusDays(5);
        Calendar testCalendar = new Calendar();
        testCalendar.setId((short) 1);

        CalendarRecipe recipeOnFromDate = new CalendarRecipe();
        recipeOnFromDate.setRecipe(testRecipe);
        recipeOnFromDate.setDate(fromDate.atStartOfDay());

        when(calendarDao.findByUserId(testUser.getId())).thenReturn(List.of(testCalendar));
        when(calendarRecipeDao.findByDateRange(testCalendar.getId(), fromDate, fromDate))
                .thenReturn(List.of(recipeOnFromDate));

        calendarService.copyDayMenu(fromDate, toDate);

        verify(calendarRecipeDao, times(1)).save(any(CalendarRecipe.class));
    }
}