package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import models.Post; // Asegúrate de importar Post
import services.FeedService;

import java.util.List;

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

    private void loadPosts() {
        // El servicio ahora nos da una lista de Posts
        List<Post> posts = feedService.getPosts(null); // Llamamos al método correcto
        ObservableList<Post> observablePosts = FXCollections.observableArrayList(posts);
        recipeListView.setItems(observablePosts);
    }
}