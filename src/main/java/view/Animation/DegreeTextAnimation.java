package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.util.Duration;
import view.menu.GameMenu;

public class DegreeTextAnimation extends Transition {
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    private int getTime() {
        double a = GameController.getGame().getWindSpeed();
        int timeOfChange = 0;
        if (a < 1.3) {
            timeOfChange = 5000;
        } else if (a < 1.6) {
            timeOfChange = 3000;
        } else if (a < 2) {
            timeOfChange = 2000;
        }
        return timeOfChange;
    }

    public DegreeTextAnimation(int state) {
        this.state = state;
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(getTime()));
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        double degree = Double.parseDouble(GameMenu.degreeText.getText());
        GameMenu.degreeText.setText(String.format("%.2f" , degree + state*0.01));
        if ((degree + 0.01 >= 15 && state > 0) || (degree - 0.01 <= -15 && state < 0)) {
            new DegreeTextAnimation(-state);
            this.stop();
        }
    }
}
