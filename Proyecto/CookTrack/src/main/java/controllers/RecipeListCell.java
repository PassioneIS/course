package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import models.Post;

import java.io.IOException;

public class RecipeListCell extends ListCell<Post> {

    @FXML private Label titleLabel;
    @FXML private Label authorLabel;
    @FXML private Label ratingLabel;
    @FXML private HBox hBox;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);

        if (empty || post == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/views/RecipeCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            titleLabel.setText(post.getRecipe().getName());
            authorLabel.setText("Por: " + post.getUser().getName());
            ratingLabel.setText(String.format("%.1f ★", post.getAverageRating()));

            setText(null);
            setGraphic(hBox);
        }
    }
}