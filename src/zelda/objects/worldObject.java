package zelda.objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.*;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;
import zelda.Link;
import zelda.scenary.Board;

public class worldObject extends AnimatedSprite{

    private String name;
    private static final int ANIMATION_DELAY = 100;
    private Game game;
    private Timer fight;
    private int hitboxInset = 0;
    private CollisionManager manager;
    public worldObject(Game game, String name) {
        this.name = name;
        this.game = game;
        this.getAnimationTimer().setDelay(worldObject.ANIMATION_DELAY);
        this.manager = new LinkCollisionManager();
        this.initResources();
    }
    public String getName() {
        return name;
    }
    private void initResources() {


        BufferedImage[] sprites = new BufferedImage[1];
        switch (this.name){
            case "heart1":
                sprites[0] = game.getImage("res/sprites/Objects/OHC.gif");
                break;
            case "heart2":
                sprites[0] = game.getImage("res/sprites/Objects/OBH.gif");
                break;
            case "crystal":
                sprites[0] = game.getImage("res/sprites/Objects/OBP.gif");
                break;
            case "dungeonEntry":
                sprites[0] = game.getImage("res/sprites/Objects/OK.gif");
                break;
        }
        this.setImages(sprites);
        //this.setLocation(256, 300);
        this.setAnimationFrame(0, 0);
    }
    public void setBoard(Board board) {
        SpriteGroup object = new SpriteGroup("LINK SPRITE GROUPE");
        object.add(this);
        this.manager.setCollisionGroup(object, board.getForeground());
    }
    public void render(Graphics2D g) {
        super.render(g);
    }
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
