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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Game.Ball;
import model.Game.Game;
import view.Animation.TimerAnimation;
import view.Paths;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;



import java.io.File;
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
    public static Text degreeText;
    public static Scene scene;
    public static ProgressBar progressBarField;
    public static int numberOfMap = 2;
    public static MediaPlayer songPlayer;
    public static Text scoreText;
    public static Text numberOfBallsText;
    public static Label timer;
    public static TimerAnimation timerTransition;
    public static TextField secondUsernameField;
    @Override
    public void start(Stage stage) throws Exception {
        URL gameMenuFXMLUrl = GameMenu.class.getResource(Paths.GAME_MENU_FXML_FILE.getPath());
        Pane pane = FXMLLoader.load(gameMenuFXMLUrl);
        if (GameViewController.isBlackWhiteThemeOn) {
            pane.getStylesheets().remove(getClass().getResource(
                    Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
            pane.getStylesheets().add(getClass().getResource(
                    Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
        }
        gamePane = pane;
        Rectangle mute_unmuteIcon = GameViewController.createMuteUnmuteIcon();
        pane.getChildren().add(mute_unmuteIcon);

        gamePane.getChildren().add(GameViewController.createPlayPauseIcon());

        ballsText = GameViewController.createNumberField();

        pane.getChildren().add(ballsText);
        pane.getChildren().add(GameViewController.createNumberLabel());
        if (GameController.isDual) {
            Label secondUser = new Label("Second User : ");
            TextField secondUsername = new TextField("");
            secondUser.setTranslateY(100);
            secondUser.setTranslateX(150);
            secondUsername.setTranslateY(120);
            secondUsername.setTranslateX(150);
            secondUsernameField = secondUsername;
            pane.getChildren().add(secondUser);
            pane.getChildren().add(secondUsername);
        }

        createButtons();
        selectingMapHandling(map1Button , 1);
        selectingMapHandling(map2Button , 2);
        selectingMapHandling(map3Button , 3);

        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (GameController.isDual)
                    GameController.secondUsername = secondUsernameField.getText();
                GameController.createGame(numberOfMap , Integer.parseInt(ballsText.getText()));
                gamePane.getChildren().clear();
                gamePane.getChildren().add(GameController.getGame().getOuterDisk());
                gamePane.getChildren().add(GameController.getGame().getDiskWithNumber());
                gamePane.getChildren().add(GameViewController.createMuteUnmuteIcon());
                gamePane.getChildren().add(GameViewController.createMenuIcon());
                gamePane.getChildren().add(GameViewController.createPlayPauseIcon());
                timer = GameViewController.showTimer();
                gamePane.getChildren().add(timer);
                timerTransition = new TimerAnimation();
                addBallsToGame();
                addLabelsToPane();
                gamePane.requestFocus();
                gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().getName().equals("Space") &&
                                !GameController.getGame().isEnd()) {
                           runShootSound();
                            if (!GameController.isPhase4) {
                                GameController.shoot();
                            } else {
                                try {
                                    GameController.shoot2();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else if (keyEvent.getCode().getName().equals("Tab")) {
                            try {
                                if (GameMenu.progressBarField.getProgress() >= 0.95) {
                                    GameController.iceMode();
                                    GameMenu.progressBarField.setProgress(0);
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (keyEvent.getCode().getName().equals("Left")) {
                            if (GameController.isPhase4)
                                GameController.moveLeft();
                        } else if (keyEvent.getCode().getName().equals("Right")) {
                            if (GameController.isPhase4)
                                GameController.moveRight();
                        } else if (keyEvent.getCode().getName().equals("Enter")
                        && GameController.isDual && !GameController.getGame().isEnd()) {
                            runShootSound();
                            if (!GameController.isPhase4) {
                                GameController.shoot3();
                            } else {
                                try {
                                    GameController.shoot4();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                });

            }
        });
        Scene gameMenuScene = new Scene(pane);
        scene = gameMenuScene;
        stage.setScene(gameMenuScene);
        stage.show();
    }

    public void createButtons() {
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
        map1Button = map1;
        map2Button = map2;
        map3Button = map3;
        maps.getChildren().add(map1);
        maps.getChildren().add(map2);
        maps.getChildren().add(map3);
        gamePane.getChildren().add(maps);
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.setTranslateX(110);
        buttons.setTranslateY(500);
        Button start = GameViewController.createStartButton();
        startButton = start;
        Button back = GameViewController.createBackButton();
        buttons.getChildren().add(start);
        buttons.getChildren().add(back);
        gamePane.getChildren().add(buttons);
    }

    public void selectingMapHandling(Button button , int number) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                numberOfMap = number;
            }
        });
    }

    public void addBallsToGame() {
        int startY = 650;
        for (int i = 0; i != GameController.getGame().getNumberOfBalls(); i++) {
            Ball stackBall = GameController.getGame().getBalls().get(i);
            stackBall.setTranslateX(215);
            stackBall.setTranslateY(startY);
            stackBall.getText().setTranslateX(208);
            stackBall.getText().setTranslateY(startY+3);
            startY += 50;
            gamePane.getChildren().add(GameController.getGame().getBalls().get(i));
            gamePane.getChildren().add(GameController.getGame().getBalls().get(i).getText());
        }
        if (GameController.isDual) {
            startY = 150;
            for (int i = GameController.game.getNumberOfBalls()
                 ; i != 2*GameController.getGame().getNumberOfBalls(); i++) {
                Ball stackBall = GameController.getGame().getBalls().get(i);
                stackBall.setTranslateX(215);
                stackBall.setTranslateY(startY);
                stackBall.getText().setTranslateX(208);
                stackBall.getText().setTranslateY(startY+3);
                startY -= 50;
                gamePane.getChildren().add(GameController.getGame().getBalls().get(i));
                gamePane.getChildren().add(GameController.getGame().getBalls().get(i).getText());
            }
        }
    }

    public void addLabelsToPane() {
        HBox iceShowerHbox = new HBox();
        iceShowerHbox.setTranslateX(20);
        iceShowerHbox.setSpacing(5);
        Text text = new Text(20 , 5,"ice progress : " );
        ProgressBar progressBar = new ProgressBar(0);
        Text degreeLabelText = new Text(45 , 5 , "degree : ");
        Text degreeT = new Text(50 , 5 ,"0");
        Text scoreT = new Text(65 , 5 , "score : 0");
        Text numberOfBalls = new Text(325 , 50 , "number of balls : ");
        degreeText = degreeT;
        progressBarField = progressBar;
        scoreText = scoreT;
        iceShowerHbox.getChildren().add(text);
        iceShowerHbox.getChildren().add(progressBar);
        iceShowerHbox.getChildren().add(degreeLabelText);
        iceShowerHbox.getChildren().add(degreeT);
        iceShowerHbox.getChildren().add(scoreText);
        gamePane.getChildren().add(numberOfBalls);
        numberOfBallsText = numberOfBalls;
        GameMenu.numberOfBallsText.setText("number of balls : " + GameController.game.getBalls().size());
        numberOfBallsText.setFill(Color.RED);
        gamePane.getChildren().add(iceShowerHbox);
    }

    public static void playMusic() {
        Media media = new Media(new File(Paths.MUSICS_PATH.getPath() + "2.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(-1);
        if (GameMenu.isMute)
            mediaPlayer.setMute(true);
        songPlayer = mediaPlayer;
    }

    public void runShootSound() {
        Media media = new Media(new File(Paths.MUSICS_PATH.getPath() + "clickSound.wav")
                .toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(1);
        if (GameMenu.isMute)
            mediaPlayer.setMute(true);
        mediaPlayer.play();
    }


    @FXML
    public void initialize() {

    }

}
