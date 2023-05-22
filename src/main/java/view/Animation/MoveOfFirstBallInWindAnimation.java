package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
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

    @Override
    protected void interpolate(double v) {
        ball.setTranslateX(ball.getTranslateX() + 3*Math.sin(Math.PI/180*angle));
        ball.setTranslateY(ball.getTranslateY() - 3*Math.cos(Math.PI/180*angle));
        ball.getText().setTranslateX(ball.getText().getTranslateX() + 3*Math.sin(Math.PI/180*angle));
        ball.getText().setTranslateY(ball.getText().getTranslateY() - 3*Math.cos(Math.PI/180*angle));
        if (ball.getTranslateX() <= 25 || ball.getTranslateX() >= 425) {
            GameController.getGame().setEnd(true);
            GameController.getGame().setWin(false);
            try {
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
            System.out.println("barkhord");
            double x = ball.getTranslateX() - game.getOuterDisk().getTranslateX();
            double y = ball.getTranslateY() - game.getOuterDisk().getTranslateY();
            System.out.println(x + " " + y);
            double angle2 = Math.atan(Math.abs(x)/Math.abs(y));
            if (x < 0)
                angle2 *= -1;
            long milliSeconds = 0;
            milliSeconds = System.currentTimeMillis() - GameController.time;
            double angle = GameController.nowAngle +
                    ((double) milliSeconds) / (timeOfRotation) * 360*signOfRotation;
            ball.setTranslateX((double) 200 * Math.sin(Math.toRadians(angle) + angle2)); // 2.5
                ball.setTranslateY((double) 200 * Math.cos(Math.toRadians(angle) + angle2)); // 2.5
                ball.setRotate(-angle-angle2*180/Math.PI);
                ball.getText().setTranslateX((double) 200 * Math.sin(Math.toRadians(angle) + angle2)); // 2.5
                ball.getText().setTranslateY((double) 200 * Math.cos(Math.toRadians(angle) + angle2)); // 2.5
                ball.getText().setRotate(-angle-angle2*180/Math.PI);
            Rod rod = new Rod();
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
            System.out.println("x : " + GameController.getGame().getOuterDisk().getTranslateX());
            System.out.println("y : " + game.getOuterDisk().getTranslateY());
            checkCollide();
                this.stop();
            }
        }
}
