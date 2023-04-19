package zelda.scenary;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import zelda.Zelda;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

public class AbstractTile implements Tile { 
    
    protected Zelda game; 
    
    private double x;
    
    private double y;
    
    private int rows;
    
    private int cols;
    
    private int height;
    
    private int width;
    
    public enum Layer {
        BACKGROUND,
        FORGROUND
    }
    
    private int size;
   
    private Sprite[][] sprites;
    
    protected AbstractTile(Zelda game, int rows, int cols, int width, int height) {
        this.game = game;
        this.rows = rows;
        this.cols = cols;
        this.width = width;
        this.height = height;
        this.sprites = new Sprite[this.rows][this.cols];
        this.size = 0;
    }
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (sprites[i][j] != null) {
                    sprites[i][j].setLocation(x + i * (this.width / this.cols),
                            y + j * (this.height / this.rows));
                }
            }
        }

    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int size() {
        return this.size;
    }
    
    public void add(Sprite sprite, int layer) {
        int x = this.size % this.cols;
        int y = this.size/this.cols;
        sprites[x][y] = sprite;
        sprite.setLayer(layer);
        this.size++;
    }
    
    public boolean moveTo(long enlapsTimed, double x, double y, double speed) {
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (this.sprites[i][j] != null) {
                    this.sprites[i][j].moveTo(enlapsTimed, x, y, speed);
                }
            }
        }
        this.x = this.sprites[0][0].getX();
        this.y = this.sprites[0][0].getY();
        return (x - 1 < this.x && this.x < x + 1 && y - 1 < this.y && this.y < y + 1);
    }
    
    
    public List<Sprite> getSprites() {
        List<Sprite> sprites = new ArrayList<Sprite>();
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (this.sprites[i][j] != null) {
                    sprites.add(this.sprites[i][j]);
                }
            }
        }
        return sprites;
    }

    
    public void add(String img, int layer) {
        this.add(new Sprite(this.game.getImage(img)), layer);
    }
    
    public void add(String img) {
        this.add(new Sprite(this.game.getImage(img)));
    }
    
    public void add(Sprite sprite) {
        this.add(sprite, 0);
    }
    
    public SpriteGroup getForeground() {
        SpriteGroup foreground = new SpriteGroup("");
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (sprites[i][j] != null && sprites[i][j].getLayer() >= 0) {
                    foreground.add(sprites[i][j]);
                }
            }
        }
        return foreground;
    }
    
    public SpriteGroup getBackground() {
        SpriteGroup background = new SpriteGroup("");
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (sprites[i][j] != null && sprites[i][j].getLayer() < 0) {
                    background.add(sprites[i][j]);
                }
            }
        }
        return background;
    }
    
    public void update(long elapsedTime) {
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (sprites[i][j] != null) {
                    sprites[i][j].update(elapsedTime);
                }
            }
        }
    }
    
    public void render(Graphics2D g) {
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (sprites[i][j] != null) {
                    sprites[i][j].render(g);
                }
            }
        }
    }
    
}
