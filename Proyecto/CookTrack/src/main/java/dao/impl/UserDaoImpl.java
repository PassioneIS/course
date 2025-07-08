package dao.impl;

import dao.UserDao;
import infrastructure.DataBaseConnection;
import models.Ingredient;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {
    static Session session = DataBaseConnection.getSession();

    @Override
    public void save(User user) {
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
    }

    @Override
    public User findById(Integer integer) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public User findByName(String name) {
        Query<User> query = session.createQuery("FROM User WHERE name = :name", User.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
