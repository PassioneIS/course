package dao.impl;

import dao.interfaces.CalendarDao;
import models.Calendar;
import java.util.List;

public class CalendarDaoImpl extends DaoImpl<Calendar,Integer> implements CalendarDao {

    public CalendarDaoImpl() {
        super(Calendar.class);
    }

    @Override
    public List<Calendar> findByUserId(int userId) {
        return List.of();
    }
}
