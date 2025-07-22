package services;

import dao.impl.CalendarDaoImpl;
import dao.impl.CalendarRecipeDaoImpl;
import dao.impl.RecipeDaoImpl;
import dao.interfaces.CalendarDao;
import dao.interfaces.CalendarRecipeDao;
import dao.interfaces.RecipeDao;
import infrastructure.SessionManager;
import models.*;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarService {

    private final CalendarDao calendarDao;
    private final CalendarRecipeDao calendarRecipeDao;
    private final RecipeDao recipeDao;

    public CalendarService() {
        this.calendarDao = new CalendarDaoImpl();
        this.calendarRecipeDao = new CalendarRecipeDaoImpl();
        this.recipeDao = new RecipeDaoImpl();
    }


    public void assignRecipeToDate(Recipe recipe, LocalDate date) {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) return;
        Calendar calendar = findOrCreateUserCalendar(currentUser);

        CalendarRecipe calendarRecipe = new CalendarRecipe();

        CalendarRecipeId id = new CalendarRecipeId();
        id.setCalendarId(calendar.getId());
        id.setRecipeId(recipe.getId());

        calendarRecipe.setId(id);
        calendarRecipe.setCalendar(calendar);
        calendarRecipe.setRecipe(recipe);
        calendarRecipe.setDate(date.atStartOfDay());

        calendarRecipeDao.save(calendarRecipe);
    }

    public List<CalendarRecipe> getCalendarRecipesByDate(LocalDate start, LocalDate end) {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) return List.of();
        Calendar calendar = findOrCreateUserCalendar(currentUser);
        return calendarRecipeDao.findByDateRange(calendar.getId(), start, end);
    }

    public void copyDayMenu(LocalDate fromDate, LocalDate toDate) {
        List<CalendarRecipe> recipesToCopy = getCalendarRecipesByDate(fromDate, fromDate);
        for (CalendarRecipe calendarRecipe : recipesToCopy) {
            assignRecipeToDate(calendarRecipe.getRecipe(), toDate);
        }
    }

    public void exportCalendar() {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) return;
        Calendar calendar = findOrCreateUserCalendar(currentUser);

        List<CalendarRecipe> recipes = calendarRecipeDao.findByCalendarId(calendar.getId());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Calendario CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("fecha,nombre_receta");
                for (CalendarRecipe cr : recipes) {
                    writer.printf("%s,\"%s\"\n", cr.getDate().toLocalDate(), cr.getRecipe().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void importCalendar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Calendario CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    LocalDate date = LocalDate.parse(values[0]);
                    String recipeName = values[1].replace("\"", "");

                    List<Recipe> foundRecipes = recipeDao.findByName(recipeName);
                    if (!foundRecipes.isEmpty()) {
                        assignRecipeToDate(foundRecipes.get(0), date);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Calendar findOrCreateUserCalendar(User user) {
        List<Calendar> calendars = calendarDao.findByUserId(user.getId());
        if (calendars != null && !calendars.isEmpty()) {
            return calendars.get(0);
        } else {
            Calendar newCalendar = new Calendar();
            newCalendar.setUser(user);
            newCalendar.setName(user.getName() + "'s Calendar");
            calendarDao.save(newCalendar);
            return newCalendar;
        }
    }
}