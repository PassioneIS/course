package dao.interfaces;

import dao.DAO;
import models.CalendarRecipe;
import models.CalendarRecipeId;
import java.time.LocalDate;
import java.util.List;

public interface CalendarRecipeDao extends DAO<CalendarRecipe, CalendarRecipeId> {
    List<CalendarRecipe> findByCalendarId(int calendarId);
    List<CalendarRecipe> findByDateRange(int calendarId, LocalDate start, LocalDate end);
}