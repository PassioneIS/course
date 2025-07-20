package dao.interfaces;

import dao.DAO;
import models.RecipeBook;

import java.util.List;

public interface RecipeBookDao extends DAO<RecipeBook, Integer> {
    List<RecipeBook> findByUserId(int userId);
}