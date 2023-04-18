package zelda;

import java.awt.*;
import java.awt.event.KeyEvent;

import zelda.scenary.Quest;
import zelda.scenary.Rock;


import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

public class Zelda extends Game {

    private Link link;
    private Link link1;

    private Quest quest;

    private boolean menu;

    public Zelda() {

    }

    private Rock rock;

    public void initResources() {

        this.quest = new Quest(this);
        this.link = new Link(this);
        this.link.setBoard(this.quest.getCurrentBoard());
        this.menu = false;
        this.link1 = new Link(this);
        this.link1.setBoard(this.quest.getCurrentBoard());
        this.link1.setLocation(200, 200); //544
        this.link1.setSpeed(0, 0);
        System.out.println("width: " + this.getWidth() + " height: " + this.getHeight());
    }

    public void update(long elapsedTime) {
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        boolean canMove = true;
        Rectangle linkRect = new Rectangle(
                (int) this.link.getX(), (int) this.link.getY(),
                link.getWidth(), link.getHeight()
        );

        Rectangle link1Rect = new Rectangle(
                (int) this.link1.getX(), (int) this.link1.getY(),
                link.getWidth(), link.getHeight()
        );

        if (linkRect.intersects(link1Rect)) {
            canMove = false;
            if (link.getOrientation() == Orientation.WEST) {
                this.link.setLocation(this.link1.getX() + this.link1.getWidth() + 5, this.link.getY());
            } else if (link.getOrientation() == Orientation.EAST) {
                this.link.setLocation(this.link1.getX() - this.link.getWidth() - 5, this.link.getY());
            } else if (link.getOrientation() == Orientation.NORTH) {
                this.link.setLocation(this.link.getX(), this.link1.getY() + this.link1.getHeight() + 5);
            } else if (link.getOrientation() == Orientation.SOUTH) {
                this.link.setLocation(this.link.getX(), this.link1.getY() - this.link.getHeight() - 5);
            }
        }

        if (this.keyPressed(KeyEvent.VK_ALT)) {
            this.link.fight();
        } else if (this.keyDown(KeyEvent.VK_LEFT)) {
            this.link.walk(Orientation.WEST);
        } else if (this.keyDown(KeyEvent.VK_RIGHT)) {
            this.link.walk(Orientation.EAST);
        } else if (this.keyDown(KeyEvent.VK_UP)) {
            this.link.walk(Orientation.NORTH);
        } else if (this.keyDown(KeyEvent.VK_DOWN)) {
            this.link.walk(Orientation.SOUTH);
        } else if (keyPressed(KeyEvent.VK_ESCAPE)) {
            finish();
        } else {
            this.link.setSpeed(0, 0);
        }
        System.out.println(this.link.getX() + " " + this.link.getY());
        //System.out.println("width: " + this.getWidth() + " height: " + this.getHeight());
        if (link.getOrientation() == Orientation.WEST) {
            if (link.getX() < -10) {
                left = true;
            }
        }
        if (link.getOrientation() == Orientation.EAST) {

            if (this.link.getX() > this.getHeight() + 50) {
                right = true;
            }
        }
        if (link.getOrientation() == Orientation.SOUTH) {

            if (this.link.getY() > 543) {
                down = true;
            }
        }
        if (link.getOrientation() == Orientation.NORTH) {

            if (this.link.getY() < 115) {
                up = true;
            }
        }
        if (left) {
            this.quest.y -= 1;
            this.link.setLocation(this.getWidth() - 30, this.link.getY());
            this.link.setAnimationFrame(0, 0);
        }
        if (right) {
            this.quest.y += 1;
            this.link.setLocation(0, this.link.getY());
            this.link.setAnimationFrame(0, 0);
        }
        if (up) {
            this.quest.x -= 1;
            this.link.setLocation(this.link.getX(), this.getHeight() - 30); //544
            this.link.setAnimationFrame(0, 0);
        }
        if (down) {
            this.quest.x += 1;
            this.link.setLocation(this.link.getX(), 115); //115 ==> hauteur du fond noir
            this.link.setAnimationFrame(0, 0);
        }

        this.quest.update(elapsedTime);
        this.link.update(elapsedTime);
        this.link1.update(elapsedTime);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.quest.render(g);
        this.link.render(g);
        this.link1.render(g);
    }

    public static void main(String[] args) {
        GameLoader game = new GameLoader();
        game.setup(new Zelda(), new Dimension(672, 588), false);
        game.start();
    }

}
