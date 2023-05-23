package controller;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Game.Game;
import view.Paths;
import view.menu.GameMenu;
import view.menu.LoginMenu;
import view.menu.MainMenu;

import java.io.File;

import static view.menu.GameMenu.*;

public class GameViewController {

    public static boolean isBlackWhiteThemeOn = false;
    public static Rectangle createMuteUnmuteIcon() {
        Rectangle icon = new Rectangle(380 , 10 , 20 , 20);
        if (isMute == false) {
            icon.setFill(new ImagePattern(new Image
                    (GameViewController.class.getResource
                            ("/images/icons/unmuteIcon.png").toExternalForm())));
        } else {
            icon.setFill(new ImagePattern(new Image
                    (GameViewController.class.getResource
                            ("/images/icons/muteIcon.jpg").toExternalForm())));
        }
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isMute == false) {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/muteIcon.jpg").toExternalForm())));
                    songPlayer.setMute(true);
                    isMute = true;
                } else {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/unmuteIcon.png").toExternalForm())));
                    songPlayer.setMute(false);
                    isMute = false;
                }
            }
        });
        return icon;
    }

    public static Rectangle createMenuIcon() {
        Rectangle icon = new Rectangle(415 , 10 , 20 , 20);
            icon.setFill(new ImagePattern(new Image
                    (GameViewController.class.getResource
                            ("/images/icons/menuIcon.png").toExternalForm())));
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuIconEvents();
            }
        });
        return icon;
    }

    public static void menuIconEvents() {
        gamePane.setEffect(new GaussianBlur());

        VBox pauseRoot = new VBox(5);
        pauseRoot.getChildren().add(new Label("Paused"));
        pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setPadding(new Insets(20));

        Button resume = new Button("Resume");
        Button restart = new Button("Restart");
        Button saveGame = new Button("Save Game");
        Button helpKeys = new Button("Help (Keys)");
        Button changeMusic = new Button("Change Music");
        Button quitGame = new Button("Quit Game");

        pauseRoot.getChildren().add(resume);
        pauseRoot.getChildren().add(restart);
        pauseRoot.getChildren().add(quitGame);
        pauseRoot.getChildren().add(saveGame);
        pauseRoot.getChildren().add(helpKeys);
        pauseRoot.getChildren().add(changeMusic);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(LoginMenu.stageOfProgram);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
        setAngleInPauseMode();

        resume.setOnAction(event -> {
            gamePane.setEffect(null);
            if (!GameController.getGame().isEnd()) {
                GameController.rotateTransition.play();
                timerTransition.play();
            }
            GameController.time = System.currentTimeMillis();
            popupStage.hide();
        });

        restart.setOnAction(e -> {
            restartEvent(popupStage);
        });

        quitGame.setOnAction(e -> {
            System.exit(0);
        });

        helpKeys.setOnAction(e -> {
            helpKeyEvents();
        });

        changeMusic.setOnAction(e -> {
            changeMusicEvents();
        });



        popupStage.show();
    }

    public static void setAngleInPauseMode(){
        GameController.nowAngle += (double) (System.currentTimeMillis() - GameController.time) /
                GameController.timeOfRotation * 360 * GameController.signOfRotation;
        GameController.rotateTransition.pause();
        timerTransition.pause();
    }

    public static void changeMusicEvents() {
        VBox pauseRoot = new VBox(5);
        pauseRoot.getChildren().add(new Label("Paused"));
        pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        pauseRoot.setAlignment(Pos.CENTER);
        pauseRoot.setPadding(new Insets(20));


        Button music1 = new Button("Sacrifise - The Weeknd");
        Button music2 = new Button("Industry Baby - Lil Nas X");
        Button music3 = new Button("Old Town Road - Lil Nas X");
        Button okButton = new Button("OK");

        setHandleForChangeMusicButtons(music1 , 1);
        setHandleForChangeMusicButtons(music2 , 2);
        setHandleForChangeMusicButtons(music3 , 3);

        pauseRoot.getChildren().add(okButton);
        pauseRoot.getChildren().add(music1);
        pauseRoot.getChildren().add(music2);
        pauseRoot.getChildren().add(music3);

        Stage popupStage2 = new Stage(StageStyle.TRANSPARENT);
        popupStage2.initOwner(LoginMenu.stageOfProgram);
        popupStage2.initModality(Modality.APPLICATION_MODAL);
        popupStage2.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
        popupStage2.show();
        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popupStage2.hide();
            }
        });
    }

    public static void helpKeyEvents(){
        VBox pauseRoot2 = new VBox(5);
        pauseRoot2.getChildren().add(new Label("Paused"));
        pauseRoot2.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        pauseRoot2.setAlignment(Pos.CENTER);
        pauseRoot2.setPadding(new Insets(20));
        pauseRoot2.getChildren().add(new Label("Shoot : Space\nIce mode: " +
                "Tab\nMove Right(Phase 4): " +
                "Right arrow\nMove Left(Phase 4): Left arrow"));
        Button okButton = new Button("OK");
        pauseRoot2.getChildren().add(okButton);
        Stage popupStage2 = new Stage(StageStyle.TRANSPARENT);
        popupStage2.initOwner(LoginMenu.stageOfProgram);
        popupStage2.initModality(Modality.APPLICATION_MODAL);
        popupStage2.setScene(new Scene(pauseRoot2, Color.TRANSPARENT));
        popupStage2.show();
        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                popupStage2.hide();
            }
        });
    }

    public static void setHandleForChangeMusicButtons(Button button , int numberOfSong) {
        button.setOnAction(e -> {
            Media song = new Media(new File(Paths.MUSICS_PATH.getPath() + numberOfSong + ".mp3")
                    .toURI().toString());
            songPlayer.stop();
            MediaPlayer mediaPlayer = new MediaPlayer(song);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(-1);
            if (GameMenu.isMute)
                mediaPlayer.setMute(true);
            else if (!isPlay) {
                mediaPlayer.pause();
            }
            songPlayer = mediaPlayer;
        });
    }

    public static void restartEvent(Stage popupStage) {
        GameController.game.setDiskWithNumber(null);
        GameController.game.setBalls(null);
        gamePane.setEffect(null);
        popupStage.hide();
        try {
            new GameMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }

    public static Rectangle createPlayPauseIcon() {
        Rectangle icon = new Rectangle(350 , 10 , 20 , 20);
        icon.setFill(new ImagePattern(new Image
                (GameViewController.class.getResource("/images/icons/playIcon.png").toExternalForm())));
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isPlay) {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/pauseIcon.png").toExternalForm())));
                    isPlay = false;
                    songPlayer.pause();
                } else {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/playIcon.png").toExternalForm())));
                    isPlay = true;
                    songPlayer.play();
                }
            }
        });
        return icon;
    }

    public static TextField createNumberField() {
        TextField numberOfBallsField = new TextField();
        numberOfBallsField.setScaleX(1);
        numberOfBallsField.setScaleY(1);
        numberOfBallsField.setTranslateX(220);
        numberOfBallsField.setTranslateY(400);
        return numberOfBallsField;
    }

    public static Label createNumberLabel() {
        Label numberOfBallsField = new Label();
        numberOfBallsField.setScaleX(1);
        numberOfBallsField.setScaleY(1);
        numberOfBallsField.setTranslateX(80);
        numberOfBallsField.setTranslateY(400);
        numberOfBallsField.setText("number of balls : ");
        return numberOfBallsField;
    }

    public static Button createStartButton() {
        Button button = new Button("start");
        return button;
    }

    public static Button createBackButton() {
        Button button = new Button("back");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new MainMenu().start(LoginMenu.stageOfProgram);
                } catch (Exception e) {
                    System.out.println("an error occurred");
                }
            }
        });
        return button;
    }

    public static void alertShowing(Alert.AlertType alertType , String title , String header ,
                                    String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Label showTimer() {
        Label timer = new Label("01:00");
        timer.setTranslateX(5);
        timer.setTranslateY(30);
        return timer;
    }



}
