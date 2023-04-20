package zelda.scenary;

import java.awt.Graphics2D;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

public interface Tile {

    void setLocation(int x, int y);        
    double getX();
    double getY();
    int getWidth();
    int getHeight();
    int size();
    void add(Sprite sprite, int layer);
    void add(Sprite sprite);
    SpriteGroup getForeground();
    SpriteGroup getBackground();
    void update(long elapsedTime);
    void render(Graphics2D g);
    
}
