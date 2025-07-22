package dao.impl;

import dao.interfaces.CalendarDao;
import infrastructure.DataBaseConnection;
import models.Calendar;
import org.hibernate.Session;

import java.util.List;

public class CalendarDaoImpl extends DaoImpl<Calendar, Integer> implements CalendarDao {

    public CalendarDaoImpl() {
        super(Calendar.class);
    }

    @Override
    public List<Calendar> findByUserId(int userId) {
        List<Calendar> calendars = List.of(); // default vacío en caso de error

        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            calendars = session.createQuery(
                            "FROM Calendar c WHERE c.user.id = :userId", Calendar.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return calendars;
    }
}
