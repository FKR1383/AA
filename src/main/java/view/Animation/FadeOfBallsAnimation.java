package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game.Ball;
import model.Game.Rod;

public class FadeOfBallsAnimation extends Transition {
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public FadeOfBallsAnimation(int state) {
        this.state = state;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(1000));
        this.play();
        this.setOnFinished(e -> {
            if (GameController.checkCollide()) {
                for (Object o : GameController.getGame().getDiskWithNumber().getChildren()) {
                    if (o instanceof Ball) {
                        ((Ball)o).setOpacity(1);
                        if (((Ball) o).getText() != null)
                            ((Ball)o).getText().setOpacity(1);
                    }
                    if (o instanceof Rod) {
                        ((Rod)o).setOpacity(1);
                    }
                }
                for (Object o : GameController.getGame().getBalls()) {
                    if (o instanceof Ball) {
                        ((Ball)o).setOpacity(1);
                        if (((Ball) o).getText() != null)
                            ((Ball)o).getText().setOpacity(1);
                    }
                }
                try {
                    GameController.showState();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                this.stop();
            } else {
                new FadeOfBallsAnimation(-state);
            }
        });
    }

    @Override
    protected void interpolate(double v) {
        double opacity = state > 0 ? v : 1-v;
        try {
            for (Object o : GameController.getGame().getDiskWithNumber().getChildren()) {
                if (o instanceof Ball) {
                    ((Ball) o).setOpacity(opacity);
                    if (((Ball) o).getText() != null)
                        ((Ball) o).getText().setOpacity(opacity);
                }
                if (o instanceof Rod) {
                    ((Rod) o).setOpacity(opacity);
                }
            }
            for (Object o : GameController.getGame().getBalls()) {
                if (o instanceof Ball) {
                    ((Ball) o).setOpacity(opacity);
                    if (((Ball) o).getText() != null)
                        ((Ball) o).getText().setOpacity(opacity);
                }
            }
        } catch (Exception e) {
            System.out.println("end");
        }
    }
}
