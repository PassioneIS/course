package dao.impl;

import dao.interfaces.RecipeBookDao;
import infrastructure.DataBaseConnection;
import models.RecipeBook;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import infrastructure.DataBaseConnection;

import models.User;

public class RecipeBookDaoImpl extends DaoImpl<RecipeBook,Integer>  implements RecipeBookDao {
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

    @Override
    public RecipeBook findByUser(User user) {
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Query<RecipeBook> query = session.createQuery("FROM RecipeBook WHERE user = :user", RecipeBook.class);
            query.setParameter("user", user);
            return query.uniqueResult();
        }
    }

    @Override
    public RecipeBook createRecipeBook(User user){
        RecipeBook recipeBook = new RecipeBook();
        recipeBook.setUser(user);
        return recipeBook;
    }

    @Override
    public void saveRecipeBook(RecipeBook recipeBook){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            System.out.println("Persistiendo: " + recipeBook);

            session.persist(recipeBook);
            transaction.commit();

            System.out.println("Se guardo el recipe book:" + recipeBook);
        }
        catch (Exception e) {
            System.err.println("Error al guardar el recipe book:" + recipeBook);
            e.printStackTrace();
        }
    }

}
