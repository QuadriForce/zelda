package zelda.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

import zelda.Link;
import zelda.Orientation;
import zelda.SoundPlayer;
// import zelda.enemies.Enemies.EnemiesCollisionManager;
import zelda.objects.Blade;
import zelda.objects.Shield;
import zelda.scenary.Board;

public class Enemies extends AnimatedSprite{
	private static final double SPEED = 0.2;
    private static final int ANIMATION_DELAY = 100;
    private static final int FIGHT_TIMER = 300;
	public static final Orientation DEFAULT_ORIENTATION = Orientation.SOUTH;
	public static final Shield.Kind DEFAULT_SHIELD = Shield.Kind.SMALL;

    private Game game;
    private Blade.Kind blade;
    private Shield.Kind shield;
    private Orientation orientation;
    private int life;
    private Timer figth;
    private int hitboxInset = 5;
    private CollisionManager manager;
    private String name;
    private long lastMoveTime; // Temps en millisecondes du dernier déplacement
    private long moveInterval; // Temps entre chaque déplacement automatique
    

    public Enemies(Game game) {
        this.game = game;
        this.shield = Link.DEFAULT_SHIELD;
        this.orientation = Enemies.DEFAULT_ORIENTATION;
        this.getAnimationTimer().setDelay(Enemies.getAnimationDelay());
        this.figth = new Timer(Enemies.getFightTimer());
        this.figth.setActive(false);
        this.manager = new EnemiesCollisionManager();
        this.initResources();
        //this.weapon = new Weapon(weaponType);
        this.life = 100;
        this.name = "Enemy";
        this.lastMoveTime = System.currentTimeMillis();
        this.moveInterval = 500;
        //this.speed = speed;
    }

    public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public void getName (String name) {
		this.name = name;
	}

	public static int getFightTimer() {
		return FIGHT_TIMER;
	}

	public static int getAnimationDelay() {
		return ANIMATION_DELAY;
	}

	private void initResources() {
		// Charger les images pour les animations des ennemis
    	BufferedImage[] sprites = new BufferedImage[2];
    	sprites[0] = game.getImage("res/sprites/Enemies/soldat1.png");
        sprites[1] = game.getImage("res/sprites/Enemies/soldat2.png");
        // Assigner les images à l'objet AnimatedSprite
    	this.setImages(sprites);
        this.setLocation(200, 300);
        this.setAnimationFrame(0, 4);
    }
	
	// Ajouter l'objet AnimatedSprite (ennemies) au groupe de sprites du plateau de jeu
    public void setBoard(Board board) {       
        SpriteGroup Enemies = new SpriteGroup("Enemies SPRITE GROUP");
        Enemies.add(this);
        this.manager.setCollisionGroup(Enemies, board.getForeground());
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        if (this.figth.action(elapsedTime)) {
            this.figth.setActive(false);
            if (this.orientation.equals(Orientation.WEST)) {
                this.setX(this.getX() + 22);
                if (this.shield.equals(Shield.Kind.SMALL)) {
                    this.setAnimationFrame(1, 1);
                } else {
                    this.setAnimationFrame(2, 2);
                }
            } else if (this.orientation.equals(Orientation.NORTH)) {
                this.setY(this.getY() + 22);
                this.setAnimationFrame(0, 0);
            }
        }
        if (this.manager != null) 
            this.manager.checkCollision();
    }
   
    
    /*public void walk(Orientation direction) {
    	
    direction = getRandomDirection();
    

    	switch (direction) {
        case NORTH:
            this.setAnimationFrame(0, 1);
            this.setAnimate(true);
            this.setVerticalSpeed(-Enemies.SPEED);
            this.setHorizontalSpeed(0);
            this.orientation = Orientation.NORTH;
            break;
        case SOUTH:
            switch(this.shield) {
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
            this.setVerticalSpeed(Enemies.SPEED);
            this.setHorizontalSpeed(0);
            this.orientation = Orientation.SOUTH;
            break;
        case EAST:
            switch(this.shield) {
            case SMALL:
                this.setAnimationFrame(3, 4);
                break;
            case MAGICAL:
                this.setAnimationFrame(2, 2);
                break;
            default:
                // do nothing
            }
            this.setAnimate(true);
            this.setHorizontalSpeed(Enemies.SPEED);
            this.setVerticalSpeed(0);
            this.orientation = Orientation.EAST;
            break;
        case WEST:
            switch(this.shield) {
            case SMALL:
                this.setAnimationFrame(1, 1);
                break;
            case MAGICAL:
                this.setAnimationFrame(2, 3);
                break;
            default:
                // do nothing
            }
            this.setAnimate(true);
            this.setHorizontalSpeed(-Enemies.SPEED);
            this.setVerticalSpeed(0);
            this.orientation = Orientation.WEST;
            break;
        default:
            // do nothing
        }
    }*/


    
    // Méthode pour vérifier si une direction est valide
    private boolean isDirectionValid(Orientation direction) {
        if (direction.equals(Orientation.EAST) && this.getX() + 22 >= 16) {
            return false;
        } else if (direction.equals(Orientation.WEST) && this.getX() - 22 < 0) {
            return false;
        } else if (direction.equals(Orientation.SOUTH) && this.getY() + 22 >= 11) {
            return false;
        } else if (direction.equals(Orientation.NORTH) && this.getY() - 22 < 0) {
            return false;
        }
        return true;
    }

    
	// Dessiner à l'écran le sprite de l'ennemi et les objets associés
    public void render(Graphics2D g) {
        super.render(g);
    }
     	
	// Empêcher les sprites de se chevaucher ou de traverser les uns les autres lorsqu'une collision est détectée.
	private class EnemiesCollisionManager extends AdvanceCollisionGroup {	
	    public EnemiesCollisionManager() {
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
    public void takeDamage() {
        this.life -= 50;
        if (this.life <= 0) {
            SoundPlayer soundPlayer = new SoundPlayer("res/sounds/LOZ_Die.wav");
            soundPlayer.play();
            this.setLocation(0, 0); //544


        }
    }
    public void autoWalk(Link link) {
        if (!this.figth.isActive()) {
            int index = (int) (Math.random() * 5);
            Orientation direction = Orientation.WEST;
            switch (index) {
                case 0: // left
                    direction = Orientation.WEST;
                    this.setAnimate(true);
                    this.setHorizontalSpeed(-Enemies.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.WEST;
                    break;
                case 1: // right
                    this.setAnimate(true);
                    this.setHorizontalSpeed(Enemies.SPEED);
                    this.setVerticalSpeed(0);
                    this.orientation = Orientation.EAST;
                    break;
                case 2: // up
                    this.setAnimationFrame(0, 1);
                    this.setAnimate(true);
                    this.setVerticalSpeed(-Enemies.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.NORTH;
                    break;
                case 3: // down
                    this.setAnimate(true);
                    this.setVerticalSpeed(Enemies.SPEED);
                    this.setHorizontalSpeed(0);
                    this.orientation = Orientation.SOUTH;
                    break;
                default://attack
                    this.fight(link);
            }
        }
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private static final double SPEED = 0.2;
    private static final int ANIMATION_DELAY = 100;
    private static final int FIGHT_TIMER = 300;
	public static final Orientation DEFAULT_ORIENTATION = null;

    private Game game;
    private Orientation orientation;
    private int life;
    private Timer figth;
    private CollisionManager manager;    
	    
    public Enemies(Game game, int life) {
        this.game = game;
        this.orientation = Enemies.DEFAULT_ORIENTATION;
        this.getAnimationTimer().setDelay(Enemies.getAnimationDelay());
        this.figth = new Timer(Enemies.getFightTimer());
        this.figth.setActive(false);
        this.manager = new EnemiesCollisionManager();
        this.initResources();
        this.life = life;
    }

    public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public static int getFightTimer() {
		return FIGHT_TIMER;
	}

	public static int getAnimationDelay() {
		return ANIMATION_DELAY;
	}

	private void initResources() {
		// Charger les images pour les animations des ennemis

    	BufferedImage[] sprites = new BufferedImage[5];
    
    	sprites[0] = game.getImage("/Characters/CM.gif");
        sprites[1] = game.getImage("/Characters/COL1.gif");
        sprites[2] = game.getImage("/Characters/COL2.gif");
        sprites[3] = game.getImage("/Characters/COM.gif");
        sprites[4] = game.getImage("/Characters/CZ.gif");        
        // Assigner les images à l'objet AnimatedSprite
    	this.setImages(sprites);
        this.setLocation(256, 380);
        this.setAnimationFrame(0, 0);
    }

    public void setBoard(Board board) {
        // Ajouter l'objet AnimatedSprite (ennemies) au groupe de sprites du plateau de jeu
        SpriteGroup Enemies = new SpriteGroup("Enemies SPRITE GROUP");
        Enemies.add(this);
        this.manager.setCollisionGroup(Enemies, board.getForeground());
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        if (this.figth.action(elapsedTime)) {
            this.figth.setActive(false);
            if (this.orientation.equals(Orientation.WEST)) {
                this.setX(this.getX() + 22);
            } else if (this.orientation.equals(Orientation.NORTH)) {
                this.setY(this.getY() + 22);              
            }
        }
        if (this.manager != null) 
            this.manager.checkCollision();
    }

    public void fight() {
        if (!this.figth.isActive()) {
            this.figth.setActive(true);
        }
    }

    public void takeDamage(int damage) {
        this.life -= damage;
        if (this.life <= 0) {
            // Ennemi mort, implémenter la logique de suppression de l'ennemi
            this.removeFromGroup();
//            this.manager.removeFromCollision();
//            game.increaseScore(100); // Ajouter un score de 100 points pour avoir vaincu un ennemi
        }
    }
    
    public void removeFromGroup() {
		// TODO Auto-generated method stub
	}
    
    public void removeFromCollision() {
		// TODO Auto-generated method stub
	}

    //methode attack : quand est-ce que l'ennemie attaque ?
    
	// Dessiner à l'écran le sprite de l'ennemi et les objets associés
    public void render(Graphics2D g) {
        super.render(g);
    }
     
	
	// Empêcher les sprites de se chevaucher ou de traverser les uns les autres lorsqu'une collision est détectée.
	private class EnemiesCollisionManager extends AdvanceCollisionGroup {	
	    public EnemiesCollisionManager() {
	        this.pixelPerfectCollision = false;
	    }
	    
	    public void collided(Sprite s1, Sprite s2) {        
	        this.revertPosition1();
	    }
	}		*/
}

