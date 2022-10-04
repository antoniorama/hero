import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {

    private int x;
    private int y;

    public Hero(int x_, int y_) {
        x = x_;
        y = y_;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x_) {
        x = x_;
    }

    public void setY(int y_) {
        y = y_;
    }

    public void moveUp() {y--;}
    public void moveDown() {y++;}
    public void moveLeft() {x--;}
    public void moveRight() {x++;}

    public void draw(Screen s) {
        s.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
    }
}