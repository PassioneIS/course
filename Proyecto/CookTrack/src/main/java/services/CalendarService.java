package services;

import dao.impl.CalendarDaoImpl;
import dao.impl.CalendarRecipeDaoImpl;
import dao.interfaces.CalendarDao;
import dao.interfaces.CalendarRecipeDao;
import infrastructure.SessionManager;
import models.Calendar;
import models.CalendarRecipe;
import models.Recipe;
import models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarService {

    private final CalendarDao calendarDao;
    private final CalendarRecipeDao calendarRecipeDao;
    private final SessionManager sessionManager;

    public CalendarService() {
        this(SessionManager.getInstance(), new CalendarDaoImpl(), new CalendarRecipeDaoImpl());
    }

    CalendarService(SessionManager sessionManager, CalendarDao calendarDao, CalendarRecipeDao calendarRecipeDao) {
        this.sessionManager = sessionManager;
        this.calendarDao = calendarDao;
        this.calendarRecipeDao = calendarRecipeDao;
    }

    public void assignRecipeToDate(Recipe recipe, LocalDate date) {
        User currentUser = sessionManager.getCurrentUser();
        if (currentUser == null) {
            return;
        }

        List<Calendar> calendars = calendarDao.findByUserId(currentUser.getId());
        Calendar calendar = calendars.stream().findFirst().orElse(null);

        if (calendar == null) {
            calendar = new Calendar();
            calendar.setUser(currentUser);
            calendar.setName(currentUser.getName() + "'s Calendar");
            calendarDao.save(calendar);
        }

        CalendarRecipe calendarRecipe = new CalendarRecipe();
        calendarRecipe.setCalendar(calendar);
        calendarRecipe.setRecipe(recipe);
        calendarRecipe.setDate(date.atStartOfDay());

        calendarRecipeDao.save(calendarRecipe);
    }

    public List<CalendarRecipe> getCalendarRecipesByDate(LocalDate start, LocalDate end) {
        User currentUser = sessionManager.getCurrentUser();
        if (currentUser == null) {
            return List.of();
        }

        List<Calendar> calendars = calendarDao.findByUserId(currentUser.getId());
        if (calendars == null || calendars.isEmpty()) {
            return List.of();
        }
        Calendar calendar = calendars.get(0);

        return calendarRecipeDao.findByDateRange(calendar.getId(), start, end);
    }

    public void copyDayMenu(LocalDate fromDate, LocalDate toDate) {
        User currentUser = sessionManager.getCurrentUser();
        if (currentUser == null) {
            return;
        }

        List<CalendarRecipe> recipesToCopy = getCalendarRecipesByDate(fromDate, fromDate);

        for (CalendarRecipe calendarRecipe : recipesToCopy) {
            assignRecipeToDate(calendarRecipe.getRecipe(), toDate);
        }
    }

    public void importCalendar() {
    }

    public void exportCalendar() {
    }
}