package services;

import dao.impl.UserDaoImpl;
import dao.interfaces.UserDao;
import infrastructure.SessionManager;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {
    static UserDao userDao = new UserDaoImpl();

    public boolean logIn(String username, String rawPassword) {
        User user = userDao.findByName(username);
        if (user != null && BCrypt.checkpw(rawPassword, user.getPassword())) {
            SessionManager.getInstance().login(user);
            return true;
        }
        return false;
    }
}