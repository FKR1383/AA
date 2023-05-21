package view.menu;

import controller.App;
import controller.GameViewController;
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
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @Override
    public void start(Stage stage) throws Exception {
        URL registerMenuFXMLUrl = RegisterMenu.class.getResource(Paths.REGISTER_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(registerMenuFXMLUrl);
        Scene registerMenuScene = new Scene(borderPane);
        stage.setScene(registerMenuScene);
        stage.show();
    }

    public void register(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Invalid register!" ,
                    "Register was not successful!" , "Username or password is empty!");
            return;
        }
        if (UserController.isUsernameExist(username)) {
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Invalid register!" ,
                    "Register was not successful!" , "A user with this username exists!");
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
