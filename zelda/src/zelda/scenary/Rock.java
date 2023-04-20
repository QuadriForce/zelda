package zelda.scenary;

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
        }
        case ESCALIER:
        this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_1.GIF", -1);
        this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_2.GIF", 1);
        this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_3.GIF", -1);
        this.add("res/sprites/scenary/GREEN_SOUTH_WEST_ROCK_4.GIF", -1);
        break;
    }

    }

}
