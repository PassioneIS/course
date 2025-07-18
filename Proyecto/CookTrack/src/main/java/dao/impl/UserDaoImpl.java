package dao.impl;

import dao.interfaces.UserDao;
import infrastructure.DataBaseConnection;
import models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDaoImpl extends DaoImpl<User,Integer> implements UserDao{

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByName(String name) {
        Session session = DataBaseConnection.getSession();
        Query<User> query = session.createQuery("FROM User WHERE name = :name", User.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
