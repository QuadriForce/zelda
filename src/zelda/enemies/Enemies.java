package zelda.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

import zelda.Orientation;
import zelda.Zelda;
import zelda.scenary.Board;

public class Enemies extends AnimatedSprite {
	private static final double SPEED = 0.2;
	private static final int ANIMATION_DELAY = 100;
	private static final int FIGHT_TIMER = 300;
	public static final Orientation DEFAULT_ORIENTATION = null;
	public static final WeaponType DEFAULT_WEAPON = WeaponType.EPEE; // Arme par défaut

	private Game game;
	private Orientation orientation;
	protected int life;
	protected Timer figth;
	private CollisionManager manager;
	private Weapon weapon;
	private double speed; 
	
	// Enumération pour les types d'armes
	public enum WeaponType {
		EPEE(0, 10), // power d'épée : 10
		ARC(1, 15), // power d'arc : 15
		HACHE(2, 20), // power de hache : 20
		LANCE(3, 30); // power de lance : 30

		private int type;
		private int power;

		private WeaponType(int type, int power) {
			this.type = type;
			this.power = power;
		}

		public int getType() {
			return type;
		}

		public int getPuissance() {
			return power;
		}

		public static WeaponType fromType(int type) {
			for (WeaponType WeaponType : WeaponType.values()) {
				if (WeaponType.getType() == type) {
					return WeaponType;
				}
			}
			throw new IllegalArgumentException("Type d'arme invalide : " + type);
		}
	}

	protected class Weapon {
		private WeaponType weaponType;

		public Weapon(WeaponType weaponType) {
			this.weaponType = weaponType;
		}

		public WeaponType getWeaponType() {
			return weaponType;
		}

		public void setWeaponType(WeaponType weaponType) {
			this.weaponType = weaponType;
		}
	}

	public Enemies(Game game, int life, double speed, WeaponType weaponType) {
		this.game = game;
		this.orientation = Enemies.DEFAULT_ORIENTATION;
		this.getAnimationTimer().setDelay(Enemies.getAnimationDelay());
		this.figth = new Timer(Enemies.getFightTimer());
		this.figth.setActive(false);
		this.manager = new EnemiesCollisionManager();
		this.initResources();
		this.weapon = new Weapon(weaponType);
		this.life = life;
		this.speed = SPEED; 
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
		// Ajouter l'objet AnimatedSprite (ennemies) au groupe de sprites du plateau de
		// jeu
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
				if (this.weapon.getWeaponType().equals(WeaponType.EPEE)) {
					this.setAnimationFrame(10, 10);
				} else if (this.weapon.getWeaponType().equals(WeaponType.ARC)) {
					this.setAnimationFrame(15, 15);
				} else if (this.weapon.getWeaponType().equals(WeaponType.HACHE)) {
					this.setAnimationFrame(20, 20); // cas du WEAPON3
				}
			} else if (this.orientation.equals(Orientation.NORTH)) {
				this.setY(this.getY() + 22);
				if (this.weapon.getWeaponType().equals(WeaponType.EPEE)) {
					this.setAnimationFrame(30, 30); // Logique spécifique pour WeaponType.WEAPON3
				} else if (this.weapon.getWeaponType().equals(WeaponType.ARC)) {
					this.setAnimationFrame(35, 35);
				} else if (this.weapon.getWeaponType().equals(WeaponType.LANCE)) {
					this.setAnimationFrame(40, 40);
				}

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

	public void changeWeapon(WeaponType weaponType) {
		this.weapon.setWeaponType(weaponType);
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
	
	public void removeFromGroup(SpriteGroup("Enemies")) { 
	    try {
	        // Ouverture du fichier audio dans un InputStream
	        InputStream audioSrc = Enemies.class.getResourceAsStream("kill.wav"); //TODO : a tester
	        // Création d'un AudioInputStream à partir de l'InputStream
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);

	        // Obtention d'un Clip audio
	        Clip clip = AudioSystem.getClip();

	        // Chargement du contenu audio dans le Clip
	        clip.open(audioStream);
	        
	        // Lecture du Clip audio
	        clip.start();

	    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	    
	    Enemies.remove(this); 
	}

	public void removeFromCollision() {
		// TODO Auto-generated method stub
	}

/*	public void increaseScore(int score) {
		// TODO Auto-generated method stub
	}*/

	// Dessiner à l'écran le sprite de l'ennemi et les objets associés
	public void render(Graphics2D g) {
		super.render(g);
	}

	// Empêcher les sprites de se chevaucher ou de traverser les uns les autres
	// lorsqu'une collision est détectée.
	private class EnemiesCollisionManager extends AdvanceCollisionGroup {
		public EnemiesCollisionManager() {
			this.pixelPerfectCollision = false;
		}

		public void collided(Sprite s1, Sprite s2) {
			this.revertPosition1();
		}
	}
}
