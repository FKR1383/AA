package controller;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Game.Game;
import view.Paths;
import view.menu.GameMenu;
import view.menu.LoginMenu;
import view.menu.MainMenu;

import static view.menu.GameMenu.isMute;
import static view.menu.GameMenu.isPlay;

public class GameViewController {
    public static Rectangle createMuteUnmuteIcon() {
        Rectangle icon = new Rectangle(10 , 10 , 20 , 20);
        icon.setFill(new ImagePattern(new Image
                (GameViewController.class.getResource("/images/icons/unmuteIcon.png").toExternalForm())));
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isMute == false) {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/muteIcon.jpg").toExternalForm())));
                    isMute = true;
                } else {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/unmuteIcon.png").toExternalForm())));
                    isMute = false;
                }
            }
        });
        return icon;
    }

    public static Rectangle createPlayPauseIcon() {
        Rectangle icon = new Rectangle(40 , 10 , 20 , 20);
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
                } else {
                    icon.setFill
                            (new ImagePattern(new Image(GameMenu.class.getResource
                                    ("/images/icons/playIcon.png").toExternalForm())));
                    isPlay = false;
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

}
