package view.Animation;

import javafx.animation.Transition;

public class WinGameAnimation extends Transition {
    private static boolean isDone;

    public static boolean isDone() {
        return isDone;
    }

    public static void setDone(boolean done) {
        isDone = done;
    }

    public WinGameAnimation() {

    }

    @Override
    protected void interpolate(double v) {

    }
}
