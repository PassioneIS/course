package dao.interfaces;

import dao.DAO;
import models.Rating;
import models.RatingId;

import java.util.List;

public interface RatingDao extends DAO<Rating, RatingId> {
    List<Rating> findByPostId(int postId);

    double findAverageRatingByPostId(int postId);
}