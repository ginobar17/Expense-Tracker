import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AuthenticationUI {
    private VBox container;

    public AuthenticationUI(Stage primaryStage) {
        container = new VBox(20);
        container.setAlignment(Pos.CENTER);

        VBox loginBox = new VBox(15);
        loginBox.setMaxWidth(350);
        loginBox.setPadding(new Insets(30));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);");

        Label title = new Label("🔐 Login");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Finance Tracker");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        usernameInput.setStyle("-fx-padding: 12; -fx-font-size: 14px;");

        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setStyle("-fx-padding: 12; -fx-font-size: 14px;");

        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12;");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");

        Label infoLabel = new Label("Default: admin / admin123");
        infoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");

        loginBtn.setOnAction(e -> {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("⚠ Please fill all fields");
                return;
            }

            if (DataManager.authenticateUser(username, password)) {
                messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                messageLabel.setText("✓ Login successful!");

                FinanceTrackerUI trackerUI = new FinanceTrackerUI();
                Scene mainScene = new Scene(trackerUI.getView(), 1100, 700);
                primaryStage.setScene(mainScene);
                primaryStage.setTitle("Finance Tracker - " + username);
            } else {
                messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                messageLabel.setText("✗ Invalid credentials");
            }
        });

        passwordInput.setOnAction(e -> loginBtn.fire());

        loginBox.getChildren().addAll(title, subtitle, usernameInput, passwordInput,
                loginBtn, messageLabel, infoLabel);
        container.getChildren().add(loginBox);
    }

    public VBox getView() {
        return container;
    }
}
