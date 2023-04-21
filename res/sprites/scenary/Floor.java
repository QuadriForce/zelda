
package zelda.scenary;

import com.golden.gamedev.object.Sprite;

import zelda.Zelda;

public class Floor extends AbstractTile {

    public enum Color {
        GREEN,
        DONJONSOL1,
        DONJONSOL2,
        DONJONVIDE,
        SAND,
        WATER_MID_CENTER,
        WATER_MID_WEST,
        WATER_MID_EAST,
        WATER_SOUTH_EAST_CORNER,
        WATER_SOUTH_WEST_CORNER,
        WATER_SOUTH_CENTER_CORNER,
        WATER_NORTH_EAST_CORNER,
        WATER_NORTH_CENTER_CORNER,
        WATER_NORTH_WEST_CORNER,
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
            case DONJONVIDE:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/vide.gif")), -1);
                break;
            case DONJONSOL1:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/sol.gif")), -1);
                break;
            case DONJONSOL2:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/4/sol2.gif")), -1);
                break;
            case WATER_MID_CENTER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w5.gif")), 1);
                break;
            case WATER_MID_WEST:
                //this.add("res/sprites/scenary/w4.gif", 1);
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w4.gif")), 1);
                break;
            case WATER_MID_EAST:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w6.gif")), 1);
                break;
            case WATER_SOUTH_EAST_CORNER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w9.gif")), 1);
                break;
            case WATER_SOUTH_WEST_CORNER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w7.gif")), 1);
                break;
            case WATER_SOUTH_CENTER_CORNER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w8.gif")), 1);
                break;
            case WATER_NORTH_EAST_CORNER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w3.gif")), 1);
                break;
            case WATER_NORTH_CENTER_CORNER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w2.gif")), 1);
                break;
            case WATER_NORTH_WEST_CORNER:
                this.add(new Sprite(this.game.getImage("res/sprites/scenary/Water/w1.gif")), 1);
                break;

        }
    }
}
