import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class LifeBar {

    private int value;
    private Position onScreenPosition;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LifeBar(int value, Position position) {
        this.value = value;
        this.onScreenPosition = position;
    }

    public void draw(TextGraphics graphics) {
        for(int i = 0; i < value; i++) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
            graphics.enableModifiers(SGR.BOLD);
            graphics.putString(new TerminalPosition(onScreenPosition.getX() + i, onScreenPosition.getY()), "X");
        }
    }

    public void remove1Hp() {
        onScreenPosition.setX(onScreenPosition.getX() - 1);
        value--;
    }
}
