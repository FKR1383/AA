package model;

import controller.App;
import controller.GameController;
import model.Game.Game;

import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class User implements Comparable<User>{
    private String username;
    private String password;
    private String avatarFilePath;
    private ArrayList<Integer> scoreOfDiff = new ArrayList<>(); // score in difficulty
    private static int nowDiff = -1; // what is now difficulty
    public String[] lastGames = new String[3];
    //private Game game;

    public User(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.avatarFilePath = address;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lastGames[0] = this.lastGames[1] = this.lastGames[2] = LocalDateTime.now().format(formatter);
        scoreOfDiff.add(0);
        scoreOfDiff.add(0);
        scoreOfDiff.add(0);
        App.getAllUsers().add(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public void setAvatarFilePath(String avatarFilePath) {
        this.avatarFilePath = avatarFilePath;
    }

    public int getHighscore() {
        return this.scoreOfDiff.get(0) + this.scoreOfDiff.get(1)
                + this.scoreOfDiff.get(2);
    }

    public LocalDateTime getLastGame() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime[] lastGamesInDate = new LocalDateTime[3];
        lastGamesInDate[0] = LocalDateTime.parse(lastGames[0], formatter);
        lastGamesInDate[1] = LocalDateTime.parse(lastGames[1], formatter);
        lastGamesInDate[2] = LocalDateTime.parse(lastGames[2], formatter);
        LocalDateTime lastGame = lastGamesInDate[0].isBefore(lastGamesInDate[1]) ?
                lastGamesInDate[1] : lastGamesInDate[0];
        lastGame = lastGamesInDate[2].isBefore(lastGame) ?
                lastGame : lastGamesInDate[2];
        return lastGame;
    }

    public LocalDateTime getLastGameWithDiff(int difficultyLevel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lastGameInDate  = LocalDateTime.parse(lastGames[0], formatter);
        return  lastGameInDate;
    }

    public static int getNowDiff() {
        return nowDiff;
    }

    public static void setNowDiff(int nowDiff) {
        User.nowDiff = nowDiff;
    }

    @Override
    public int compareTo(User o) {
        if (this.getHighscore() > o.getHighscore())
            return -1;
        if (this.getHighscore() < o.getHighscore())
            return 1;
        if (this.getLastGame().isBefore(o.getLastGame()))
            return -1;
        if (this.getLastGame().isAfter(o.getLastGame()))
            return 1;
        return 0;
    }

    public static class OrderByScoreOfAType implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            if (u1.scoreOfDiff.get(nowDiff) > u2.scoreOfDiff.get(nowDiff))
                return -1;
            if (u1.scoreOfDiff.get(nowDiff) < u2.scoreOfDiff.get(nowDiff))
                return 1;
            if (u1.getLastGameWithDiff(nowDiff).isBefore(u2.getLastGameWithDiff(nowDiff)))
                return -1;
            if (u1.getLastGameWithDiff(nowDiff).isAfter(u2.getLastGameWithDiff(nowDiff)))
                return 1;
            return 0;
        }
    }


    public void setScoreOfDiff(int score) {
        this.scoreOfDiff.set(GameController.getDifficulty()-1 , this.scoreOfDiff.get(
                GameController.getDifficulty()-1) + score);
    }

    public ArrayList<Integer> getScoreOfDiff() {
        return scoreOfDiff;
    }

    public void setScoreOfDiff(ArrayList<Integer> scoreOfDiff) {
        this.scoreOfDiff = scoreOfDiff;
    }

    /*public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }*/
}
