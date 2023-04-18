package zelda.scenary;

import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

import zelda.Zelda;

import com.golden.gamedev.object.PlayField;

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
        this.x = 0;
        this.y = 0;
    }
    private void initRessources() {
        this.menu = new QuestMenu(this.game);

        try (Stream<Path> paths = Files.walk(mapDir)) {
            paths.filter(Files::isRegularFile)
                    .forEachOrdered(mapPath -> {
                        String mapName = mapPath.getFileName().toString();
                        mapName = mapName.substring(0,mapName.lastIndexOf('.'));

                        int x = Character.getNumericValue(mapName.charAt(0));
                        int y = Character.getNumericValue(mapName.charAt(1));
                        String typeMap = mapName.split("-")[1];
                        System.out.println(x + "-"+y+"-"+mapName+"-"+typeMap);
                        Board tempBoard = new Board(this.game, x, y);

                        try (Scanner sc = new Scanner(mapPath);) {
                            while(sc.hasNext()) {
                                String word = sc.next();

                                switch (typeMap){
                                    case "foret":
                                        switch (word){
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
                                        }
                                }
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.add(tempBoard);
                    });
        }catch (IOException e) {
            e.printStackTrace();
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