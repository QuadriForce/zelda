package zelda.scenary;

import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import zelda.Zelda;

import com.golden.gamedev.object.PlayField;
import zelda.objects.worldObject;

public class Quest extends PlayField {

    private Zelda game;
    private static final Path mapDir = Paths.get("res/sprites/maps");
    private Board[][] boards;


    private QuestMenu menu;

    public int x;
    public int y;

    public Quest(Zelda game) {
        super();
        this.game = game;
        this.boards = new Board[5][5];
        this.initRessources();
        this.x = 4;
        this.y = 0;
    }

    private void initRessources() {
        this.menu = new QuestMenu(this.game);
        try (Stream<Path> paths = Files.walk(mapDir)) {
            paths.filter(Files::isRegularFile)
                    .forEachOrdered(mapPath -> {
                        String mapName = mapPath.getFileName().toString();
                        mapName = mapName.substring(0, mapName.lastIndexOf('.'));

                        int x = Character.getNumericValue(mapName.charAt(0));
                        int y = Character.getNumericValue(mapName.charAt(1));
                        String typeMap = mapName.split("-")[1];

                        Board tempBoard = new Board(this.game, x, y, null);
                        System.out.println(x + "-" + y + "-" + mapName + "-" + typeMap);


                        if (x == 4 && y == 0) {
                            ArrayList<worldObject> objectsList = new ArrayList<>();
                            objectsList.add(new worldObject(this.game, "keyDungeon"));
                            //objectsList.add(new worldObject(this.game, "dungeonEntry"));
                            tempBoard.setObjects(objectsList);
                        }

                        try (Scanner sc = new Scanner(mapPath);) {
                            while (sc.hasNext()) {
                                String word = sc.next();
                                switch (typeMap) {
                                    case "foret":
                                        switch (word) {
                                            case ".":
                                                tempBoard.add(new Floor(this.game, Floor.Color.SAND));
                                                break;
                                            case "x":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.GREEN_PLAIN));
                                                break;
                                            case "/":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.GREEN_SOUTH_EAST_CORNER));
                                                break;
                                            case "\\":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.GREEN_SOUTH_WEST_CORNER));
                                                break;
                                            case "M":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.GREEN_NORTH_EAST_CORNER));
                                                break;
                                            default:
                                                break;
                                        }
                                    case "rocher":
                                        switch (word) {
                                            case ".":
                                                tempBoard.add(new Floor(this.game, Floor.Color.SAND));
                                                break;
                                            case "r":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_ROCK));
                                                break;
                                            case "r1":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_NORTH_WEST_CORNER));
                                                break;
                                            case "r2":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_NORTH_CENTER));
                                                break;
                                            case "r3":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_NORTH_EAST_CORNER));
                                                break;
                                            case "r4":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_SOUTH_WEST_CORNER));
                                                break;
                                            case "r5":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_SOUTH_CENTER));
                                                break;
                                            case "r6":
                                                tempBoard.add(new Rock(this.game, Rock.Kind.DESERT_SOUTH_EAST_CORNER));
                                                break;
                                            case "w1":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_NORTH_WEST_CORNER));
                                                break;
                                            case "w2":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_NORTH_CENTER_CORNER));
                                                break;
                                            case "w3":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_NORTH_EAST_CORNER));
                                                break;
                                            case "w4":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_MID_WEST));
                                                break;
                                            case "w5":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_MID_CENTER));
                                                break;
                                            case "w6":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_MID_EAST));
                                                break;
                                            case "w7":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_SOUTH_WEST_CORNER));
                                                break;
                                            case "w8":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_SOUTH_CENTER_CORNER));
                                                break;
                                            case "w9":
                                                tempBoard.add(new Floor(this.game, Floor.Color.WATER_SOUTH_EAST_CORNER));
                                                break;
                                            default:
                                                break;
                                        }
                                    default:
                                        break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.add(tempBoard);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Board getCurrentBoard() {
        return this.boards[this.x][this.y];
    }
    public void add(Board board) {
        //this.addGroup(board.getBackground());
        //this.addGroup(board.getForground());
        this.boards[board.getX()][board.getY()] = board;
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        this.boards[this.x][this.y].update(elapsedTime);
        this.menu.update(elapsedTime);
    }

    public void render(Graphics2D g) {
        super.render(g);
        this.boards[this.x][this.y].render(g);
        this.menu.render(g);
    }
}

/*// Board (0, 0)
        Board b00 = new Board(this.game, 0, 0);
        // Board (0, 1)
        Board b01 = new Board(this.game, 0, 1);

        Path pMap1 = Paths.get("res/sprites/maps/map1.txt");
        Path pMap2 = Paths.get("res/sprites/maps/map2.txt");

        try (Scanner sc = new Scanner(pMap1);) {
            while(sc.hasNext()) {
                String word = sc.next();
                //System.out.println(word);
                switch (word){
                    case ".":
                        b00.add(new Floor(this.game, Floor.Color.SAND));
                        break;
                    case "x":
                        b00.add(new Rock(this.game, Rock.Kind.GREEN_PLAIN));
                        break;
                    case "/":
                        b00.add(new Rock(this.game, Rock.Kind.GREEN_SOUTH_EAST_CORNER));
                        break;
                    case "\\":
                        b00.add(new Rock(this.game, Rock.Kind.GREEN_SOUTH_WEST_CORNER));
                        break;
                    case "M":
                        b00.add(new Rock(this.game, Rock.Kind.GREEN_NORTH_EAST_CORNER));
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        this.add(b00);
        this.add(b01);*/