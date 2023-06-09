package zelda.scenary;

import com.golden.gamedev.object.Sprite;
import zelda.Zelda;

public class Rock extends AbstractTile {

    public enum Kind {
        GREEN_PLAIN,
        GREEN_PLAIN_BORDER,
        GREEN_INDENTED,
        GREEN_SOUTH_EAST_CORNER,
        GREEN_SOUTH_WEST_CORNER,
        GREEN_NORTH_EAST_CORNER,
        GREEN_NORTH_WEST_CORNER,
        ESCALIER,
        DESERT_PLAIN,
        DESERT_PLAIN_BORDER,
        DESERT_INDENTED,
        DESERT_SOUTH_EAST_CORNER,
        DESERT_SOUTH_WEST_CORNER,
        DESERT_SOUTH_CENTER,
        DESERT_NORTH_EAST_CORNER,
        DESERT_NORTH_WEST_CORNER,
        DESERT_NORTH_CENTER,
        DESERT_ROCK,
        DESERT_TOTEM,
        FORET_TOTEM,
        NEIGE_TOTEM,
        DONJON_BLOCK1,
        DONJON_BLOCK2,
        DONJON_DOOR_SOUTH,
        DONJON_DOOR_NORTH,
        DONJON_DOOR_WEST,
        DONJON_DOOR_EAST,
        DONJON_DOOR_CLOSED_SOUTH,
    }

    private Kind kind;

    public Rock(Zelda game, Kind kind) {
        super(game, 2, 2, 42, 42);
        this.kind = kind;
        switch (this.kind) {
            case GREEN_NORTH_WEST_CORNER:
                this.add("res/sprites/scenary/GREEN_NORTH_WEST_ROCK_1.GIF", -1);
                this.add("res/sprites/scenary/GREEN_NORTH_WEST_ROCK_2.GIF", 1);
                this.add("res/sprites/scenary/GREEN_NORTH_WEST_ROCK_3.GIF", 1);
                this.add("res/sprites/scenary/GREEN_NORTH_WEST_ROCK_4", 1);
                break;
            case GREEN_INDENTED:
                this.add("res/sprites/scenary/GREEN_INDENTED_ROCK_1.GIF", 1);
                this.add("res/sprites/scenary/GREEN_INDENTED_ROCK_2.GIF", 1);
                this.add("res/sprites/scenary/GREEN_INDENTED_ROCK_3.GIF", 1);
                this.add("res/sprites/scenary/GREEN_INDENTED_ROCK_4.GIF", 1);
                break;
            case GREEN_NORTH_EAST_CORNER:
                this.add("res/sprites/scenary/GREEN_NORTH_EAST_ROCK_1.GIF", -1);
                this.add("res/sprites/scenary/GREEN_NORTH_EAST_ROCK_2.GIF", -1);
                this.add("res/sprites/scenary/GREEN_NORTH_EAST_ROCK_3.GIF", 1);
                this.add("res/sprites/scenary/GREEN_NORTH_EAST_ROCK_4.GIF", 1);
                break;
            case GREEN_SOUTH_EAST_CORNER:
                this.add("res/sprites/scenary/GREEN_SOUTH_EAST_ROCK_1.GIF", 1);
                this.add("res/sprites/scenary/GREEN_SOUTH_EAST_ROCK_2.GIF", -1);
                this.add("res/sprites/scenary/GREEN_SOUTH_EAST_ROCK_3.GIF", -1);
                this.add("res/sprites/scenary/GREEN_SOUTH_EAST_ROCK_4.GIF", -1);
                break;
            case GREEN_PLAIN_BORDER:
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_1.GIF", 1);
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_2.GIF", 1);
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_3.GIF", -1);
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_4.GIF", -1);
                break;
            case GREEN_PLAIN:
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_1.GIF", 1);
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_2.GIF", 1);
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_3.GIF", 1);
                this.add("res/sprites/scenary/GREEN_PLAIN_ROCK_4.GIF", 1);
                break;
            case GREEN_SOUTH_WEST_CORNER:
                this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_1.GIF", -1);
                this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_2.GIF", 1);
                this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_3.GIF", -1);
                this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_4.GIF", -1);
                break;
            case ESCALIER:
                this.add("res/sprites/scenary/ESCALIERDESERT.gif", 1);
                this.add("res/sprites/scenary/ESCALIERFORET.gif", 1);
                this.add("res/sprites/scenary/ESCALIERDONJON.gif", 1);
                break;
            case DESERT_NORTH_EAST_CORNER:
                this.add("res/sprites/scenary/BGBNER.gif", 1);
                break;
            case DESERT_NORTH_CENTER:
                this.add("res/sprites/scenary/BGBNR.gif", 1);
                break;
            case DESERT_NORTH_WEST_CORNER:
                this.add("res/sprites/scenary/BGBNWR.gif", 1);
                break;
            case DESERT_SOUTH_EAST_CORNER:
                this.add("res/sprites/scenary/BGBSER.gif", 1);
                break;
            case DESERT_SOUTH_CENTER:
                this.add("res/sprites/scenary/DESERT_ROCK_7.gif", 1);
                break;
            case DESERT_SOUTH_WEST_CORNER:
                this.add("res/sprites/scenary/BGBSWR.gif", 1);
                break;
            case DESERT_ROCK:
                this.add("res/sprites/scenary/DESERT_ROCK.gif", 1);
                break;

            case DONJON_BLOCK1:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/b1.gif")), 1);
                break;
            case DONJON_BLOCK2:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/b2.gif")), 1);
                break;
            case DONJON_DOOR_SOUTH:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/porteSouth.gif")), -1);
                break;
            case DONJON_DOOR_NORTH:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/porteNorth.gif")), -1);
                break;
            case DONJON_DOOR_EAST:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/porteEast.gif")), -1);
                break;
            case DONJON_DOOR_WEST:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/porteWest.gif")), -1);
                break;
            case DONJON_DOOR_CLOSED_SOUTH:
                this.add(new Sprite(this.game.getImage("res/sprites/Dongeon/1/porteSouth1.gif")), -1);
                break;
        }
    }
}