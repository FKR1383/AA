package view.Animation;

import controller.GameController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import view.menu.GameMenu;
import view.menu.MainMenu;

public class TimerAnimation extends Transition {
    private int seconds = 0 , minutes = 0;
    public TimerAnimation() {
        String text = GameMenu.timer.getText().substring(3,5);
        String text2 = GameMenu.timer.getText().substring(0,2);
        seconds = Integer.parseInt(text);
        minutes = Integer.parseInt(text2);
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(1);
        this.play();
        this.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (minutes == 1) {
                    GameMenu.timer.setText(String.format("00:59", seconds));
                    GameMenu.timerTransition = new TimerAnimation();
                } else {
                    seconds--;
                    if (seconds != -1) {
                        GameMenu.timer.setText(String.format("00:%02d", seconds));
                        GameMenu.timerTransition = new TimerAnimation();
                    } else {
                        GameController.game.setEnd(true);
                        GameController.game.setWin(false);
                        try {
                            GameController.showState();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void interpolate(double v) {
    }

}
