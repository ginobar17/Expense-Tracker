import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize database
        DataManager.initializeUsersTable();
        DataManager.initializeTransactionsTable();
        DataManager.createDefaultUser();

        // Setup login screen
        AuthenticationUI loginUI = new AuthenticationUI(primaryStage);
        Scene loginScene = new Scene(loginUI.getView(), 500, 400);

        primaryStage.setTitle("Finance Tracker - Login");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
