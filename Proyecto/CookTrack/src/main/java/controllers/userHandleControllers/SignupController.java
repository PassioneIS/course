package controllers.userHandleControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import services.SignupService;

public class SignupController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    private final SignupService signupService = new SignupService();

    public void initialize(){
        // Permitir ENTER en passwordField
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSignup(event);
            }
        });

        // Permitir ENTER en usernameField también si deseas
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSignup(event);
            }
        });

        createButton.setOnAction(event -> {
            handleSignup(event);
        });

        backButton.setOnAction(event -> {
            returnToLoginScene(event);
        });
    }

    @FXML
    private void handleSignup(Event event){
        System.out.println("User Created");
    }

    @FXML
    private void returnToLoginScene(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual y cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

