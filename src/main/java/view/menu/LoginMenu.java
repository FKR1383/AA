package view.menu;

import controller.App;
import controller.DBController;
import controller.GameViewController;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.Paths;

import java.net.URL;

public class LoginMenu extends Application {
    public static Stage stageOfProgram;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    public static void main(String[] args) {
        App.run();
        launch(args);
    }
    public void start(Stage stage) throws Exception {
        stageOfProgram = stage;
        stage.getIcons().add(new Image(LoginMenu.class.getResource("/images/icons/aaIcon.png")
                .toExternalForm()));
        if (App.isStayLoggedIn()) {
            new MainMenu().start(stage);
        } else {
            URL loginMenuFXMLUrl = LoginMenu.class.getResource(Paths.LOGIN_MENU_FXML_FILE.getPath());
            BorderPane borderPane = FXMLLoader.load(loginMenuFXMLUrl);
            if (GameViewController.isBlackWhiteThemeOn) {
                borderPane.getStylesheets().remove(getClass().getResource(
                        Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
                borderPane.getStylesheets().add(getClass().getResource(
                        Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
            }
            Scene loginMenuScene = new Scene(borderPane);
            stage.setScene(loginMenuScene);
            stage.show();
        }
    }
    public void login(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Invalid login!" ,
                    "Login was not successful!" , "Username or password is empty!");
            return;
        }
        boolean isLoginSuccessful = UserController.login(username , password);
        if (isLoginSuccessful) {
            try {
                new MainMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        } else {
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Invalid login!",
                    "Login was not successful!" , "Username or password is incorrect!");
        }
    }

    public void goToRegister(MouseEvent mouseEvent) {
        try {
            new RegisterMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void playAsGuest(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

}
