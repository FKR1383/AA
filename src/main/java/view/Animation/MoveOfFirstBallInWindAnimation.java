package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Game.Ball;
import model.Game.Disk;
import model.Game.Rod;
import org.w3c.dom.ls.LSOutput;

import static controller.GameController.*;

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
        System.out.println("x ball : " + ball.getTranslateX());
        System.out.println("y ball : " + ball.getTranslateY());
        this.play();
    }

    private void changeLocationOfBall() {
        if (ball.getFill().equals(Color.DIMGREY)) {
            ball.setTranslateX(ball.getTranslateX() - 3*Math.sin(Math.PI/180*angle));
            ball.setTranslateY(ball.getTranslateY() + 3*Math.cos(Math.PI/180*angle));
            ball.getText().setTranslateX(ball.getText().getTranslateX() - 3*Math.sin(Math.PI/180*angle));
            ball.getText().setTranslateY(ball.getText().getTranslateY() + 3*Math.cos(Math.PI/180*angle));
        } else {
            ball.setTranslateX(ball.getTranslateX() + 3*Math.sin(Math.PI/180*angle));
            ball.setTranslateY(ball.getTranslateY() - 3*Math.cos(Math.PI/180*angle));
            ball.getText().setTranslateX(ball.getText().getTranslateX() + 3*Math.sin(Math.PI/180*angle));
            ball.getText().setTranslateY(ball.getText().getTranslateY() - 3*Math.cos(Math.PI/180*angle));
        }
    }

    private void putBallInDisk() {
        double x = ball.getTranslateX() - game.getOuterDisk().getTranslateX();
        double y = ball.getTranslateY() - game.getOuterDisk().getTranslateY();
        double angle2 = Math.atan(Math.abs(x)/Math.abs(y));
        if (x < 0)
            angle2 *= -1;
        if (ball.getFill().equals(Color.DIMGREY)) {
            angle2 *= -1;
            angle2 += Math.PI;
        }
        long milliSeconds = 0;
        milliSeconds = System.currentTimeMillis() - GameController.time;
        double angle = GameController.nowAngle +
                ((double) milliSeconds) / (timeOfRotation) * 360*signOfRotation;
        ball.setTranslateX((double) 200 * Math.sin(Math.toRadians(angle) + angle2));
        ball.setTranslateY((double) 200 * Math.cos(Math.toRadians(angle) + angle2));
        ball.setRotate(-angle-angle2*180/Math.PI);
        ball.getText().setTranslateX((double) 200 * Math.sin(Math.toRadians(angle) + angle2));
        ball.getText().setTranslateY((double) 200 * Math.cos(Math.toRadians(angle) + angle2));
        ball.getText().setRotate(-angle-angle2*180/Math.PI);
        createRodAndPutBall(angle , angle2);
    }

    private void createRodAndPutBall(double angle , double angle2) {
        Rod rod = new Rod();
        if (ball.getFill().equals(Color.DIMGREY))
            rod.setFill(Color.DIMGREY);
        Text text = (Text) game.getDiskWithNumber().getChildren().get
                (game.getDiskWithNumber().getChildren().size() - 1);
        game.getDiskWithNumber().getChildren().remove
                (game.getDiskWithNumber().getChildren().size() - 1);
        Disk disk = (Disk) game.getDiskWithNumber().getChildren().get
                (game.getDiskWithNumber().getChildren().size() - 1);
        game.getDiskWithNumber().getChildren().remove(disk);
        game.getDiskWithNumber().getChildren().add(rod);
        game.getDiskWithNumber().getChildren().add(ball);
        game.getDiskWithNumber().getChildren().add(ball.getText());
        game.getDiskWithNumber().getChildren().add(disk);
        game.getDiskWithNumber().getChildren().add(text);
        rod.setTranslateX((double) 100 * Math.sin(Math.toRadians(angle) + angle2));
        rod.setTranslateY((double) 100 * Math.cos(Math.toRadians(angle) + angle2));
        rod.setScaleY(200);
        rod.setRotate(-angle-angle2*180/Math.PI);
    }

    @Override
    protected void interpolate(double v) {
        changeLocationOfBall();
        if (ball.getTranslateX() <= 25 || ball.getTranslateX() >= 425 && !game.isEnd()) {
            GameController.getGame().setEnd(true);
            GameController.getGame().setWin(false);
            try {
                System.out.println("show state in move of first divare");
                GameController.showState();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.stop();
        }
        if (GameController.isCollide(ball.getTranslateX() , ball.getTranslateY() ,
                GameController.getGame().getOuterDisk().getTranslateX() ,
                GameController.getGame().getOuterDisk().getTranslateY() ,
                ball.getRadius() , GameController.getGame().getOuterDisk().getRadius())) {
            putBallInDisk();
            try {
                System.out.println("check collide in move of first of ball in collide (correct shoot)");
                checkCollide();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.stop();
            }
        }
}
