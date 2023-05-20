package view.menu;


import controller.GameController;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import view.Paths;

import java.net.URL;
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

    @Override
    public void start(Stage stage) throws Exception {
        URL scoreboardMenuFXMLUrl = ScoreboardMenu.class.getResource(Paths.SCOREBOARD_MENU_FXML_FILE.getPath());
        BorderPane borderPane = FXMLLoader.load(scoreboardMenuFXMLUrl);
        Scene scoreboardMenuScene = new Scene(borderPane);
        stage.setScene(scoreboardMenuScene);
        stage.show();
    }

    public void initialize() {
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
}