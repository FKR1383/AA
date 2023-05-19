package model.Game;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Ball extends Circle {
    private int number;
    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
