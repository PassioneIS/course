package services;

import dao.impl.PostDaoImpl;
import dao.interfaces.PostDao;
import models.Ingredient;
import models.Post;
import models.Recipe;
import models.User;

import java.util.Collections;
import java.util.List;

public class FeedService {

    private final PostDao postDao;


    public FeedService() {
        this.postDao = new PostDaoImpl();
    }

    public void publish(User user, Recipe recipe) {

    }

    public List<Post> getPosts(User user) {

        try {
            return postDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    public void importRecipe(Recipe recipe) {

    }

    public void filterPostsByIngredient(List<Post> posts, Ingredient ing) {

    }

    public void ratePost(Post post) {

    }

    public void sortPostsByRating(List<Post> posts) {

    }
}