package dao.impl;

import dao.interfaces.CalendarRecipeDao;
import models.CalendarRecipe;
import models.CalendarRecipeId;
import java.time.LocalDate;
import java.util.List;

public class CalendarRecipeDaoImpl extends DaoImpl<CalendarRecipe, CalendarRecipeId> implements CalendarRecipeDao {

    public CalendarRecipeDaoImpl() {
        super(CalendarRecipe.class);
    }
    @Override
    public List<CalendarRecipe> findByCalendarId(int calendarId) {
        return List.of();
    }

    @Override
    public List<CalendarRecipe> findByDateRange(int calendarId, LocalDate start, LocalDate end) {
        return List.of();
    }
}
