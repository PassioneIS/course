package controllers;

import infrastructure.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    @FXML
    private BorderPane mainScene;

    @FXML
    private Button recipeServiceButton;
    @FXML
    private Button calendarServiceButton;
    @FXML
    private Button feedServiceButtton;
    @FXML
    private Button shoppingListServiceButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private MenuItem logOutButton;

    private final Map<String, Parent> loadedViews = new HashMap<>();

    @FXML
    public void initialize() {

        welcomeLabel.setText("Bienvenido " + SessionManager.getInstance().getCurrentUser().getName());

        shoppingListServiceButton.setOnAction(e -> {
            goToShoppinListService();
        });

        recipeServiceButton.setOnAction(e -> {
            changeScene("/views/RecipesViews/recipesView.fxml");
        });

        feedServiceButtton.setOnAction(e->{
            changeScene("/views/FeedView.fxml");
        });

        calendarServiceButton.setOnAction(e -> {
            changeScene("/views/CalendarView.fxml");
        });

        logOutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            logOut(e);
        });

        changeScene("/views/FeedView.fxml");
    }


    public void changeScene(String fxml) {
        try {
            Parent view;
            if (loadedViews.containsKey(fxml)) {
                view = loadedViews.get(fxml);
            } else {
                view = FXMLLoader.load(getClass().getResource(fxml));
                loadedViews.put(fxml, view);
            }
            mainScene.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logOut(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToShoppinListService() {
        changeScene("/views/ShoppingListViews/shoppingList.fxml");
    }
}