import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;
    private LifeBar heroLifeBar;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
        this.heroLifeBar = new LifeBar(3, new Position(0, 20));
    }


    public boolean canHeroMove(Position position) {
        if (position.getX() < 0) return false;
        if (position.getX() > width - 1) return false;
        if (position.getY() < 0) return false;
        if (position.getY() > height - 1) return false;

        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return false;
        }

        return true;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public void processKey(KeyStroke key, TextGraphics graphics) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp) moveHero(hero.moveUp());
        if (key.getKeyType() == KeyType.ArrowDown) moveHero(hero.moveDown());
        if (key.getKeyType() == KeyType.ArrowRight) moveHero(hero.moveRight());
        if (key.getKeyType() == KeyType.ArrowLeft) moveHero(hero.moveLeft());
        retrieveCoins();
        moveMonsters();
        verifyMonsterCollisions(graphics);
    }

    public void draw(TextGraphics graphics) {
        //Background
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        this.hero.draw(graphics);
        //Walls
        for (Wall wall : walls)
            wall.draw(graphics);
        //Coins
        for (Coin coin : coins)
            coin.draw(graphics);
        //Monsters
        for (Monster monster : monsters)
            monster.draw(graphics);
        //LifeBar
        heroLifeBar.draw(graphics);
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 2));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 3) + 1));
        return coins;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 3) + 1));
        return monsters;
    }

    private boolean canMonsterMove(Monster monster, Position movePosition) {
        for (Wall wall : walls)
            if (wall.getPosition().equals(movePosition)) return false;
        return true;
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position movePosition = monster.move();
            if (canMonsterMove(monster, movePosition))
                monster.setPosition(movePosition);
        }
    }

    private void verifyMonsterCollisions(TextGraphics graphics) throws IOException {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                heroLifeBar.remove1Hp();
                if (heroLifeBar.getValue() == 1) {
                    System.out.println("Game Over!");
                    System.exit(0);
                }
            }
        }
    }

}
