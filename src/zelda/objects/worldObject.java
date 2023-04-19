package zelda.objects;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import zelda.Link;
import zelda.objects.Blade;
import zelda.objects.Shield;
import zelda.scenary.Board;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;
public class Objects extends AnimatedSprite{
    private String name;
    private static final int ANIMATION_DELAY = 100;
    private Game game;
    private Timer figth;
    private CollisionManager manager;
    public Objects(Game game, String name) {
        this.name = name;
        this.game = game;
        this.getAnimationTimer().setDelay(Objects.ANIMATION_DELAY);
        this.initResources();
    }
    private void initResources() {
        BufferedImage[] sprites = new BufferedImage[1];
        if(name.equals("key"))
            sprites[0] = game.getImage("res/sprites/Objects/OK.gif");
        else if (name.equals("door"))
            sprites[0] = game.getImage("res/sprites/Donjeon/1/DG1LDN.gif");
        this.setImages(sprites);
        this.setLocation(256, 300);
        this.setAnimationFrame(0, 0);
    }
    public void render(Graphics2D g) {
        super.render(g);
    }
}
