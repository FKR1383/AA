package view.menu;


import controller.App;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import view.Paths;

import java.net.URL;

public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL mainMenuFXMLUrl = MainMenu.class.getResource(Paths.MAIN_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(mainMenuFXMLUrl);
        Scene mainMenuScene = new Scene(borderPane);
        stage.setScene(mainMenuScene);
        stage.show();
    }


    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void logout(MouseEvent mouseEvent) {
        UserController.logout();
        try {
            new LoginMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void settings(MouseEvent mouseEvent) {
        try {
            new SettingsMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void profileMenu(MouseEvent mouseEvent) {
        if (App.isStayLoggedIn()) {
            try {
                new ProfileMenu().start(LoginMenu.stageOfProgram);
            } catch (Exception e) {
                System.out.println("an error occurred");
            }
        }
    }

    public void resumingPreviousGame(MouseEvent mouseEvent) {
        if (App.isStayLoggedIn()) {

        }
    }

    public void scoreBoardMenu(MouseEvent mouseEvent) {
        try {
            new ScoreboardMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public void startNewGame(MouseEvent mouseEvent) {
        try {
            new GameMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
            System.out.println(e.getMessage());
        }
    }
}
