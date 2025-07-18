package dao.interfaces;

import dao.DAO;
import models.User;

public interface UserDao extends DAO<User,Integer> {
    User findByName(String name);
    void createUser(User user);
}
