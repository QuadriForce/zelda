package zelda.enemies;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.*;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;
import zelda.Link;
import zelda.Orientation;
import zelda.SoundPlayer;
import zelda.objects.Shield;
import zelda.objects.worldObject;
import zelda.scenary.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends AnimatedSprite {
    private static final double SPEED = 0.3;
    private static final int ANIMATION_DELAY = 100;

    private static final int FIGHT_TIMER = 300;
    public static final Shield.Kind DEFAULT_SHIELD = Shield.Kind.SMALL;
    public static final Orientation DEFAULT_ORIENTATION = Orientation.NORTH;

    private Game game;

    private SpriteGroup EnemyGroup;

    private Shield.Kind shield;
    private Orientation orientation;
    public int life;

    private Timer figth;
    private int hitboxInset = 5;
    public CollisionManager manager;

    public Enemy(Game game) {
        this.game = game;

        this.EnemyGroup = new SpriteGroup("Enemies SPRITE GROUP");
        this.life = 100;
        this.shield = Link.DEFAULT_SHIELD;
        this.orientation = Link.DEFAULT_ORIENTATION;
        this.getAnimationTimer().setDelay(Enemy.ANIMATION_DELAY);

        this.figth = new Timer(Enemy.FIGHT_TIMER);
        this.figth.setActive(false);

        this.manager = new Enemy.LinkCollisionManager();

        this.initResources();
    }

    private void initResources() {
        BufferedImage[] sprites = new BufferedImage[2];
        sprites[0] = game.getImage("res/sprites/Enemies/soldat1.png");
        sprites[1] = game.getImage("res/sprites/Enemies/soldat2.png");

        this.setImages(sprites);
        this.setLocation(256, 380);
        this.setAnimationFrame(0, 0);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setBoard(Board board) {
        EnemyGroup.add(this);
        this.manager.setCollisionGroup(EnemyGroup, board.getForeground());
    }

    public void takeDamage() {
        this.life -= 50;
        if (this.life <= 0) {
            SoundPlayer soundPlayer = new SoundPlayer("res/sounds/LOZ_Sword.wav");
            soundPlayer.play();
            this.setLocation(0, 0);
            this.EnemyGroup.remove(this);
            int index = (int) (Math.random() * 5);
            worldObject dropItem;
            //drop enemies
            switch (index) {
                case 0:
                    dropItem = new worldObject(this.game, "crystal");
                    dropItem.setLocation(this.getX(), this.getY());
                    break;
                case 1:
                    dropItem = new worldObject(this.game, "heart1");
                    dropItem.setLocation(this.getX(), this.getY());
                    break;
                case 2:
                    dropItem = new worldObject(this.game, "heart2");
                    dropItem.setLocation(this.getX(), this.getY());
                    break;
                default:
                    break;
            }
        }
    }

    public SpriteGroup getLinkGroup() {
        return this.EnemyGroup;
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

    public void autoWalk() {
        if (!this.figth.isActive()) {
            int index = (int) (Math.random() * 5);
            Orientation direction = Orientation.WEST;
            switch (index) {
                case 0: // left
                    direction = Orientation.WEST;
                    this.setAnimate(true);
                    this.setHorizontalSpeed(-Enemy.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.WEST;
                    break;
                case 1: // right
                    this.setAnimate(true);
                    this.setHorizontalSpeed(Enemy.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.EAST;
                    break;
                case 2: // up
                    this.setAnimationFrame(0, 1);
                    this.setAnimate(true);
                    this.setVerticalSpeed(-Enemy.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.NORTH;
                    break;
                case 3: // down
                    this.setAnimate(true);
                    this.setVerticalSpeed(Enemy.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.SOUTH;
                    break;
                default://attack
                    //this.fight(link);
                    break;
            }
        }
    }

    private boolean checkHit(Link link) {
        if (this.getRectPos().intersects(link.getRectPos())) {
            SoundPlayer soundPlayer = new SoundPlayer("res/sounds/LOZ_Hit.wav");
            soundPlayer.play();
            link.takeDamage();
            //System.out.println("adverse took damage! -1 hp to :" + link.life);
            return true;
        }
        return false;
    }

    public void fight(Link link) {
        if (!this.figth.isActive()) {
            this.setSpeed(0, 0);
            this.figth.setActive(true);
            switch (this.orientation) {
                case NORTH:
                    //System.out.println("avant: "+ this.getY());
                    this.setY(this.getY() - 20);
                    //System.out.println("après: "+this.getY());
                    checkHit(link);
                    this.setAnimationFrame(14, 16);
                    this.setAnimate(true);
                    break;
                case SOUTH:
                    this.setY(this.getY() + 20);
                    checkHit(link);
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
                    checkHit(link);
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
                    /*if(checkHit(adv)){
                        this.setX(this.getX() + 15);
                    }*/
                    checkHit(link);
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


    public void render(Graphics2D g) {
        super.render(g);
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


    private class LinkCollisionManager extends AdvanceCollisionGroup {

        public LinkCollisionManager() {
            this.pixelPerfectCollision = false;

        }

        public void collided(Sprite s1, Sprite s2) {
            this.revertPosition1();
        }

    }


    public int getLife() {
        return life;
    }
}