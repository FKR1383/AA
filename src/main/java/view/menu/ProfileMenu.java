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
import model.User;
import view.Paths;

import java.net.URL;

public class ProfileMenu extends Application {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationField;

    @Override
    public void start(Stage stage) throws Exception {
        URL profileMenuFXMLUrl = ProfileMenu.class.getResource(Paths.PROFILE_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(profileMenuFXMLUrl);
        if (GameViewController.isBlackWhiteThemeOn) {
            borderPane.getStylesheets().remove(getClass().getResource(
                    Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
            borderPane.getStylesheets().add(getClass().getResource(
                    Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
        }
        Scene profileMenuScene = new Scene(borderPane);
        stage.setScene(profileMenuScene);
        stage.show();
    }

    public void logout(MouseEvent mouseEvent) {
        UserController.logout();
        GameViewController.alertShowing(Alert.AlertType.INFORMATION , "Logged out!" ,
                "Logged out!" , "Logged out successfully!");
        try {
            new LoginMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) {
        UserController.deleteUser();
        GameViewController.alertShowing(Alert.AlertType.INFORMATION , "Delete Account was successful!",
                "Account was deleted successfully!" , "Account was deleted!");
        try {
            new LoginMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void changeUsername(MouseEvent mouseEvent) {
        String newUsername = usernameField.getText();
        if (newUsername == null || newUsername.equals("")) {
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Change information Error" ,
                    "Empty field" , "New username field is empty!");
            return;
        }
        if (UserController.isUsernameExist(newUsername)) {
            GameViewController.alertShowing(Alert.AlertType.ERROR , "Change information Error" ,
                    "Username exists!" , "New username exists");
            return;
        }
        UserController.changeUsername(newUsername);
        GameViewController.alertShowing(Alert.AlertType.INFORMATION , "Change information was successful!",
                "Username was changed!" , "Username was changed successfully!");
        try {
            new ProfileMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void changePassword(MouseEvent mouseEvent) {
        String newPassword = passwordField.getText();
        String passwordConfirmation = confirmationField.getText();
        if (newPassword == null || passwordConfirmation == null ||
        newPassword.equals("") || passwordConfirmation.equals("")) {
            GameViewController.alertShowing(Alert.AlertType.ERROR ,"Change information failed!" ,
                    "Change password was not successful!" ,
                    "Password field or new password field is empty!");
            return;
        }
        if (!newPassword.equals(passwordConfirmation)) {
            GameViewController.alertShowing(Alert.AlertType.ERROR ,"Change information failed!",
                    "Change password was not successful!" ,
                    "Password confirmation and password doesn't match!");
            return;
        }
        UserController.changePassword(newPassword);
        GameViewController.alertShowing(Alert.AlertType.INFORMATION , "Change information was successful!",
                "Password changed successfully!" ,"Password changed successfully!");
        try {
            new ProfileMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void changeAvatar(MouseEvent mouseEvent) {
        try {
            SelectAvatarMenu.setIsChangingAvatarMenuActive(true);
            new SelectAvatarMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }
}
