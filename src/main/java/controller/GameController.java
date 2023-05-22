package controller;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.util.Duration;


import model.Game.*;
import model.User;
import view.Animation.ChangeSizeOfBallsAnimation;
import view.Animation.FadeOfBallsAnimation;
import view.Animation.MoveOfFirstBallInWindAnimation;
import view.Paths;
import view.menu.GameMenu;
import view.menu.LoginMenu;
import view.menu.MainMenu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static view.menu.GameMenu.*;

public class GameController {
    public static Game game;
    private static int difficulty = 2;
    private static RotateTransition rotateTransition;
    public static double nowAngle = 0;
    public static long time;
    private static boolean isIceMode = false;
    public static int timeOfRotation;
    private static boolean isPhase2 = false;
    private static boolean isPhase3 = false;
    public static boolean isPhase4 = false;
    public static int signOfRotation = 1;
    private static int musicsNumber = 1;

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

    public static void createGame(int numberOfMap, int numberOfBalls) {
        StackPane stackPane = null;
        switch (numberOfMap) {
            case 1 : {
                stackPane = createDiskMap1();
            } break;
            case 2 : {
                stackPane = createDiskMap2();
            } break;
            case 3 : {
                stackPane = createDiskMap3();
            }
        }
        Game game = new Game(difficulty, "", createBalls(numberOfBalls)
                , stackPane, numberOfBalls, rotatingByDifficulty(),
                windSpeedByDifficulty(), iceTimerByDifficulty());
        OuterDisk outerDisk = new OuterDisk();
        outerDisk.setTranslateX(225);
        outerDisk.setTranslateY(400);
        outerDisk.setRadius(200);
        GameController.setGame(game);
        game.setOuterDisk(outerDisk);
        outerDisk.setFill(Color.GRAY);
        outerDisk.setOpacity(0.5);
        nowAngle = 0;
        game.setRotateTransition(rotateTransition);
        isIceMode = false;
        timeOfRotation = 15000/rotatingByDifficulty();
        isPhase2 = false;
        isPhase3 = false;
        isPhase4 = false;
        time = System.currentTimeMillis();
        signOfRotation = 1;
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
                return 6;
            case 3:
                return 10;
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

    public static void createBallAndRod(double angle, StackPane disk) {
        Rod rod = new Rod();
        Ball ball = new Ball();
        ball.setRadius(10);
        ball.setTranslateX(Math.cos(angle) * 200);
        ball.setTranslateY(Math.sin(angle) * 200);
        rod.setTranslateX(Math.cos(angle) * 100);
        rod.setTranslateY(Math.sin(angle) * 100);
        rod.setScaleY(200);
        rod.setRotate(angle*180/Math.PI-90);
        disk.getChildren().add(ball);
        disk.getChildren().add(rod);
    }

    private static StackPane createDiskMap1() {
        StackPane stackPane = new StackPane();
        Disk disk = new Disk();
        disk.setRadius(80);
        Text text = new Text("1");
        text.setScaleX(5);
        text.setScaleY(5);
        createBallAndRod(Math.PI/4 , stackPane);
        createBallAndRod(Math.PI/4*2 , stackPane);
        createBallAndRod(Math.PI/4*3 , stackPane);
        createBallAndRod(Math.PI , stackPane);
        createBallAndRod(Math.PI/4*5 , stackPane);
        createBallAndRod(Math.PI/2*3, stackPane);
        createBallAndRod(Math.PI/4*7, stackPane);
        createBallAndRod(Math.PI/4*8, stackPane);
        Color color = null;
        if (GameViewController.isBlackWhiteThemeOn) {
            color = Color.BLACK;
            text.setFill(Color.WHITE);
        } else {
            color = Color.rgb(100, 200, 5);
        }
        disk.setFill(color);
        stackPane.getChildren().add(disk);
        stackPane.getChildren().add(text);
        stackPane.setTranslateX(145);
        stackPane.setTranslateY(320);
        settingRotateTransition(stackPane);
        return stackPane;
    }
    private static StackPane createDiskMap3() {
        StackPane stackPane = new StackPane();
        Disk disk = new Disk();
        disk.setRadius(80);
        Text text = new Text("1");
        text.setScaleX(5);
        text.setScaleY(5);
        createBallAndRod(Math.PI/3 , stackPane);
        createBallAndRod(Math.toRadians(110) , stackPane);
        createBallAndRod(Math.toRadians(130) , stackPane);
        createBallAndRod(Math.PI , stackPane);
        createBallAndRod(Math.toRadians(230) , stackPane);
        createBallAndRod(Math.toRadians(250) , stackPane);
        createBallAndRod(Math.PI/3*5 , stackPane);
        createBallAndRod(Math.toRadians(10) , stackPane);
        createBallAndRod(Math.toRadians(-10) , stackPane);
        Color color = null;
        if (GameViewController.isBlackWhiteThemeOn) {
            color = Color.BLACK;
            text.setFill(Color.WHITE);
        } else {
            color = Color.rgb(250, 200, 0);
        }
        disk.setFill(color);
        stackPane.getChildren().add(disk);
        stackPane.getChildren().add(text);
        stackPane.setTranslateX(145);
        stackPane.setTranslateY(320);
        settingRotateTransition(stackPane);
        return stackPane;
    }

    private static StackPane createDiskMap2() {
        StackPane stackPane = new StackPane();
        Disk disk = new Disk();
        disk.setRadius(80);
        Text text = new Text("1");
        text.setScaleX(5);
        text.setScaleY(5);
        createBallAndRod(Math.PI/2 , stackPane);
        createBallAndRod(Math.PI/6 , stackPane);
        createBallAndRod(-Math.PI/6 , stackPane);
        createBallAndRod(Math.PI/6*5 , stackPane);
        createBallAndRod(-Math.PI/2 , stackPane);
        createBallAndRod(-Math.PI/6*5, stackPane);
        Color color = null;
        if (GameViewController.isBlackWhiteThemeOn) {
            color = Color.BLACK;
            text.setFill(Color.WHITE);
        } else {
            color = Color.rgb(225, 0, 0);
        }
        disk.setFill(color);
        stackPane.getChildren().add(disk);
        stackPane.getChildren().add(text);
        stackPane.setTranslateX(145);
        stackPane.setTranslateY(320);
        settingRotateTransition(stackPane);
        return stackPane;
    }

    public static void settingRotateTransition(StackPane stackPane){
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
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100) , ball);
        translateTransition.setByY(-50);
        TranslateTransition translateTransition2 = new TranslateTransition
                (Duration.millis(100) , ball.getText());
        translateTransition2.setByY(-50);
        translateTransition.setCycleCount(1);
        translateTransition2.setCycleCount(1);
        translateTransition2.play();
        translateTransition.play();
        translateTransition.setOnFinished(e -> {
            translateTransition2.stop();
            translateTransition.stop();
            if (ball.equals(firstBall)) {
                try {
                    checkEndgame(ball , firstBall);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
    public static void checkEndgame(Ball ball , Ball firstBall) throws Exception {
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
        checkCollide();
        if (game.isEnd()) {
            GameController.showState();
        }
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
        GameMenu.scoreText.setText(String.format("score : %d",game.getDifficulty() *
                (game.getNumberOfBalls() - game.getBalls().size())));
        GameMenu.numberOfBallsText.setText("number of balls : " + GameController.game.getBalls()
                .size());
        if (game.getBalls().size() <= 5) {
            numberOfBallsText.setFill(Color.GREEN);
        }
        progressBarField.setProgress(progressBarField.getProgress() + 0.1);
        if (!isPhase2 && (double)(game.getNumberOfBalls()-game.getBalls().size()) / game.getNumberOfBalls()
                >= 0.24) {
            System.out.println("run");
            GameController.runPhase2();
            GameController.bigAndSmallBalls(1);
        }
        if (!isPhase3 && (double)(game.getNumberOfBalls()-game.getBalls().size())/game.getNumberOfBalls()
            >= 0.49) {
            isPhase3 = true;
            numberOfBallsText.setFill(Color.YELLOW);
            GameController.runPhase3();
        }
        if (!isPhase4 && (double)(game.getNumberOfBalls()-game.getBalls().size())/game.getNumberOfBalls()
            >= 0.74) {
            isPhase4 = true;
            GameController.runPhase4();
        }

    }

    private static void runPhase4() {
        ((Text)(game.getDiskWithNumber().getChildren().get
                (game.getDiskWithNumber().getChildren().size()-1))).setText("4");
        GameController.changingDegree();
    }

    private static void changingDegree() {
        if (!isPhase4)
            return;
        int durationInMillis = 0;
        switch (GameController.getDifficulty()) {
            case 1 : {
                durationInMillis = 5000;
            } break;
            case 2 : {
                durationInMillis = 3000;
            } break;
            case 3 : {
                durationInMillis = 2000;
            } break;
        }
        RotateTransition degreeChanging = new RotateTransition(Duration.millis(5000)
        , GameController.getGame().getOuterDisk());
        degreeChanging.setCycleCount(1);
        degreeChanging.play();
        degreeChanging.setOnFinished(e -> {
            if (game.isEnd()) {
                return;
            }
            Random random = new Random();
            int degree = (Math.abs(random.nextInt()) % 31) - 15;
            degreeText.setText(String.format("%d" , degree));
            changingDegree();
        });
    }

    private static void runPhase3() {
        ((Text)(game.getDiskWithNumber().getChildren().get
                (game.getDiskWithNumber().getChildren().size()-1))).setText("3");
        new FadeOfBallsAnimation(-1);
    }

    public static void showWin() {
        //rotateTransition.stop();
        gamePane.getStyleClass().remove("Background");
        gamePane.getStyleClass().add("Background_Win");
        GameController.distributionOfScore();
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
                    System.out.println("collide");
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
    
    public static boolean checkCollide() throws Exception {
        if (game != null && game.getDiskWithNumber() != null) {
            for (int i = 0; i != game.getDiskWithNumber().getChildren().size(); i++) {
                Object o = game.getDiskWithNumber().getChildren().get(i);
                for (int j = 0; j != i; j++) {
                    Object p = game.getDiskWithNumber().getChildren().get(j);
                    if (o instanceof Ball && p instanceof Ball) {
                        Ball first = (Ball) o;
                        Ball second = (Ball) p;
                        if (isCollide(first.getTranslateX(), first.getTranslateY(),
                                second.getTranslateX(), second.getTranslateY(),
                                first.getRadius())) {
                            game.setEnd(true);
                            game.setWin(false);
                            System.out.println("lose");
                            System.out.println("collide");
                            showState();
                            return true;
                        }
                    }

                }
            }
            if (game.getBalls().size() == 0) {
                game.setEnd(true);
                game.setWin(true);
                System.out.println("win");
                showState();
                return false;
            }
            return false;
        }
        return false;
    }


    private static void distributionOfScore() {
        if (App.getCurrentUser() != null) {
            User user = App.getCurrentUser();
            int nowScore = user.getScoreOfDiff().get(GameController.getDifficulty() - 1);
            nowScore += game.getDifficulty() * (game.getNumberOfBalls() - game.getBalls().size());
            user.getScoreOfDiff().set(game.getDifficulty() - 1, nowScore);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            user.getLastGames()[GameController.difficulty-1] = LocalDateTime.now().format(formatter);
            DBController.saveCurrentUser();
            DBController.saveUsers();
        }
    }

    public static void showLose() {
        //rotateTransition.stop();
        gamePane.getStyleClass().remove("Background");
        gamePane.getStyleClass().add("Background_Lose");
        GameController.distributionOfScore();

    }

    private static boolean isCollide(double cx1 , double cy1 , double cx2 ,
                                     double cy2 , double radius) {
        double distance = Math.sqrt(Math.pow(cx1-cx2,2)+Math.pow(cy1-cy2 , 2));
        return distance <= 2*radius;
    }
    public static boolean isCollide(double cx1 , double cy1 , double cx2 ,
                                     double cy2 , double radius , double radius2) {
        double distance = Math.sqrt(Math.pow(cx1-cx2,2)+Math.pow(cy1-cy2 , 2));
        return distance <= radius + radius2;
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
        int durationInMillis = 0;
        switch (GameController.difficulty) {
            case 1 : {
                durationInMillis = 5000;
            } break;
            case 2 : {
                durationInMillis = 4000;
            } break;
            case 3 : {
                durationInMillis = 3000;
            } break;
        }
        ImageView snowImage = new ImageView(new Image(
                GameController.class.getResource("/images/icons/snowIcon.png").toExternalForm()));
        snowImage.setFitWidth(100);
        snowImage.setFitHeight(100);
        snowImage.setTranslateX(175);
        snowImage.setTranslateY(350);
        gamePane.getChildren().add(snowImage);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000) , snowImage);
        fadeTransition.setCycleCount(-1);
        fadeTransition.setFromValue(10);
        fadeTransition.setToValue(0.1);
        fadeTransition.play();
        rotateTransition.setDuration(Duration.millis(durationInMillis));
        rotateTransition.setFromAngle(nowAngle);
        rotateTransition.setCycleCount(2);
        rotateTransition.setToAngle(nowAngle + signOfRotation*360);
        rotateTransition.play();
        rotateTransition.setOnFinished(e -> {
            rotateTransition.stop();
            fadeTransition.stop();
            gamePane.getChildren().remove(snowImage);
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
        });
    }
    public static int getTimeOfRotation() {
        return timeOfRotation;
    }

    public static void setTimeOfRotation(int timeOfRotation) {
        GameController.timeOfRotation = timeOfRotation;
    }

    public static void bigAndSmallBalls(int index) {
        Transition transition = new ChangeSizeOfBallsAnimation(index , game.getDiskWithNumber());
    }

    public static void showState() throws Exception {
        rotateTransition.stop();
        if (game.isWin())
            GameController.showWin();
        else
            GameController.showLose();
        Label label = new Label("");
        if (GameController.game.isWin())
            label.setText("You Win!\n" + scoreText.getText() + " scores reached!");
        else
            label.setText("You Lose!\n" + scoreText.getText() + " scores reached!");
        Button button = new Button("OK!");
        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        vBox.getChildren().add(button);
        gamePane.getChildren().add(vBox);
        vBox.setTranslateX(20);
        vBox.setTranslateY(20);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    GameController.game.setDiskWithNumber(null);
                    GameController.game.setBalls(null);
                    new MainMenu().start(LoginMenu.stageOfProgram);
                } catch (Exception e) {
                    System.out.println("an error occurred");
                }
            }
        });
    }

    public static void moveRight() {
        for (int i = 0; i != GameController.game.getBalls().size(); i++) {
            if (GameController.getGame().getBalls().get(i).getTranslateX() <= 425) {
                GameController.getGame().getBalls().get(i).setTranslateX(
                        GameController.getGame().getBalls().get(i).getTranslateX() + 5);
                GameController.getGame().getBalls().get(i).getText().setTranslateX(
                        GameController.getGame().getBalls().get(i).getText().getTranslateX() + 5);
            }
        }
    }

    public static void moveLeft() {
        for (int i = 0; i != GameController.game.getBalls().size(); i++) {
            if (GameController.getGame().getBalls().get(i).getTranslateX() >= 25) {
                GameController.getGame().getBalls().get(i).setTranslateX(
                        GameController.getGame().getBalls().get(i).getTranslateX() - 5);
                GameController.getGame().getBalls().get(i).getText().setTranslateX(
                        GameController.getGame().getBalls().get(i).getText().getTranslateX() - 5);
            }
        }
    }

    public static void shoot2() throws Exception {
        Ball firstBall = GameController.getGame().getBalls().get(0);
        Iterator itr = GameController.getGame().getBalls().iterator();
        while (itr.hasNext()) {
            Ball ball = (Ball) itr.next();
            ballGoingToUp2(ball ,firstBall);
            if (ball.equals(firstBall)) {
                itr.remove();
            }
        }
        GameMenu.scoreText.setText(String.format("score : %d",game.getDifficulty() *
                (game.getNumberOfBalls() - game.getBalls().size())));
        GameMenu.numberOfBallsText.setText("number of balls : " + GameController.game.getBalls()
                .size());
        if (GameController.game.getBalls().size() <= 5) {
            numberOfBallsText.setFill(Color.GREEN);
        }
        progressBarField.setProgress(progressBarField.getProgress() + 0.1);
        checkCollide();
        if (game.isEnd()) {
            GameController.showState();
        }
    }

    private static void ballGoingToUp2(Ball ball, Ball firstBall) {
        if (ball.equals(firstBall)) {
            new MoveOfFirstBallInWindAnimation(ball , Integer.parseInt(degreeText.getText()));
        } else {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), ball);
            translateTransition.setByY(-50);
            TranslateTransition translateTransition2 = new TranslateTransition
                    (Duration.millis(100), ball.getText());
            translateTransition2.setByY(-50);
            translateTransition.setCycleCount(1);
            translateTransition2.setCycleCount(1);
            translateTransition2.play();
            translateTransition.play();
            translateTransition.setOnFinished(e -> {
                translateTransition2.stop();
                translateTransition.stop();
            });
        }
    }


    public static int getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(int difficulty) {
        GameController.difficulty = difficulty;
    }
}
