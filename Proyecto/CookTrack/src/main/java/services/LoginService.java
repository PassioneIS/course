package services;

import dao.interfaces.UserDao;
import dao.impl.UserDaoImpl;
import infrastructure.SessionManager;
import models.User;

public class LoginService {
    static UserDao userDao = new UserDaoImpl();

    public boolean logIn(String username,String password){
       User user = userDao.findByName(username);
       if (user != null && user.getPassword().equals(password)){
           SessionManager.getInstance().login(user);
           return true;
       };
       return false;
    }

    public void registerUser(User user){

    }

    public void logOut(){

    }
}
