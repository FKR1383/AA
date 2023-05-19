package view.menu;

import controller.App;
import controller.GameController;
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

    @Override
    public void start(Stage stage) throws Exception {
        URL settingsMenuFXMLUrl = SettingsMenu.class.getResource(Paths.SETTINGS_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(settingsMenuFXMLUrl);
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
                try {
                    new MainMenu().start(LoginMenu.stageOfProgram);
                } catch (Exception e) {
                    System.out.println("an error occurred");
                }
            }
        });
        level2Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.setDifficulty(2);
                try {
                    new MainMenu().start(LoginMenu.stageOfProgram);
                } catch (Exception e) {
                    System.out.println("an error occurred");
                }
            }
        });
        level3Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.setDifficulty(3);
                try {
                    new MainMenu().start(LoginMenu.stageOfProgram);
                } catch (Exception e) {
                    System.out.println("an error occurred");
                }
            }
        });
    }
}
