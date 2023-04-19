package zelda.objects;

import java.awt.Graphics2D;
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
        Set<String> dungeonKeys = new HashSet<>();
        dungeonKeys.add("keyDungeon");
        dungeonKeys.add("keyDungeon-1");
        dungeonKeys.add("keyDungeon-2");
        dungeonKeys.add("keyDungeon-3");

        Set<String> DoorsStair = new HashSet<>();
        DoorsStair.add("dungeonEntry");
        DoorsStair.add("upStair");
        DoorsStair.add("downStair");


        BufferedImage[] sprites = new BufferedImage[1];
        if (dungeonKeys.contains(name)) {
            sprites[0] = game.getImage("res/sprites/Objects/key.gif");
        }
        else if (DoorsStair.contains(name))
            sprites[0] = game.getImage("res/sprites/Dongeon/1/stair.gif");
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
}
