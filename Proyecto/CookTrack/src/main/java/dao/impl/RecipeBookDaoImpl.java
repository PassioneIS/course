package dao.impl;

import dao.interfaces.RecipeBookDao;
import models.RecipeBook;

import java.util.List;

public class RecipeBookDaoImpl extends DaoImpl<RecipeBook, Integer> implements RecipeBookDao {
    public RecipeBookDaoImpl() {
        super(RecipeBook.class);
    }

    @Override
    public List<RecipeBook> findByUserId(int userId) {
        return List.of();
    }
}
