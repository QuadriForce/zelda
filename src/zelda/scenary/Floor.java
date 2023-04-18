
package zelda.scenary;

import com.golden.gamedev.object.Sprite;

import zelda.Zelda;

public class Floor extends AbstractTile {

    public enum Color {
        GREEN,
        DARCK1,
        DARCK2,
        SAND
    }

    private Color color;

    public Floor(Zelda game, Color color) {
        super(game, 1, 1, 42, 42);
        this.color = color;
        switch (color) {
            case SAND:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/BGGF.gif")), -1);
                break;
            case GREEN:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/BGGB.gif")), -1);
                break;
            case DARCK1:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/DG1F7-G.gif")), -1);
                break;
            case DARCK2:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/4/DG4F6.gif")), -1);
                break;
        }
    }
}
