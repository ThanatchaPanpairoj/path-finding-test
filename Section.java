import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Write a description of class Section here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Section
{
    private int mouseX, mouseY;
    private boolean confilct;
    private ArrayList<Tile> tiles;
    private ArrayList<Character> characters;
    private Tile currentTile;
    private Character user;

    public Section(boolean firstSection) {
        mouseX = 0;
        mouseY = 0;
        confilct = false;

        tiles = new ArrayList<Tile>();
        int tile = 1;
        for(int row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                Color tileColor = new Color((int)(Math.random() * 156), (int)(Math.random() * 156 + 100), (int)(Math.random() * 156));
                if(tile == 8) {
                    tiles.add(new NextSectionTile("up", tileColor, tile++, col * 40, row * 40));
                } else if (tile == 106) {
                    tiles.add(new NextSectionTile("left", tileColor, tile++, col * 40, row * 40));
                } else if (tile == 120) {
                    tiles.add(new NextSectionTile("right", tileColor, tile++, col * 40, row * 40));
                } else if (tile == 218) {
                    tiles.add(new NextSectionTile("down", tileColor, tile++, col * 40, row * 40));
                } else if(firstSection && (tile == 81 || tile == 96 || tile == 111 || tile == 126 || tile == 141 || tile == 142 || tile == 143 || tile == 144 || tile == 145 || tile == 130 || tile == 115 || tile == 100 || tile == 85)) {
                    //tiles.add(new BlockedTile(tile++, col * 40, row * 40));
                    tiles.add(new Tile(tileColor, tile++, col * 40, row * 40));
                } else {
                    tiles.add(new Tile(tileColor, tile++, col * 40, row * 40));
                }
            }
        }

        characters = new ArrayList<Character>();

        if(firstSection)
            addCharacter(new Character("You", 280, 280), 280, 280);
    }

    public void draw(Graphics2D g2) {
        if(!confilct) {
            for(Tile t : tiles) {
                int xDifference = mouseX - t.getX();
                int yDifference = mouseY - t.getY();
                if(xDifference < 40 && xDifference >= 0 && yDifference < 40 && yDifference >= 0 && !(t instanceof BlockedTile)) {
                    currentTile = t;
                    t.highlight(true);
                } else
                    t.highlight(false);
                t.resetDistanceValue();
            }

            calculatePath(user.getTile(), currentTile);
            for(Tile t : tiles)
                t.draw(g2);
            for(Character c : characters)
                c.draw(g2);
        } else {

        }
    }

    public void addCharacter(Character character, int x, int y) {
        characters.add(character);
        if(character.getName().equals("You"))
            user = character;
        for(Tile t : tiles)
            if(Math.abs(x - t.getX()) < 40 && Math.abs(y - t.getY()) < 40) {
                ArrayList<Tile> startingPath = new ArrayList<Tile>();
                startingPath.add(t);
                character.setPath(startingPath);
                character.setCurrentTile(t);
                t.addCharacter(character);
                break;
            }
    }

    public void updateInfo(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void click() {
        user.setPath(calculatePath(user.getTile(), currentTile));
    }

    public ArrayList<Tile> calculatePath(Tile from, Tile to) {
        ArrayList<Tile> path = new ArrayList<Tile>();
        assignDistanceValues(0, to);
        int toX = to.getX();
        int toY = to.getY();

        Tile nextTile = from;
        while(nextTile != to) {
            int x = nextTile.getX();
            int y = nextTile.getY();
            int nextTileIndex = tiles.indexOf(nextTile);
            ArrayList<Tile> possiblePath = new ArrayList<Tile>();
            if(x > 0)
                possiblePath.add(leftOf(nextTileIndex));
            if(x < 560)
                possiblePath.add(rightOf(nextTileIndex));
            if(y > 0)
                possiblePath.add(above(nextTileIndex));
            if(y < 560)
                possiblePath.add(below(nextTileIndex));

            int shortestDistance = nextTile.getDistanceValue();
            for(Tile t : possiblePath) {
                int tileDistance = t.getDistanceValue();
                if(tileDistance <= shortestDistance && Math.sqrt(Math.pow(toX - t.getX(), 2) + Math.pow(toY - t.getY(), 2)) < Math.sqrt(Math.pow(toX - nextTile.getX(), 2) + Math.pow(toY - nextTile.getY(), 2))) {
                    nextTile = t;
                    shortestDistance = tileDistance;
                }
            }

            path.add(nextTile);
            nextTile.highlight(true);
        }
        return path;
    }

    public void assignDistanceValues(int i, Tile to) {
        to.assignDistanceValue(i);
        int x = to.getX();
        int y = to.getY();
        int tileIndex = tiles.indexOf(to);

        if(x > 0) {
            Tile left = leftOf(tileIndex);
            if(left.getDistanceValue() > i + 1)
                assignDistanceValues(i + 1, left);
        } 
        if(x < 560) {
            Tile right = rightOf(tileIndex);
            if(right.getDistanceValue() > i + 1)
                assignDistanceValues(i + 1, right);
        }
        if(y > 0) {
            Tile up = above(tileIndex);
            if(up.getDistanceValue() > i + 1)
                assignDistanceValues(i + 1, up);
        }
        if(y < 560) {
            Tile down = below(tileIndex);
            if(down.getDistanceValue() > i + 1)
                assignDistanceValues(i + 1, down);
        } 
    }

    public Tile leftOf(int tileIndex) {
        return tiles.get(tileIndex - 1);
    }

    public Tile rightOf(int tileIndex) {
        return tiles.get(tileIndex + 1);
    }

    public Tile above(int tileIndex) {
        return tiles.get(tileIndex - 15);
    }

    public Tile below(int tileIndex) {
        return tiles.get(tileIndex + 15);
    }
}
