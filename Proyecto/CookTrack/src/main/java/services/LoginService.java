package services;

import dao.DAO;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import models.User;

public class LoginService {
    static UserDao userDao = new UserDaoImpl();

    public User getUser(String username,String password){
       User user = userDao.findByName(username);
       if (user != null && user.getPassword().equals(password)){
           return user;
       };
       return null;
    }

    public void registerUser(User user){
        //TODO
    }
}
