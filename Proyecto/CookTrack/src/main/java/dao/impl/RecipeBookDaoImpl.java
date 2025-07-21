package dao.impl;

import dao.interfaces.RecipeBookDao;
import infrastructure.DataBaseConnection;
import models.RecipeBook;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RecipeBookDaoImpl extends DaoImpl<RecipeBook, Integer> implements RecipeBookDao {
    public RecipeBookDaoImpl() {
        super(RecipeBook.class);
    }

    @Override
    public List<RecipeBook> findByUserId(int userId) {
        try (Session session = DataBaseConnection.getSession()) {
            String hql = "FROM RecipeBook rb WHERE rb.user.id = :userId";
            Query<RecipeBook> query = session.createQuery(hql, RecipeBook.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
}