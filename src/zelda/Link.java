
package zelda;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import zelda.objects.Blade;
import zelda.objects.Shield;
import zelda.objects.worldObject;
import zelda.scenary.Board;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

public class Link extends AnimatedSprite {

    private static final double SPEED = 0.3;

    private static final int ANIMATION_DELAY = 100;

    private static final int FIGHT_TIMER = 300;
    public static final Shield.Kind DEFAULT_SHIELD = Shield.Kind.SMALL;
    public static final Orientation DEFAULT_ORIENTATION = Orientation.NORTH;

    private Game game;

    public int score;

    private HashSet<worldObject> worldObjects;

    private Blade.Kind blade;
    private int puissance;
    private SpriteGroup linkGroup;

    private Shield.Kind shield;
    private Orientation orientation;
    public int life;

    private Timer figth;
    private int hitboxInset = 5;
    public CollisionManager manager;

    public Link(Game game) {
        this.game = game;
        this.worldObjects = new HashSet<>();
        this.linkGroup = new SpriteGroup("LINK SPRITE GROUP");
        this.life = 100;
        this.shield = Link.DEFAULT_SHIELD;
        this.orientation = Link.DEFAULT_ORIENTATION;
        this.getAnimationTimer().setDelay(Link.ANIMATION_DELAY);
        this.figth = new Timer(Link.FIGHT_TIMER);
        this.figth.setActive(false);
        this.manager = new LinkCollisionManager();
        this.initResources();
    }

    private void initResources() {
        BufferedImage[] sprites = new BufferedImage[35];
        // Walk north
        sprites[0] = game.getImage("res/sprites/Link/GLWN1.gif");
        sprites[1] = game.getImage("res/sprites/Link/GLWN2.gif");
        // Walk south with small shield
        sprites[2] = game.getImage("res/sprites/Link/GLSSWS1.gif");
        sprites[3] = game.getImage("res/sprites/Link/GLSSWS2.gif");
        // Walk south with magical shield
        sprites[4] = game.getImage("res/sprites/Link/GLMSWS1.gif");
        sprites[5] = game.getImage("res/sprites/Link/GLMSWS2.gif");
        // Walk east with small shield
        sprites[6] = game.getImage("res/sprites/Link/GLSSWE1.gif");
        sprites[7] = game.getImage("res/sprites/Link/GLSSWE2.gif");
        // Walk east with magical shield
        sprites[8] = game.getImage("res/sprites/Link/GLMSWE1.gif");
        sprites[9] = game.getImage("res/sprites/Link/GLMSWE2.gif");
        // Walk west with small shield
        sprites[10] = game.getImage("res/sprites/Link/GLSSWW1.gif");
        sprites[11] = game.getImage("res/sprites/Link/GLSSWW2.gif");
        // Walk west with magical shield
        sprites[12] = game.getImage("res/sprites/Link/GLMSWW1.gif");
        sprites[13] = game.getImage("res/sprites/Link/GLMSWW2.gif");
        // Fight north with wood blade
        sprites[14] = game.getImage("res/sprites/Link/GLFWBN1.gif");
        sprites[15] = game.getImage("res/sprites/Link/GLFN1.gif");
        sprites[16] = game.getImage("res/sprites/Link/GLFWBN.gif");
        // Fight south with wood blade and small shield
        sprites[17] = sprites[2];
        sprites[18] = game.getImage("res/sprites/Link/GLFS.gif");
        sprites[19] = game.getImage("res/sprites/Link/GLFWBS.gif");
        // Fight south with wood blade and magical shield
        sprites[20] = sprites[4];
        sprites[21] = game.getImage("res/sprites/Link/GLFS.gif");
        sprites[22] = game.getImage("res/sprites/Link/GLFWBS.gif");
        // Fight east with wood blade and small shield
        sprites[23] = sprites[6];
        sprites[24] = game.getImage("res/sprites/Link/GLFE.gif");
        sprites[25] = game.getImage("res/sprites/Link/GLFWBE.gif");
        // Fight east with wood blade and magical shield
        sprites[26] = sprites[8];
        sprites[27] = game.getImage("res/sprites/Link/GLFE.gif");
        sprites[28] = game.getImage("res/sprites/Link/GLFWBE.gif");
        // Fight west with wood blade and small shield
        sprites[29] = game.getImage("res/sprites/Link/GLFSSWBW.gif");
        sprites[30] = game.getImage("res/sprites/Link/GLFW.gif");
        sprites[31] = game.getImage("res/sprites/Link/GLFWBW.gif");
        // Fight west with wood blade and magical shield
        sprites[32] = game.getImage("res/sprites/Link/GLFMSWBW.gif");
        sprites[33] = game.getImage("res/sprites/Link/GLFW.gif");
        sprites[34] = game.getImage("res/sprites/Link/GLFWBW.gif");

        this.setImages(sprites);
        this.setLocation(256, 380);
        this.setAnimationFrame(0, 0);
    }

    public HashSet<worldObject> getWorldObjects() {
        return this.worldObjects;
    }

    public void addObject(worldObject obj) {
        this.worldObjects.add(obj);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setBoard(Board board) {
        linkGroup.add(this);
        this.manager.setCollisionGroup(linkGroup, board.getForeground());
    }

    public void takeDamage() {
        this.life -= 30;
        if (this.life <= 0) {
            SoundPlayer soundPlayer = new SoundPlayer("res/sounds/LOZ_Die.wav");
            soundPlayer.play();
            this.setLocation(0, 0); //544
            this.linkGroup.remove(this);

        }
    }

    public SpriteGroup getLinkGroup() {
        return this.linkGroup;
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        if (this.figth.action(elapsedTime)) {
            this.figth.setActive(false);
            if (this.orientation.equals(Orientation.WEST)) {
                this.setX(this.getX() + 22);
                if (this.shield.equals(Shield.Kind.SMALL)) {
                    this.setAnimationFrame(10, 10);
                } else {
                    this.setAnimationFrame(12, 12);
                }
            } else if (this.orientation.equals(Orientation.NORTH)) {
                this.setY(this.getY() + 22);
                this.setAnimationFrame(0, 0);
            }
        }
        if (this.manager != null)
            this.manager.checkCollision();
    }

    public void render(Graphics2D g) {
        super.render(g);
    }

    public void walk(Orientation direction) {
        if (!this.figth.isActive()) {
            switch (direction) {
                case NORTH:
                    this.setAnimationFrame(0, 1);
                    this.setAnimate(true);
                    this.setVerticalSpeed(-Link.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.NORTH;
                    break;
                case SOUTH:
                    switch (this.shield) {
                        case SMALL:
                            this.setAnimationFrame(2, 3);
                            break;
                        case MAGICAL:
                            this.setAnimationFrame(4, 5);
                            break;
                        default:
                            // do nothing
                    }
                    this.setAnimate(true);
                    this.setVerticalSpeed(Link.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.SOUTH;
                    break;
                case EAST:
                    switch (this.shield) {
                        case SMALL:
                            this.setAnimationFrame(6, 7);
                            break;
                        case MAGICAL:
                            this.setAnimationFrame(8, 9);
                            break;
                        default:
                            // do nothing
                    }
                    this.setAnimate(true);
                    this.setHorizontalSpeed(Link.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.EAST;
                    break;
                case WEST:
                    switch (this.shield) {
                        case SMALL:
                            this.setAnimationFrame(10, 11);
                            break;
                        case MAGICAL:
                            this.setAnimationFrame(12, 13);
                            break;
                        default:
                            // do nothing
                    }
                    this.setAnimate(true);
                    this.setHorizontalSpeed(-Link.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.WEST;
                    break;
                default:
                    // do nothing
            }
        }
    }

    public void autoWalk() {
        if (!this.figth.isActive()) {
            int index = (int) (Math.random() * 5);
            Orientation direction = Orientation.WEST;
            switch (index) {
                case 0: // left
                    direction = Orientation.WEST;
                    this.setAnimate(true);
                    this.setHorizontalSpeed(-Link.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.WEST;
                    break;
                case 1: // right
                    this.setAnimate(true);
                    this.setHorizontalSpeed(Link.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.EAST;
                    break;
                case 2: // up
                    this.setAnimationFrame(0, 1);
                    this.setAnimate(true);
                    this.setVerticalSpeed(-Link.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.NORTH;
                    break;
                case 3: // down
                    this.setAnimate(true);
                    this.setVerticalSpeed(Link.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.SOUTH;
                    break;
                case 4: // attack
                    break;
                default:
                    break;
            }
        }

        /*if(!this.figth.isActive()) {
        switch (direction) {
            case NORTH:
                this.setAnimationFrame(0, 1);
                this.setAnimate(true);
                this.setVerticalSpeed(-Link.SPEED);
                this.setHorizontalSpeed(0);
                this.orientation = Orientation.NORTH;
                break;
            case SOUTH:
                switch (this.shield) {
                    case SMALL:
                        this.setAnimationFrame(2, 3);
                        break;
                    case MAGICAL:
                        this.setAnimationFrame(4, 5);
                        break;
                    default:
                        // do nothing
                }
                this.setAnimate(true);
                this.setVerticalSpeed(Link.SPEED);
                this.setHorizontalSpeed(0);
                this.orientation = Orientation.SOUTH;
                break;
            case EAST:
                switch (this.shield) {
                    case SMALL:
                        this.setAnimationFrame(6, 7);
                        break;
                    case MAGICAL:
                        this.setAnimationFrame(8, 9);
                        break;
                    default:
                        // do nothing
                }
                this.setAnimate(true);
                this.setHorizontalSpeed(Link.SPEED);
                this.setVerticalSpeed(0);
                this.orientation = Orientation.EAST;
                break;
            case WEST:
                switch (this.shield) {
                    case SMALL:
                        this.setAnimationFrame(10, 11);
                        break;
                    case MAGICAL:
                        this.setAnimationFrame(12, 13);
                        break;
                    default:
                        // do nothing
                }
                this.setAnimate(true);
                this.setHorizontalSpeed(-Link.SPEED);
                this.setVerticalSpeed(0);
                this.orientation = Orientation.WEST;
                break;
            default:
                // do nothing
        }
    }*/
    }

    private boolean checkHit(Link adv) {
        if (this.getRectPos().intersects(adv.getRectPos())) {
            SoundPlayer soundPlayer = new SoundPlayer("res/sounds/LOZ_Hit.wav");
            soundPlayer.play();
            adv.takeDamage();
            System.out.println("adverse took damage! -30 hp to :" + adv.life);
            return true;
        }
        return false;
    }

    public void fight(Link adv) {
        if (!this.figth.isActive()) {
            this.setSpeed(0, 0);
            this.figth.setActive(true);
            switch (this.orientation) {
                case NORTH:
                    //System.out.println("avant: "+ this.getY());
                    this.setY(this.getY() - 20);
                    //System.out.println("après: "+this.getY());
                    checkHit(adv);
                    this.setAnimationFrame(14, 16);
                    this.setAnimate(true);
                    break;
                case SOUTH:
                    this.setY(this.getY() + 20);
                    checkHit(adv);
                    switch (this.shield) {
                        case SMALL:
                            this.setAnimationFrame(17, 19);
                            break;
                        case MAGICAL:
                            this.setAnimationFrame(20, 22);
                            break;
                        default:
                            // do nothing
                    }
                    this.setAnimate(true);
                    break;
                case EAST:
                    this.setX(this.getX() + 10);
                    checkHit(adv);
                    switch (this.shield) {
                        case SMALL:
                            this.setAnimationFrame(23, 25);
                            break;
                        case MAGICAL:
                            this.setAnimationFrame(26, 28);
                            break;
                        default:
                            // do nothing
                    }
                    this.setAnimate(true);
                    break;
                case WEST:
                    this.setX(this.getX() - 10);
                    checkHit(adv);
                    switch (this.shield) {
                        case SMALL:
                            this.setAnimationFrame(29, 31);
                            break;
                        case MAGICAL:
                            this.setAnimationFrame(32, 34);
                            break;
                        default:
                            // do nothing
                    }
                    this.setAnimate(true);
                    this.orientation = Orientation.WEST;
                    break;
                default:
                    // do nothing
            }
        }
    }
    
/*    public void takeDamage(int damage) {
        //si collision alors damage
        if
        this.life -= damage;
        if (this.life <= 0) {
            // mort, implémenter la logique de suppression de l'ennemi
            this.die();
//            this.manager.removeFromCollision();
//            game.increaseScore(100); // Ajouter un score de 100 points pour avoir vaincu un ennemi
        }
    }*/


    private class LinkCollisionManager extends AdvanceCollisionGroup {

        public LinkCollisionManager() {
            this.pixelPerfectCollision = false;

        }

        public void collided(Sprite s1, Sprite s2) {
            this.revertPosition1();
        }

    }

    public Rectangle getRectPos() {
        Rectangle RectPos = new Rectangle(
                (int) this.getX() + this.hitboxInset,
                (int) this.getY() + this.hitboxInset,
                this.getWidth() - 2 * this.hitboxInset,
                this.getHeight() - 2 * this.hitboxInset
        );
        return RectPos;
    }


}
    
