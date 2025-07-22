package dao.impl;

import dao.DAO;
import infrastructure.DataBaseConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public class DaoImpl<T, ID extends Serializable> implements DAO<T, ID> {

    private final Class<T> entityType;

    public DaoImpl(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public void save(T entity) {
        Transaction tx = null;
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e) {
            /*if (tx != null && tx.getStatus().canRollback()) {
                tx.rollback();
            }*/
            e.printStackTrace();
        }
    }

    @Override
    public T findById(ID id) {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            return session.find(entityType, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            return session.createQuery("FROM " + entityType.getName(), entityType).getResultList();
        }
    }

    @Override
    public void update(T entity) {
        Transaction tx = null;
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T entity) {
        Transaction tx = null;
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}