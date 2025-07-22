package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import models.Post; // Asegúrate de importar Post
import services.FeedService;

import java.util.List;

import javafx.scene.control.ListCell;
import java.time.format.DateTimeFormatter;

public class FeedController {

    @FXML
    private ListView<Post> recipeListView; // <<-- CAMBIO AQUÍ

    @FXML
    private ComboBox<String> sortComboBox;

    private final FeedService feedService;

    public FeedController() {
        this.feedService = new FeedService();
    }

    @FXML
    public void initialize() {
        sortComboBox.setItems(FXCollections.observableArrayList("Más recientes", "Mayor calificación"));
        recipeListView.setCellFactory(listView -> new RecipeListCell()); // Asegúrate que esta línea esté aquí
        loadPosts(); // Cambiamos el nombre del método para más claridad
    }

    @FXML
    private void loadPosts() {
        // El servicio ahora nos da una lista de Posts
        List<Post> posts = feedService.getPosts(null);
        System.out.println(posts.size());// Llamamos al método correcto
        ObservableList<Post> observablePosts = FXCollections.observableArrayList(posts);
        recipeListView.setItems(observablePosts);

        //System.out.println(posts.get(0).getRecipe()); // Asegúrate que tenga contenido
        //observablePosts.forEach(post -> System.out.println(post.getRecipe())); // ¿Muestra algo?
    }

    private class RecipeListCell extends ListCell<Post> {
        @Override
        protected void updateItem(Post post, boolean empty) {
            super.updateItem(post, empty);
            if (empty || post == null) {
                setText(null);
                setGraphic(null);
            } else {

                DateTimeFormatter formated = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                String postRecipeName = post.getRecipe().getName();
                String postRating = Double.toString(post.getAverageRating());
                String postDate = (post.getPublishedAt()).format(formated);

                setText("Receta " + postRecipeName + "              Rating:" + postRating + "             Fecha de publicación:" + postDate );
            }
        }
    }


}