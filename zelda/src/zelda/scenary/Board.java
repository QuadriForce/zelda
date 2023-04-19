package zelda.scenary;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import zelda.Zelda;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

public class Board {
    
    public final static int HEIGHT = 11;
    
    public final static int WIDTH = 16;
    
    private int x;
    
    private int y;
    
    private AbstractTile[][] tiles;
    
    private Zelda game;
    
    private int size;
    
    private boolean display;
    
    public Board(Zelda game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.tiles = new AbstractTile[WIDTH][HEIGHT];
        this.size = 0;
        display = true;
    }

    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    
    public boolean moveTo(long elapsedTime, double x, double y, double speed) {
        boolean reached = false;
        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (tiles[i][j] != null) {
                    reached &= tiles[i][j].moveTo(elapsedTime, x, y, speed);
                }
            }
        }
        return reached;        
    }
    
    public List<Sprite> getSprites() {
        List<Sprite> sprites = new ArrayList<Sprite>();
        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (tiles[i][j] != null) {
                    sprites.addAll(tiles[i][j].getSprites());
                }
            }
        }
        return sprites;
    }
    
    public void add(AbstractTile tile) {
        int x = this.size % Board.WIDTH;
        int y = this.size/Board.WIDTH;
        tile.setLocation(x * 42, y * 42 + 126);
        tiles[x][y] = tile;
        this.size++;

   }
    
    public SpriteGroup getForeground() {
        SpriteGroup foreground = new SpriteGroup("");
        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (tiles[i][j] != null) {
                    SpriteGroup sg = tiles[i][j].getForeground(); 
                    Sprite sprites[] = sg.getSprites();
                    for (int k = 0; k < sg.getSize(); k++) {
                        foreground.add(sprites[k]);
                    }
                }
            }
        }
        return foreground;
    }
    
    public SpriteGroup getBackground() {
        SpriteGroup background = new SpriteGroup("");
        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (tiles[i][j] != null) {
                    SpriteGroup sg = tiles[i][j].getForeground(); 
                    Sprite sprites[] = sg.getSprites();
                    for (int k = 0; k < sg.getSize(); k++) {
                        background.add(sprites[k]);
                    }
                }
            }
        }
        return background;
    }
    
    public void update(long elapsedTime) {
        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].update(elapsedTime);
                }
            }
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < Board.WIDTH; i++) {
            for (int j = 0; j < Board.HEIGHT; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].render(g);
                }
            }
        }
    }
}
