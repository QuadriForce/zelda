package zelda;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import zelda.enemies.Enemy;
import zelda.objects.worldObject;
import zelda.scenary.Quest;
import zelda.scenary.Rock;


import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

public class Zelda extends Game {

    private Link link;
    private ArrayList<Enemy> enemies = new ArrayList<>();
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
        int randomNumber = (int) (Math.random() * 6) + 1;
        for (int i = 0; i < randomNumber; i++) {
            Enemy enemy = new Enemy(this);
            enemy.setBoard(this.quest.getCurrentBoard());
            enemies.add(enemy);
        }
        //this.enemy1.setLocation(200, 250); //544
        //this.link1.setSpeed(0, 0);
        System.out.println("width: " + this.getWidth() + " height: " + this.getHeight());
    }

    public void initObjectsResources() {
        //System.out.println(this.link.getWorldObjects());
        for (worldObject obj : this.worldObjects) {
            if (!this.link.getWorldObjects().contains(obj)) {
                String objectName = obj.getName();
                //this.worldObjects.get(i) = new worldObject(this, objectName);
                //obj.setBoard(this.quest.getCurrentBoard());
                if (objectName.equals("crystal"))
                    obj.setLocation(200, 400);
                else if (objectName.equals("dungeonEntry")) {
                    obj.setLocation(300, 300);
                }
            }
        }
    }

    public void checkMapTransition() {
        Orientation orientation = this.link.getOrientation();
        double x = this.link.getX();
        double y = this.link.getY();

        boolean moveLeft = false;
        boolean moveRight = false;
        boolean moveUp = false;
        boolean moveDown = false;

        switch (orientation) {
            case WEST:
                moveLeft = x < -10;
                break;
            case EAST:
                moveRight = x > getHeight() + 50;
                break;
            case SOUTH:
                moveDown = y > 543;
                break;
            case NORTH:
                moveUp = y < 115;
                break;
        }

        if (moveLeft) {
            this.quest.y -= 1;
            this.link.setLocation(getWidth() - 30, y);
            this.link.setAnimationFrame(0, 0);
        }
        if (moveRight) {
            this.quest.y += 1;
            this.link.setLocation(0, y);
            this.link.setAnimationFrame(0, 0);
        }
        if (moveUp) {
            this.quest.x -= 1;
            this.link.setLocation(x, getHeight() - 30);
            this.link.setAnimationFrame(0, 0);
        }
        if (moveDown) {
            this.quest.x += 1;
            this.link.setLocation(x, 115);
            this.link.setAnimationFrame(0, 0);
        }
    }

    public void update(long elapsedTime) {
        if (this.quest.getCurrentBoard().getObjects() != null) {
            this.worldObjects = this.quest.getCurrentBoard().getObjects();
            initObjectsResources();

            for (int i = 0; i < this.worldObjects.size(); i++) {
                if (this.link.getRectPos().intersects(this.worldObjects.get(i).getRectPos())) {
                    SoundPlayer soundPlayer;
                    switch (this.worldObjects.get(i).getName()){
                        case "heart1":
                            this.link.setLife(this.link.getLife()+1);
                            soundPlayer = new SoundPlayer("res/sounds/LOZ_Get_Heart.wav");
                            soundPlayer.play();
                            this.quest.getCurrentBoard().getObjects().remove(i);
                            break;
                        case "heart2":
                            this.link.setLife(this.link.getLife()+2);
                            soundPlayer = new SoundPlayer("res/sounds/LOZ_Get_Heart.wav");
                            soundPlayer.play();
                            this.quest.getCurrentBoard().getObjects().remove(i);
                            break;
                        case "crystal":
                            this.link.addObject(this.worldObjects.get(i));
                            soundPlayer = new SoundPlayer("res/sounds/LOZ_Get_Rupee.wav");
                            soundPlayer.play();
                            this.quest.getCurrentBoard().getObjects().remove(i);
                            break;
                        case "keyDungeon":
                            this.link.addObject(this.worldObjects.get(i));
                            soundPlayer = new SoundPlayer("res/sounds/LOZ_Get_Item.wav");
                            soundPlayer.play();
                            this.quest.getCurrentBoard().getObjects().remove(i);
                            break;
                        default:
                            break;
                    }
                }
            }
        } else
            worldObjects = null;

        for (Enemy enemy : this.enemies) {
            if (this.link.getRectPos().intersects(enemy.getRectPos())) {
                if (link.getOrientation() == Orientation.WEST) {
                    this.link.setLocation(enemy.getX() + enemy.getWidth(), this.link.getY());
                    if(enemy.getOrientation() == Orientation.WEST)
                        this.link.takeDamage();
                } else if (link.getOrientation() == Orientation.EAST) {
                    this.link.setLocation(enemy.getX() - this.link.getWidth(), this.link.getY());
                    if(enemy.getOrientation() == Orientation.EAST)
                        this.link.takeDamage();
                } else if (link.getOrientation() == Orientation.NORTH) {
                    this.link.setLocation(this.link.getX(), enemy.getY() + enemy.getHeight());
                    if(enemy.getOrientation() == Orientation.NORTH)
                        this.link.takeDamage();
                } else if (link.getOrientation() == Orientation.SOUTH) {
                    this.link.setLocation(this.link.getX(), enemy.getY() - this.link.getHeight());
                    if(enemy.getOrientation() == Orientation.SOUTH)
                        this.link.takeDamage();
                }
            }
        }


        if (this.keyPressed(KeyEvent.VK_ALT)) {
            this.link.fight(this.enemies);
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

        for (Enemy enemy : this.enemies) {
            enemy.autoWalk();
        }
        checkMapTransition();

        //System.out.println(this.link.getX() + " " + this.link.getY());
        //System.out.println("width: " + this.getWidth() + " height: " + this.getHeight());


        this.quest.update(elapsedTime);
        this.link.update(elapsedTime);
        //this.link1.update(5);
        for (Enemy enemy : this.enemies) {
            enemy.update(5);
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.quest.render(g);
        if (this.link.getLife() > 0) {
            this.link.render(g);
            for (Enemy enemy : this.enemies) {
                if (enemy.life > 0)
                    enemy.render(g);
            }
            if (this.worldObjects != null && !this.worldObjects.isEmpty()) {
                for (int i = 0; i < this.worldObjects.size(); i++) {
                    if (!this.link.getWorldObjects().contains(this.worldObjects.get(i))) {
                        this.worldObjects.get(i).render(g);
                    }
                }
            }
        } else {
            this.quest.x = 4;
            this.quest.y = 2;
        }
    }

    public static void main(String[] args) {
        GameLoader game = new GameLoader();
        game.setup(new Zelda(), new Dimension(672, 588), false);
        game.start();
    }

}
