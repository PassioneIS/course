import controllers.LoginController;
import infrastructure.DataBaseConnection;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        showLoadingScreen();
    }

    private void showLoadingScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loading.fxml"));
            Scene splashScene = new Scene(loader.load());

            primaryStage.setTitle("CookTrack");
            primaryStage.setScene(splashScene);
            primaryStage.centerOnScreen();
            primaryStage.show();

            // Inicializa Hibernate
            Task<Void> initTask = new Task<>() {
                @Override
                protected Void call() {
                    DataBaseConnection.getSessionFactory(); //Cargar DB
                    return null;
                }
            };

            initTask.setOnSucceeded(e -> showLogin());

            new Thread(initTask).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
            Scene scene = new Scene(loader.load());

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}