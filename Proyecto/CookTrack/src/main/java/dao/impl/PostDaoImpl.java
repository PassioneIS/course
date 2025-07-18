package dao.impl;

import dao.interfaces.PostDao;
import models.Post;
import java.util.List;

public class PostDaoImpl extends DaoImpl<Post, Integer> implements PostDao {
    public PostDaoImpl() {
        super(Post.class);
    }

    @Override
    public List<Post> findByUserId(int userId) {
        return List.of();
    }
}
