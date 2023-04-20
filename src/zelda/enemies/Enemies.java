package zelda.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

import zelda.Orientation;
import zelda.scenary.Board;

public class Enemies extends AnimatedSprite{
	
	private static final double SPEED = 0.2;
    private static final int ANIMATION_DELAY = 100;
    private static final int FIGHT_TIMER = 300;
	public static final Orientation DEFAULT_ORIENTATION = null;
//	public static final WeaponType DEFAULT_WEAPON = WeaponType.EPEE; // Arme par défaut

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
	}
}

