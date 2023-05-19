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

    public ChangeSizeOfBallsAnimation(double percent, StackPane diskWithBalls) {
        this.percent = percent;
        this.diskWithBalls = diskWithBalls;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(200));
        this.play();
        this.setOnFinished(e -> {
            if (!GameController.checkCollide()) {
                new ChangeSizeOfBallsAnimation(-percent, diskWithBalls);
            } else {
                this.stop();
                GameController.showState();
            }
        });
    }

    @Override
    protected void interpolate(double v) {
        double radius = 0;
        for (Object o : diskWithBalls.getChildren()) {
            if (o instanceof StackPane) {
                radius = ((Ball)(((StackPane)o).getChildren().get(0))).getRadius();
                ((Ball)(((StackPane)o).getChildren().get(0))).setRadius(radius + percent*0.1);
            } else if (o instanceof Ball) {
                radius = ((Ball)o).getRadius();
                ((Ball)o).setRadius(radius + percent*0.1);
            }
        }
        for (Object o : GameController.getGame().getBalls()) {
            if (o instanceof StackPane) {
                radius = ((Ball)(((StackPane)o).getChildren().get(0))).getRadius();
                ((Ball)(((StackPane)o).getChildren().get(0))).setRadius(radius + percent*0.1);
            } else if (o instanceof Ball) {
                radius = ((Ball)o).getRadius();
                ((Ball)o).setRadius(radius + percent*0.1);
            }
        }
    }
}
