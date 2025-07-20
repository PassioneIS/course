package dao.interfaces;

import dao.DAO;
import models.Calendar;

import java.util.List;

public interface CalendarDao extends DAO<Calendar, Integer> {
    List<Calendar> findByUserId(int userId);
}
