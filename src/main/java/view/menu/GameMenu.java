package view.menu;

import controller.GameController;
import controller.GameViewController;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game.Ball;
import model.Game.Game;
import view.Paths;


import java.net.URL;

public class GameMenu extends Application {
    @FXML
    public static Pane gamePane;
    public static boolean isMute = false;
    public static boolean isPlay = true;
    public static Button startButton;
    public static TextField ballsText;
    public static Button map1Button;
    public static Button map2Button;
    public static Button map3Button;
    public static Scene scene;
    public static ProgressBar progressBarField;

    public static int numberOfMap = 2;
    @Override
    public void start(Stage stage) throws Exception {
        URL gameMenuFXMLUrl = GameMenu.class.getResource(Paths.GAME_MENU_FXML_FILE.getPath());
        Pane pane = FXMLLoader.load(gameMenuFXMLUrl);
        Rectangle mute_unmuteIcon = GameViewController.createMuteUnmuteIcon();
        pane.getChildren().add(mute_unmuteIcon);
        /*Rectangle play_pauseIcon = GameViewController.createPlayPauseIcon();
        pane.getChildren().add(play_pauseIcon);*/
        /*Label numberLabel = new Label();
        numberLabel.setBounds(20 , 400 , 200 , 20);
        pane.getChildren().add(numberOfBallsField);
        pane.getChildren().add(numberLabel);*/
        ballsText = GameViewController.createNumberField();
        pane.getChildren().add(ballsText);
        pane.getChildren().add(GameViewController.createNumberLabel());
        HBox maps = new HBox();
        maps.setAlignment(Pos.CENTER);
        maps.setSpacing(50);
        maps.setTranslateX(25);
        maps.setTranslateY(450);
        Button map1 = new Button();
        Button map2 = new Button();
        Button map3=  new Button();
        map1.setText("map1");
        map2.setText("map2");
        map3.setText("map3");
        map1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                numberOfMap = 1;
            }
        });
        map2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                numberOfMap = 2;
            }
        });
        map3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                numberOfMap = 3;
            }
        });
        map1Button = map1;
        map2Button = map2;
        map3Button = map3;
        maps.getChildren().add(map1);
        maps.getChildren().add(map2);
        maps.getChildren().add(map3);
        pane.getChildren().add(maps);
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.setTranslateX(110);
        buttons.setTranslateY(500);
        Button start = GameViewController.createStartButton();
        startButton = start;
        gamePane = pane;
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameController.createGame(numberOfMap , Integer.parseInt(ballsText.getText()));
                gamePane.getChildren().clear();
                gamePane.getChildren().add(GameController.getGame().getDiskWithNumber());
                int startY = 650;
                for (int i = 0; i != GameController.getGame().getBalls().size(); i++) {
                    Ball ball = (Ball)(GameController.getGame().getBalls().get(i));
                    Ball stackBall = GameController.getGame().getBalls().get(i);
                    stackBall.setTranslateX(215);
                    stackBall.setLayoutY(startY);
                    stackBall.getText().setTranslateX(208);
                    stackBall.getText().setLayoutY(startY+3);
                    startY += 50;
                    gamePane.getChildren().add(GameController.getGame().getBalls().get(i));
                    gamePane.getChildren().add(GameController.getGame().getBalls().get(i).getText());
                }
                gamePane.requestFocus();
                gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().getName().equals("Space")) {
                            GameController.shoot();
                        } else if (keyEvent.getCode().getName().equals("Tab")) {
                            try {
                                if (GameMenu.progressBarField.getProgress() >= 0.95) {
                                    GameController.iceMode();
                                    GameMenu.progressBarField.setProgress(0);
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                HBox iceShowerHbox = new HBox();
                iceShowerHbox.setTranslateX(20);
                Text text = new Text(20 , 5,"ice progress : " );
                ProgressBar progressBar = new ProgressBar(0);
                progressBarField = progressBar;
                iceShowerHbox.getChildren().add(text);
                iceShowerHbox.getChildren().add(progressBar);
                gamePane.getChildren().add(iceShowerHbox);
            }
        });
        Button back = GameViewController.createBackButton();
        buttons.getChildren().add(start);
        buttons.getChildren().add(back);
        pane.getChildren().add(buttons);
        Scene gameMenuScene = new Scene(pane);
        scene = gameMenuScene;
        stage.setScene(gameMenuScene);
        stage.show();
    }

    @FXML
    public void initialize() {


    }

}
