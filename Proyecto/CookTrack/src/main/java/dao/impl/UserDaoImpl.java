package dao.impl;

import dao.interfaces.UserDao;
import infrastructure.DataBaseConnection;
import models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDaoImpl extends DaoImpl<User, Integer> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByName(String name) {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE name = :name", User.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void createUser(User user) {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error al guardar Usuario /UserDaoImpl.createUser");
            ex.printStackTrace();
        }
    }
}
