package view.menu;


import controller.GameController;
import controller.GameViewController;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import view.Paths;

import java.net.URL;
import java.security.interfaces.EdECKey;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ScoreboardMenu extends Application {
    public Label rank1;
    public Label rank2;
    public Label rank3;
    public Label rank4;
    public Label rank5;
    public Label rank6;
    public Label rank7;
    public Label rank8;
    public Label rank9;
    public Label rank10;
    public Label stateOfWin;

    @Override
    public void start(Stage stage) throws Exception {
        URL scoreboardMenuFXMLUrl = ScoreboardMenu.class.getResource(Paths.SCOREBOARD_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(scoreboardMenuFXMLUrl);
        if (GameViewController.isBlackWhiteThemeOn) {
            borderPane.getStylesheets().remove(getClass().getResource(
                    Paths.COMMON_STYLES_FILE_PATH.getPath()).toExternalForm());
            borderPane.getStylesheets().add(getClass().getResource(
                    Paths.BLACK_WHITE_STYLE_FILE_PATH.getPath()).toExternalForm());
        }
        Scene scoreboardMenuScene = new Scene(borderPane);
        stage.setScene(scoreboardMenuScene);
        stage.show();
    }

    public void initialize() {
        showRanking();
    }

    private void showRanking() {
        HashMap <Integer , Label> labels = new HashMap<>();
        pushingLabelsToHashMap(labels);
        ArrayList<User> rankedUsers = GameController.rankingOfUsers();
        for (int i = 0; i != rankedUsers.size(); i++) {
            labels.get(i+1).setText((i+1) + ". " + rankedUsers.get(i).getUsername() + " | score : " +
                    (User.getNowDiff() == -1 ? rankedUsers.get(i).getHighscore() :
                            rankedUsers.get(i).getScoreOfDiff().get(User.getNowDiff())) + " / " +
                    (User.getNowDiff() == -1 ?
                            rankedUsers.get(i).getLastGame().format
                                    (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                            rankedUsers.get(i).getLastGameWithDiff(User.getNowDiff()).format
                                    (DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
    }

    private void pushingLabelsToHashMap(HashMap<Integer , Label> labels) {
        labels.put(1 , rank1);
        labels.put(2 , rank2);
        labels.put(3 , rank3);
        labels.put(4 , rank4);
        labels.put(5 , rank5);
        labels.put(6 , rank6);
        labels.put(7 , rank7);
        labels.put(8 , rank8);
        labels.put(9 , rank9);
        labels.put(10 , rank10);
    }

    public void changeRankingToDiff1(MouseEvent mouseEvent) {
        User.setNowDiff(0);
        showRanking();
    }

    public void changeRankingToDiff2(MouseEvent mouseEvent) {
        User.setNowDiff(1);
        showRanking();
    }

    public void changeRankingToDiff3(MouseEvent mouseEvent) {
        User.setNowDiff(2);
        showRanking();
    }

    public void changeRankingToAllDiffs(MouseEvent mouseEvent) {
        User.setNowDiff(-1);
        showRanking();
    }

    public void back(MouseEvent mouseEvent) {
        try {
            new MainMenu().start(LoginMenu.stageOfProgram);
        } catch (Exception e) {
            System.out.println("an error occurred");
        }
    }
}
