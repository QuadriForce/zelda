
package zelda.scenary;

import com.golden.gamedev.object.Sprite;

import zelda.Zelda;

public class Floor extends AbstractTile {
    
    public enum Color {
        GREEN,
        DARCK,
        SAND
    }
    private Color color;
    
    public Floor(Zelda game, Color color) {
        super(game, 1, 1, 42, 42);
        this.color = color;
        switch(color) {
        case SAND:
            this.add(new Sprite(this.game.getImage("res/sprites/scenary/BGGF.gif")), -1);
            break;
        case GREEN:
            break;
        case DARCK:
            break;
        }
    }
}
