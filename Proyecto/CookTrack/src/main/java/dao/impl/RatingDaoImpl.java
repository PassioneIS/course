package dao.impl;

import dao.interfaces.RatingDao;
import models.Rating;
import models.RatingId;

import java.util.List;

public class RatingDaoImpl extends DaoImpl<Rating, RatingId> implements RatingDao {

    public RatingDaoImpl() {
        super(Rating.class);
    }

    @Override
    public List<Rating> findByPostId(int postId) {
        return List.of();
    }

    @Override
    public double findAverageRatingByPostId(int postId) {
        return 0;
    }
}
