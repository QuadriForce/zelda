package zelda;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;

import zelda.objects.worldObject;
import zelda.scenary.Quest;
import zelda.scenary.Rock;


import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

public class Zelda extends Game {

    private Link link;
    private Link link1;

    private Quest quest;
    private ArrayList<worldObject> worldObjects;
    private boolean menu;
    private int hitboxInset = 5;

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

    public void initObjectsResources() {
        //System.out.println(this.link.getWorldObjects());
        for (int i = 0; i < this.worldObjects.size(); i++) {
            if (!this.link.getWorldObjects().contains(this.worldObjects.get(i))) {
                String objectName = this.worldObjects.get(i).getName();
                //this.worldObjects.get(i) = new worldObject(this, objectName);
                this.worldObjects.get(i).setBoard(this.quest.getCurrentBoard());
                if (objectName.equals("keyDungeon"))
                    this.worldObjects.get(i).setLocation(200, 400);
                else if (objectName.equals("dungeonEntry")) {
                    this.worldObjects.get(i).setLocation(300, 300);
                }
            }
        }
    }

    public void update(long elapsedTime) {
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        Rectangle linkRect = new Rectangle(
                (int) this.link.getX() + this.hitboxInset,
                (int) this.link.getY() + this.hitboxInset,
                link.getWidth() - 2 * this.hitboxInset,
                link.getHeight() - 2 * this.hitboxInset
        );

        if (this.quest.getCurrentBoard().getObjects() != null) {
            this.worldObjects = this.quest.getCurrentBoard().getObjects();
            initObjectsResources();
            Rectangle[] objectRect = new Rectangle[this.worldObjects.size()];

            for (int i = 0; i < this.worldObjects.size(); i++) {
                objectRect[i] = new Rectangle(
                        (int) this.worldObjects.get(i).getX() + this.hitboxInset,
                        (int) this.worldObjects.get(i).getY() + this.hitboxInset,
                        this.worldObjects.get(i).getWidth() - 2 * this.hitboxInset,
                        this.worldObjects.get(i).getHeight() - 2 * this.hitboxInset
                );
                if (linkRect.intersects(objectRect[i])) {

                    if (this.worldObjects.get(i).getName() == "keyDungeon") {
                        this.link.addObject(this.worldObjects.get(i));
                        //System.out.println(this.quest.clearPlayField());
                        this.quest.clearPlayField();
                    } else if (this.worldObjects.get(i).getName() == "door") {
                        //this.link.setLocation(this.worldObjects.get(i).getX(), this.worldObjects.get(i).getY() + this.worldObjects.get(i).getHeight());
                        this.link.addObject(this.worldObjects.get(i));
                    }
                }
            }
        } else
            worldObjects = null;


            Rectangle link1Rect = new Rectangle(
                    (int) this.link1.getX() + this.hitboxInset,
                    (int) this.link1.getY() + this.hitboxInset,
                    link.getWidth() - 2 * this.hitboxInset,
                    link.getHeight() - 2 * this.hitboxInset
            );

        if (linkRect.intersects(link1Rect)) {
            if (link.getOrientation() == Orientation.WEST) {
                this.link.setLocation(this.link1.getX() + this.link1.getWidth(), this.link.getY());
            } else if (link.getOrientation() == Orientation.EAST) {
                this.link.setLocation(this.link1.getX() - this.link.getWidth(), this.link.getY());
            } else if (link.getOrientation() == Orientation.NORTH) {
                this.link.setLocation(this.link.getX(), this.link1.getY() + this.link1.getHeight());
            } else if (link.getOrientation() == Orientation.SOUTH) {
                this.link.setLocation(this.link.getX(), this.link1.getY() - this.link.getHeight());
            }
        }

        if (this.keyPressed(KeyEvent.VK_ALT)) {
            this.link.fight(this.link1, this.hitboxInset);
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

        //System.out.println(this.link.getX() + " " + this.link.getY());
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
    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.quest.render(g);
        this.link.render(g);
        if(this.link1.life > 0)
            this.link1.render(g);
        if (this.worldObjects != null && !this.worldObjects.isEmpty()) {
            for (int i = 0; i < this.worldObjects.size(); i++) {
                if (!this.link.getWorldObjects().contains(this.worldObjects.get(i))) {
                    this.worldObjects.get(i).render(g);
                }
            }
        }
    }

    public static void main(String[] args) {
        GameLoader game = new GameLoader();
        game.setup(new Zelda(), new Dimension(672, 588), false);
        game.start();
    }

}
