package view.menu;

import controller.App;
import controller.GameController;
import controller.GameViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.Paths;

import java.net.URL;

public class SettingsMenu extends Application {
    @FXML
    private Button level3Button;
    @FXML
    private Button level2Button;
    @FXML
    private Button level1Button;
    @FXML
    private Label difficultyLevel;
    public static BorderPane settingsPane;

    @Override
    public void start(Stage stage) throws Exception {
        URL settingsMenuFXMLUrl = SettingsMenu.class.getResource(Paths.SETTINGS_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(settingsMenuFXMLUrl);
        settingsPane = borderPane;
        settingsPane.getChildren().add(GameViewController.createMuteUnmuteIcon());
        if (GameViewController.isBlackWhiteThemeOn) {
            borderPane.getStylesheets().remove(getClass().getResource(
                    Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
            borderPane.getStylesheets().add(getClass().getResource(
                    Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
        }
        Scene settingsMenuScene = new Scene(borderPane);
        stage.setScene(settingsMenuScene);
        stage.show();

    }

    @FXML
    public void initialize() {
        if (App.getCurrentGame() != null) {
            difficultyLevel.setText("difficulty : " + App.getCurrentGame().getDifficulty());
        } else {
            difficultyLevel.setText("difficulty : " + GameController.getDifficulty());
        }
        level1Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.setDifficulty(1);
                if (App.getCurrentGame() != null) {
                    difficultyLevel.setText("difficulty : " + App.getCurrentGame().getDifficulty());
                } else {
                    difficultyLevel.setText("difficulty : " + GameController.getDifficulty());
                }
            }
        });
        level2Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.setDifficulty(2);
                if (App.getCurrentGame() != null) {
                    difficultyLevel.setText("difficulty : " + App.getCurrentGame().getDifficulty());
                } else {
                    difficultyLevel.setText("difficulty : " + GameController.getDifficulty());
                }
            }
        });
        level3Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.setDifficulty(3);
                if (App.getCurrentGame() != null) {
                    difficultyLevel.setText("difficulty : " + App.getCurrentGame().getDifficulty());
                } else {
                    difficultyLevel.setText("difficulty : " + GameController.getDifficulty());
                }
            }
        });
    }

    public void changeTheme(MouseEvent mouseEvent) {
        GameViewController.isBlackWhiteThemeOn = !GameViewController.isBlackWhiteThemeOn;
        if (GameViewController.isBlackWhiteThemeOn) {
            settingsPane.getStylesheets().remove(getClass().getResource(
                    Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
            settingsPane.getStylesheets().add(getClass().getResource(
                    Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
        } else {
            settingsPane.getStylesheets().add(getClass().getResource(
                    Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
            settingsPane.getStylesheets().remove(getClass().getResource(
                    Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
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
