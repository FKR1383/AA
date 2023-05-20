package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.Game.Ball;

public class MoveOfFirstBallInWindAnimation extends Transition {
    private Ball ball;
    private int angle;

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public MoveOfFirstBallInWindAnimation(Ball ball, int angle) {
        this.ball = ball;
        this.angle = angle;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(10));
        this.play();
    }

    @Override
    protected void interpolate(double v) {
        ball.setTranslateX(ball.getTranslateX() + 3*Math.sin(Math.PI/180*angle));
        ball.setTranslateY(ball.getTranslateY() - 3*Math.cos(Math.PI/180*angle));
        ball.getText().setTranslateX(ball.getText().getTranslateX() + 3*Math.sin(Math.PI/180*angle));
        ball.getText().setTranslateY(ball.getText().getTranslateY() - 3*Math.cos(Math.PI/180*angle));
        if (ball.getTranslateX() <= 25 || ball.getTranslateX() >= 425) {
            GameController.getGame().setEnd(true);
            GameController.getGame().setWin(false);
            GameController.showState();
            this.stop();
        }
        if (GameController.isCollide(ball.getTranslateX() , ball.getTranslateY() ,
                GameController.getGame().getOuterDisk().getTranslateX() ,
                GameController.getGame().getOuterDisk().getTranslateY() ,
                ball.getRadius() , GameController.getGame().getOuterDisk().getRadius())) {
            System.out.println("mordam");
            this.stop();
        }
    }
}
