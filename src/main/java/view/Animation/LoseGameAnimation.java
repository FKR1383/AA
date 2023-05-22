package view.Animation;

import javafx.animation.Transition;

public class LoseGameAnimation extends Transition {
    private static boolean isDone;

    public static boolean isIsDone() {
        return isDone;
    }

    public static void setIsDone(boolean isDone) {
        LoseGameAnimation.isDone = isDone;
    }

    @Override
    protected void interpolate(double v) {

    }
}
