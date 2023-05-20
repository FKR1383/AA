package controller;

import javafx.animation.*;
import javafx.beans.binding.ObjectExpression;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;


import model.Game.*;
import model.User;
import view.Animation.ChangeSizeOfBallsAnimation;
import view.Animation.DegreeTextAnimation;
import view.Animation.FadeOfBallsAnimation;
import view.Paths;
import view.menu.GameMenu;
import view.menu.LoginMenu;
import view.menu.MainMenu;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

import static view.menu.GameMenu.*;

public class GameController {
    private static int difficulty = 2;
    private static int collides = 0;

    public static int getDifficulty() {
        return difficulty;
    }

    private static Game game;
    private static RotateTransition rotateTransition;
    private static double nowAngle = 0;
    private static long time;
    private static boolean isIceMode = false;
    private static int timeOfRotation;
    private static boolean isPhase2 = false;
    private static boolean isPhase3 = false;
    public static boolean isPhase4 = false;

    public static void setDifficulty(int difficulty) {
        GameController.difficulty = difficulty;
    }
    private static int signOfRotation = 1;
    private static boolean isBeingBig = true;

    public static ArrayList<User> rankingOfUsers() {
        ArrayList<User> rankedUsers = new ArrayList<>(App.getAllUsers());
        if (User.getNowDiff() == -1) {
            Collections.sort(rankedUsers);
        } else {
            Collections.sort(rankedUsers, new User.OrderByScoreOfAType());
        }
        return rankedUsers;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game g) {
        game = g;
    }

    private static String getColorByNumberOfMap(int numberOfMap) {
        switch (numberOfMap) {
            case 1:
                return "blue";
            case 2:
                return "orange";
            case 3:
                return "green";
        }
        return "";
    }


    public static void createGame(int numberOfMap, int numberOfBalls) {
        Game game = new Game(difficulty, getColorByNumberOfMap(numberOfMap), createBalls(numberOfBalls)
                , createDisk(numberOfMap), numberOfBalls, rotatingByDifficulty(),
                windSpeedByDifficulty(), iceTimerByDifficulty());
        GameController.setGame(game);
        nowAngle = 0;
        game.setRotateTransition(rotateTransition);
        isIceMode = false;
        timeOfRotation = 15000/rotatingByDifficulty();
        isPhase2 = false;
        isPhase3 = false;
        time = System.currentTimeMillis();
        signOfRotation = 1;
        game.setOuterDisk((OuterDisk) game.getDiskWithNumber().getChildren().get(0)); // outerDisk is first
        // children of diskWithNumber
    }

    private static double windSpeedByDifficulty() {
        switch (difficulty) {
            case 1:
                return 1.2;
            case 2:
                return 1.5;
            case 3:
                return 1.8;
        }
        return 0;
    }

    private static int iceTimerByDifficulty() {
        switch (difficulty) {
            case 1:
                return 7;
            case 2:
                return 5;
            case 3:
                return 3;
        }
        return 0;
    }

    private static int rotatingByDifficulty() {
        switch (difficulty) {
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 25;
        }
        return 0;
    }

    public static void runPhase2() {
        if (!isPhase2) {
            ((Text)(game.getDiskWithNumber().getChildren().get
                    (game.getDiskWithNumber().getChildren().size()-1))).setText("2");
        }
        isPhase2 = true;
        long miliseconds = (System.currentTimeMillis() - time);
        nowAngle += ((double)miliseconds)/timeOfRotation * signOfRotation * 360;
        time = System.currentTimeMillis();
        signOfRotation = -signOfRotation;
        rotateTransition.stop();
        rotateTransition = new RotateTransition(Duration.millis(timeOfRotation) ,
                getGame().getDiskWithNumber());
        Random random = new Random();
        int randomCycleCount = random.nextInt()%3+1;
        rotateTransition.setCycleCount(randomCycleCount);
        rotateTransition.setFromAngle(nowAngle);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setToAngle(nowAngle + signOfRotation*360);
        rotateTransition.play();
        rotateTransition.setOnFinished(e -> {
            runPhase2();
        });
    }

    private static StackPane createDisk(int numberOfMap) {
        Disk disk = new Disk();
        OuterDisk outerDisk = new OuterDisk();
        disk.setRadius(80);
        outerDisk.setRadius(200);
        Text text = new Text("1");
        text.setScaleX(5);
        text.setScaleY(5);
        Rod rod = new Rod();
        Ball ball = new Ball();
        ball.setRadius(10);
        ball.setTranslateX(0);
        ball.setTranslateY(200);
        rod.setTranslateX(0);
        rod.setTranslateY(100);
        rod.setScaleY(200);
        Rod rod2 = new Rod();
        Ball ball2 = new Ball();
        ball2.setRadius(10);
        ball2.setTranslateX((double) 200 * Math.cos(Math.PI / 6));
        ball2.setTranslateY((double) 200 * Math.cos(Math.PI / 3));
        rod2.setTranslateX((double) 100 * Math.cos(Math.PI / 6));
        rod2.setTranslateY((double) 100 * Math.cos(Math.PI / 3));
        rod2.setScaleY(200);
        rod2.setRotate(-60);
        Rod rod3 = new Rod();
        Ball ball3 = new Ball();
        ball3.setRadius(10);
        ball3.setTranslateX((double) 200 * Math.cos(Math.PI / 6));
        ball3.setTranslateY(-(double) 200 * Math.cos(Math.PI / 3));
        rod3.setTranslateX((double) 100 * Math.cos(Math.PI / 6));
        rod3.setTranslateY(-(double) 100 * Math.cos(Math.PI / 3));
        rod3.setScaleY(200);
        rod3.setRotate(-120);
        Rod rod4 = new Rod();
        Ball ball4 = new Ball();
        ball4.setRadius(10);
        ball4.setTranslateX(-(double) 200 * Math.cos(Math.PI / 6));
        ball4.setTranslateY((double) 200 * Math.cos(Math.PI / 3));
        rod4.setTranslateX(-(double) 100 * Math.cos(Math.PI / 6));
        rod4.setTranslateY((double) 100 * Math.cos(Math.PI / 3));
        rod4.setScaleY(200);
        rod4.setRotate(60);
        Rod rod5 = new Rod();
        Ball ball5 = new Ball();
        ball5.setRadius(10);
        ball5.setTranslateX(0);
        ball5.setTranslateY(-200);
        rod5.setTranslateX(0);
        rod5.setTranslateY(-100);
        rod5.setScaleY(200);
        rod5.setRotate(180);
        Rod rod6 = new Rod();
        Ball ball6 = new Ball();
        ball6.setRadius(10);
        ball6.setTranslateX(-(double) 200 * Math.cos(Math.PI / 6));
        ball6.setTranslateY(-(double) 200 * Math.cos(Math.PI / 3));
        rod6.setTranslateX(-(double) 100 * Math.cos(Math.PI / 6));
        rod6.setTranslateY(-(double) 100 * Math.cos(Math.PI / 3));
        rod6.setScaleY(200);
        rod6.setRotate(120);
        Color color = Color.rgb(225, 0, 0);
        outerDisk.setFill(Color.GRAY);
        disk.setFill(color);
        outerDisk.setOpacity(0.5);
        StackPane stackPane = new StackPane(outerDisk, ball, rod, ball2, rod2, rod3, ball3,
                rod4, ball4, rod5, ball5, rod6, ball6, disk, text);
        stackPane.setLayoutX(25);
        stackPane.setLayoutY(200);
        RotateTransition rt = new RotateTransition(Duration.millis
                (15000 / GameController.rotatingByDifficulty()), stackPane);
        timeOfRotation = 15000 / GameController.rotatingByDifficulty();
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.setCycleCount(Timeline.INDEFINITE);
        rt.play();
        time = System.currentTimeMillis();
        rotateTransition = rt;
        return stackPane;
    }

    private static ArrayList<Ball> createBalls(int numberOfBalls) {
        ArrayList<Ball> balls = new ArrayList<>();
        for (int i = numberOfBalls; i >= 1; i--) {
            Ball ball = new Ball();
            ball.setNumber(i);
            Text text = new Text(String.format("%d", i));
            text.setFill(Color.WHITE);
            ball.setText(text);
            ball.setRadius(10);
            balls.add(ball);
        }
        return balls;
    }

    private static void ballGoingToUp(Ball ball , Ball firstBall) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(50) , ball);
        translateTransition.setByY(-50);
        TranslateTransition translateTransition2 = new TranslateTransition
                (Duration.millis(50) , ball.getText());
        translateTransition2.setByY(-50);
        translateTransition.setCycleCount(1);
        translateTransition2.setCycleCount(1);
        translateTransition2.play();
        translateTransition.play();
        translateTransition.setOnFinished(e -> {
            translateTransition2.stop();
            translateTransition.stop();
            if (ball.equals(firstBall)) {
                long milliSeconds = 0;
                milliSeconds = System.currentTimeMillis() - time;
                Rod rod = new Rod();
                double angle = nowAngle + ((double) milliSeconds) / (timeOfRotation) * 360*signOfRotation;
                firstBall.setTranslateX((double) 200 * Math.sin(Math.toRadians(angle))); // 2.5
                firstBall.setTranslateY((double) 200 * Math.cos(Math.toRadians(angle))); // 2.5
                firstBall.setRotate(-angle);
                firstBall.getText().setTranslateX((double) 200 * Math.sin(Math.toRadians(angle))); // 2.5
                firstBall.getText().setTranslateY((double) 200 * Math.cos(Math.toRadians(angle))); // 2.5
                firstBall.getText().setRotate(-angle);
                rod.setTranslateX((double) 100 * Math.sin(Math.toRadians(angle)));
                rod.setTranslateY((double) 100 * Math.cos(Math.toRadians(angle)));
                rod.setScaleY(200);
                rod.setRotate(-angle);
                Text text = (Text) game.getDiskWithNumber().getChildren().get
                        (game.getDiskWithNumber().getChildren().size() - 1);
                game.getDiskWithNumber().getChildren().remove
                        (game.getDiskWithNumber().getChildren().size() - 1);
                Disk disk = (Disk) game.getDiskWithNumber().getChildren().get
                        (game.getDiskWithNumber().getChildren().size() - 1);
                game.getDiskWithNumber().getChildren().remove(disk);
                game.getDiskWithNumber().getChildren().add(rod);
                game.getDiskWithNumber().getChildren().add(firstBall);
                game.getDiskWithNumber().getChildren().add(firstBall.getText());
                game.getDiskWithNumber().getChildren().add(disk);
                game.getDiskWithNumber().getChildren().add(text);
            }
        });

    }

    public static void shoot() {
        Ball firstBall = GameController.getGame().getBalls().get(0);
        Iterator itr = GameController.getGame().getBalls().iterator();
        while (itr.hasNext()) {
            Ball ball = (Ball) itr.next();
            ballGoingToUp(ball ,firstBall);
            if (ball.equals(firstBall)) {
                itr.remove();
            }
        }
        checkCollide();
        if (game.isEnd()) {
            GameController.showState();
        }
        progressBarField.setProgress(progressBarField.getProgress() + 0.1);
        if (!isPhase2 && (double)(game.getNumberOfBalls()-game.getBalls().size()) / game.getNumberOfBalls()
                >= 0.25) {
            System.out.println("run");
            GameController.runPhase2();
            GameController.bigAndSmallBalls(1);
        }
        if (!isPhase3 && (double)(game.getNumberOfBalls()-game.getBalls().size())/game.getNumberOfBalls()
            >= 0.5) {
            isPhase3 = true;
            GameController.runPhase3();
        }
        if (!isPhase4 && (double)(game.getNumberOfBalls()-game.getBalls().size())/game.getNumberOfBalls()
            >= 0.75) {
            isPhase4 = true;
            GameController.runPhase4();
        }

    }

    private static void runPhase4() {
        degreeText.setText("-15.00");
        GameController.changingDegree();
    }

    private static void changingDegree() {
        new DegreeTextAnimation(1);
    }

    private static void runPhase3() {
        ((Text)(game.getDiskWithNumber().getChildren().get
                (game.getDiskWithNumber().getChildren().size()-1))).setText("3");
        new FadeOfBallsAnimation(-1);
    }

    public static void showWin() {
        rotateTransition.stop();
        gamePane.getStyleClass().remove("Background");
        gamePane.getStyleClass().add("Background_Win");
        GameController.distributionOfScore();
        Label label = new Label("Game Over!");
        Button button = new Button("ok!");
        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);
        gamePane.getChildren().add(vBox);
        vBox.setTranslateX(180);
        vBox.setTranslateY(100);
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
    }

    public static boolean checkCollide2(Ball ball) {
        for (int i = 0; i != game.getDiskWithNumber().getChildren().size(); i++) {
            if (game.getDiskWithNumber().getChildren().get(i) instanceof Ball) {
                Ball o = (Ball) game.getDiskWithNumber().getChildren().get(i);
                System.out.println("balling");
                if (!ball.equals(o) && isCollide(ball.getTranslateX() , ball.getTranslateY(),
                        o.getTranslateX() , o.getTranslateY() , ball.getRadius())) {
                    game.setEnd(true);
                    game.setWin(false);
                    System.out.println("lose");
                    System.out.println("collide" + (++collides));
                    return true;
                }
            }
        }
        if (game.getBalls().size() == 0) {
            game.setEnd(true);
            game.setWin(true);
            System.out.println("win");
            return true;
        }
        return false;
    }
    
    public static boolean checkCollide(){
        for (int i = 0; i != game.getDiskWithNumber().getChildren().size(); i++) {
            Object o = game.getDiskWithNumber().getChildren().get(i);
            for (int j = 0; j != i; j++) {
                Object p = game.getDiskWithNumber().getChildren().get(j);
                if (o instanceof Ball && p instanceof Ball) {
                    Ball first = (Ball) o;
                    Ball second = (Ball) p;
                    if (isCollide(first.getTranslateX() , first.getTranslateY() ,
                    second.getTranslateX() , second.getTranslateY() ,
                    first.getRadius())) {
                        game.setEnd(true);
                        game.setWin(false);
                        System.out.println("lose");
                        System.out.println("collide" + (++collides));
                        return true;
                    }
                }
            
        }
        }
        if (game.getBalls().size() == 0) {
            game.setEnd(true);
            game.setWin(true);
            System.out.println("win");
            return true;
        }
        return false;
    }


    private static void distributionOfScore() {
        if (App.getCurrentUser() != null) {
            User user = App.getCurrentUser();
            int nowScore = user.getScoreOfDiff().get(GameController.getDifficulty() - 1);
            nowScore += game.getDifficulty() * (game.getNumberOfBalls() - game.getBalls().size());
            user.getScoreOfDiff().set(game.getDifficulty() - 1, nowScore);
            DBController.saveCurrentUser();
            DBController.saveUsers();
        }
    }

    public static void showLose() {
        rotateTransition.stop();
        gamePane.getStyleClass().remove("Background");
        gamePane.getStyleClass().add("Background_Lose");
        GameController.distributionOfScore();
        Label label = new Label("Game Over!");
        Button button = new Button("ok!");
        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);
        gamePane.getChildren().add(vBox);
        vBox.setTranslateX(180);
        vBox.setTranslateY(100);
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
    }

    private static boolean isCollide(double cx1 , double cy1 , double cx2 ,
                                     double cy2 , double radius) {
        double distance = Math.sqrt(Math.pow(cx1-cx2,2)+Math.pow(cy1-cy2 , 2));
        return distance <= 2*radius;
    }

    public static boolean isIsIceMode() {
        return isIceMode;
    }

    public static void setIsIceMode(boolean isIceMode) {
        GameController.isIceMode = isIceMode;
    }

    public static void iceMode() throws InterruptedException {
        long milliSeconds = System.currentTimeMillis() - time;
        nowAngle = nowAngle + ((double) milliSeconds) /
                (timeOfRotation) * signOfRotation* 360;
        time = System.currentTimeMillis();
        timeOfRotation = 5000;
        isIceMode = true;
        rotateTransition.stop();
        rotateTransition.setDuration(Duration.millis(5000));
        rotateTransition.setFromAngle(nowAngle);
        rotateTransition.setCycleCount(2);
        rotateTransition.setToAngle(nowAngle + signOfRotation*360);
        rotateTransition.play();
        rotateTransition.setOnFinished(e -> {
            rotateTransition.stop();
            long milliSecond = System.currentTimeMillis() - time;
            nowAngle = nowAngle + signOfRotation * ((double) milliSecond) /
                    ((double) timeOfRotation) * 360;
            time = System.currentTimeMillis();
            timeOfRotation = 15000 / GameController.rotatingByDifficulty();
            rotateTransition.setDuration(Duration.millis(15000 / GameController.rotatingByDifficulty()));
            rotateTransition.setFromAngle(nowAngle);
            rotateTransition.setToAngle(nowAngle + (signOfRotation) * 360);
            rotateTransition.setCycleCount(Timeline.INDEFINITE);
            rotateTransition.play();
            System.out.println("wow!");
        });
    }
    public static int getTimeOfRotation() {
        return timeOfRotation;
    }

    public static void setTimeOfRotation(int timeOfRotation) {
        GameController.timeOfRotation = timeOfRotation;
    }

    public static void bigAndSmallBalls(int index) {
        System.out.println("showIt");
        Transition transition = new ChangeSizeOfBallsAnimation(index , game.getDiskWithNumber());
    }

    public static void showState() {
        if (game.isWin())
            GameController.showWin();
        else
            GameController.showLose();
    }

    public static void moveRight() {
        for (int i = 0; i != GameController.game.getBalls().size(); i++) {
            GameController.getGame().getBalls().get(i).setTranslateX(
                    GameController.getGame().getBalls().get(i).getTranslateX()+5);
            GameController.getGame().getBalls().get(i).getText().setTranslateX(
                    GameController.getGame().getBalls().get(i).getText().getTranslateX()+5);
        }
    }

    public static void moveLeft() {
        for (int i = 0; i != GameController.game.getBalls().size(); i++) {
            GameController.getGame().getBalls().get(i).setTranslateX(
                    GameController.getGame().getBalls().get(i).getTranslateX()-5);
            GameController.getGame().getBalls().get(i).getText().setTranslateX(
                    GameController.getGame().getBalls().get(i).getText().getTranslateX()-5);
        }
    }
}
