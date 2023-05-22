package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Game.Ball;

import java.util.ArrayList;
import java.util.Random;

public class ChangeSizeOfBallsAnimation extends Transition {
    private double percent;
    private StackPane diskWithBalls;
    private ArrayList<Object> diskWithChildren;
    private ArrayList<Object> balls;
    private static ArrayList<Object> disksStatic;
    private static ArrayList<Object> ballsStatic;

    public ChangeSizeOfBallsAnimation(double percent, StackPane diskWithBalls) {
        this.percent = percent;
        this.diskWithBalls = diskWithBalls;
        this.setCycleCount(1);
        if (percent > 0) {
            diskWithChildren = new ArrayList<>(
                    GameController.getGame().getDiskWithNumber().getChildren());
            balls = new ArrayList<>(GameController.getGame().getBalls());
            disksStatic = new ArrayList<>(
                    GameController.getGame().getDiskWithNumber().getChildren());
            ballsStatic = new ArrayList<>(GameController.getGame().getBalls());
        } else {
            diskWithChildren = disksStatic;
            balls = ballsStatic;
        }
        this.setCycleDuration(Duration.millis(200));
        this.play();
        this.setOnFinished(e -> {
            try {
                if (GameController.checkCollide()) {
                } else {
                    new ChangeSizeOfBallsAnimation(-percent, diskWithBalls);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    protected void interpolate(double v) {
        double radius = 0;
        for (Object o : diskWithChildren) {
             if (o instanceof Ball) {
                radius = ((Ball)o).getRadius();
                ((Ball)o).setRadius(radius + percent*0.1);
            }
        }
        for (Object o : balls) {
            if (o instanceof Ball) {
                radius = ((Ball)o).getRadius();
                ((Ball)o).setRadius(radius + percent*0.1);
            }
        }
    }
}
