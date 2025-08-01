package dao.impl;

import dao.interfaces.CalendarRecipeDao;
import infrastructure.DataBaseConnection;
import models.CalendarRecipe;
import models.CalendarRecipeId;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class CalendarRecipeDaoImpl extends DaoImpl<CalendarRecipe, CalendarRecipeId> implements CalendarRecipeDao {

    public CalendarRecipeDaoImpl() {
        super(CalendarRecipe.class);
    }

    @Override
    public List<CalendarRecipe> findByCalendarId(int calendarId) {
        try (Session session = DataBaseConnection.getSession()) {
            String hql = "FROM CalendarRecipe cr WHERE cr.calendar.id = :calendarId";
            Query<CalendarRecipe> query = session.createQuery(hql, CalendarRecipe.class);
            query.setParameter("calendarId", calendarId);
            return query.list();
        }
    }

    @Override
    public List<CalendarRecipe> findByDateRange(int calendarId, LocalDate start, LocalDate end) {
        try (Session session = DataBaseConnection.getSession()) {
            String hql = "FROM CalendarRecipe cr WHERE cr.calendar.id = :calendarId AND cr.date >= :startDate AND cr.date <= :endDate";
            Query<CalendarRecipe> query = session.createQuery(hql, CalendarRecipe.class);
            query.setParameter("calendarId", calendarId);
            query.setParameter("startDate", start.atStartOfDay());
            query.setParameter("endDate", end.atTime(23, 59, 59));
            return query.list();
        }
    }
}