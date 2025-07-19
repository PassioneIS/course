package controllers;

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
import services.LoginService;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createButton;

    private final LoginService loginService = new LoginService();

    @FXML
    public void initialize() {
        // Permitir ENTER en passwordField
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(event);
            }
        });

        // Permitir ENTER en usernameField también si deseas
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(event);
            }
        });

        loginButton.setOnAction(event -> {
            handleLogin(event);
        });

        createButton.setOnAction(event -> {
            openSignUpScene(event);
        });
    }

    @FXML
    private void handleLogin(Event event) {
        System.out.println("LoginController: handleLogin()");

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean success = loginService.logIn(username, password);
        if (success) {
            System.out.println("Login exitoso!");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/mainView.fxml"));
                Scene scene = new Scene(loader.load());

                // Obtener el Stage actual y cambiar la escena
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setMaximized(true);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void openSignUpScene(Event event) {
        System.out.println("LoginController: openSignUpScene()");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserHandleViews/signupView.fxml"));
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
