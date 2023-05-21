package view.menu;

import controller.App;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        Scene profileMenuScene = new Scene(borderPane);
        stage.setScene(profileMenuScene);
        stage.show();
    }

    public void logout(MouseEvent mouseEvent) {
        UserController.logout();
        try {
            new LoginMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) {
        UserController.deleteUser();
        try {
            new LoginMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void changeUsername(MouseEvent mouseEvent) {
        String newUsername = usernameField.getText();
        if (newUsername == null || newUsername.equals("")) {
            // TODO: message
            return;
        }
        if (UserController.isUsernameExist(newUsername)) {
            // TODO: message
            return;
        }
        UserController.changeUsername(newUsername);
        // TODO: messsage
    }

    public void changePassword(MouseEvent mouseEvent) {
        // old password giving???
        String newPassword = passwordField.getText();
        String passwordConfirmation = confirmationField.getText();
        if (newPassword == null || passwordConfirmation == null ||
        newPassword.equals("") || passwordConfirmation.equals("")) {
            // TODO: message
            return;
        }
        if (!newPassword.equals(passwordConfirmation)) {
            // TODO: message
            return;
        }
        UserController.changePassword(newPassword);
        // TODO: message
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
