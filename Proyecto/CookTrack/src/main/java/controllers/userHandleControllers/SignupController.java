package controllers.userHandleControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import services.SignupService;
import services.LoginService;

public class SignupController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    @FXML
    private Label invalidUserLabel;

    @FXML
    private Label invalidPasswordLabel;

    @FXML
    private Label invalidConfirmPasswordLabel;

    private final SignupService signupService = new SignupService();
    private final LoginService loginService = new LoginService();

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

        confirmPasswordField.setOnKeyPressed(event -> {
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
        System.out.println("SignupController: handleSignup()");

        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        boolean hasErrors = false;

        invalidUserLabel.setVisible(false);
        invalidPasswordLabel.setVisible(false);
        invalidConfirmPasswordLabel.setVisible(false);

        if (signupService.userExists(username)) {
            invalidUserLabel.setVisible(true);
            hasErrors = true;
        }

        if (!signupService.validPassword(password)) {
            invalidPasswordLabel.setVisible(true);
            hasErrors = true;
        }

        if (!signupService.passwordMatch(password, confirmPassword)) {
            invalidConfirmPasswordLabel.setVisible(true);
            hasErrors = true;
        }

        if (hasErrors) {
            System.out.println("Usuario inválido, contraseña inválida o confirmación incorrecta.");
            return;
        }

        boolean signupSuccess = signupService.signUp(username, password, confirmPassword);
        boolean loginSuccess = loginService.logIn(username, password);

        if (signupSuccess && loginSuccess) {
            System.out.println("Signup exitoso!");

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
            System.out.println("Error al registrar o iniciar sesión.");
        }


    }

    @FXML
    private void returnToLoginScene(Event event){
        System.out.println("SignupController: returnToLoginScene()");
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

