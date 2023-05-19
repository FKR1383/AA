package view.menu;

import controller.App;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.Paths;

import java.net.URL;

public class RegisterMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL registerMenuFXMLUrl = RegisterMenu.class.getResource(Paths.REGISTER_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(registerMenuFXMLUrl);
        Scene registerMenuScene = new Scene(borderPane);
        stage.setScene(registerMenuScene);
        stage.show();
    }

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    public void register(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
            emptyFieldAlert.setTitle("invalid register!");
            emptyFieldAlert.setHeaderText("register was not successful!");
            emptyFieldAlert.setContentText("username or password is empty!");
            emptyFieldAlert.showAndWait();
            return;
        }
        if (UserController.isUsernameExist(username)) {
            Alert usernameExistsAlert = new Alert(Alert.AlertType.ERROR);
            usernameExistsAlert.setTitle("invalid register!");
            usernameExistsAlert.setHeaderText("register was not successful!");
            usernameExistsAlert.setContentText("A user with this username exists!");
            usernameExistsAlert.showAndWait();
            return;
        }
        UserController.setTemporaryUsername(username);
        UserController.setTemporaryPassword(password);
        try {
            new SelectAvatarMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new LoginMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }
}
