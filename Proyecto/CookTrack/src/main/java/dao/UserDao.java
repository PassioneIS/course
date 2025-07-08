package dao;

import models.User;

public interface UserDao extends DAO<User,Integer> {
    User findByName(String name);
}
