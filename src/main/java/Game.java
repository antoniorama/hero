import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.KeyStroke;

import javax.swing.*;
import java.io.IOException;

public class Game {
    private Screen screen;

    private int x = 10;
    private int y = 10;
    public Game() {
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void draw() throws IOException {
        this.screen.clear();
        this.screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
        this.screen.refresh();
    }

    private void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp) y--;
        if (key.getKeyType() == KeyType.ArrowDown) y++;
        if (key.getKeyType() == KeyType.ArrowRight) x++;
        if (key.getKeyType() == KeyType.ArrowLeft) x--;
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') screen.close();
    }

    public void run() throws IOException {
        while (true) {
            draw();
            KeyStroke key = screen.readInput();
            processKey(key);
            if (key.getKeyType() == KeyType.EOF) break;
        }
    }
}