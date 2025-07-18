package dao.interfaces;


import dao.DAO;
import models.Post;
import java.util.List;

public interface PostDao extends DAO<Post, Integer> {
    List<Post> findByUserId(int userId);
}
