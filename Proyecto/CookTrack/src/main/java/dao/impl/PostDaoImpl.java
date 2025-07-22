package dao.impl;

import dao.interfaces.PostDao;
import models.Post;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import infrastructure.DataBaseConnection;

public class PostDaoImpl extends DaoImpl<Post, Integer> implements PostDao {
    public PostDaoImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findByUserId(int userId) {
        return List.of();
    }

    public List<Post> findAll(){
        try (Session session = DataBaseConnection.getSessionFactory().openSession()){
            return session.createQuery("""
            SELECT i
            FROM Post i
        """, Post.class).getResultList();
        }
    }
}
