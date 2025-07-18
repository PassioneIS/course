package services;

import dao.interfaces.UserDao;
import dao.impl.UserDaoImpl;
import infrastructure.SessionManager;
import models.User;

public class SignupService {
    static UserDao userDao = new UserDaoImpl();


}