package model.Game;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private int difficulty;
    private String color;
    private ArrayList<Ball> balls;
    private StackPane diskWithNumber;
    private int numberOfBalls;
    private int rotatingRate;
    private double windSpeed;
    private int iceTimer;
    private OuterDisk outerDisk;
    private RotateTransition rotateTransition;
    private boolean isEnd = false;
    private boolean isWin = true;

    public RotateTransition getRotateTransition() {
        return rotateTransition;
    }

    public void setRotateTransition(RotateTransition rotateTransition) {
        this.rotateTransition = rotateTransition;
    }

    public Game(int difficulty,
                String color, ArrayList<Ball> balls
                , StackPane diskWithNumber, int numberOfBalls,
                int rotatingRate,
                Double windSpeed, int iceTimer) {
        this.difficulty = difficulty;
        this.color = color;
        this.balls = balls;
        this.diskWithNumber = diskWithNumber;
        this.numberOfBalls = numberOfBalls;
        this.rotatingRate = rotatingRate;
        this.windSpeed = windSpeed;
        this.iceTimer = iceTimer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public StackPane getDiskWithNumber() {
        return diskWithNumber;
    }

    public void setDiskWithNumber(StackPane diskWithNumber) {
        this.diskWithNumber = diskWithNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(int numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }

    public int getRotatingRate() {
        return rotatingRate;
    }

    public void setRotatingRate(int rotatingRate) {
        this.rotatingRate = rotatingRate;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getIceTimer() {
        return iceTimer;
    }

    public void setIceTimer(int iceTimer) {
        this.iceTimer = iceTimer;
    }

    public OuterDisk getOuterDisk() {
        return outerDisk;
    }

    public void setOuterDisk(OuterDisk outerDisk) {
        this.outerDisk = outerDisk;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }
}
