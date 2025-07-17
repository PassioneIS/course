package controllers;

import infrastructure.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

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
    private Button logOutButton;

    @FXML
    public void initialize(){

        welcomeLabel.setText("Bienvenido " + SessionManager.getInstance().getCurrentUser().getName());

        shoppingListServiceButton.setOnAction(e->{
            changeScene("/views/ShoppingListViews/shoppingList.fxml");
        });

        logOutButton.setOnAction(e->{
            SessionManager.getInstance().logout();
            logOut(e);
        });
    }
    public void changeScene(String fxml) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(fxml));
            mainScene.setCenter(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logOut(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual y cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setMaximized(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}