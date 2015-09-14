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
    private int sectionIndex, mouseX, mouseY;
    private boolean confilct, moved;
    private Character user;
    private Tile currentTile, upNextSectionTile, leftNextSectionTile, rightNextSectionTile, downNextSectionTile;
    private ArrayList<Tile> tiles;
    private ArrayList<Character> characters;

    public Section(int sectionIndex) {
        this.sectionIndex = sectionIndex;
        mouseX = 0;
        mouseY = 0;
        confilct = false;
        moved = false;

        tiles = new ArrayList<Tile>();

        for(int tileIndex = 0, row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                Color tileColor = new Color((int)(Math.random() * 156), (int)(Math.random() * 156 + 100), (int)(Math.random() * 156));
                if(tileIndex == 7 && sectionIndex > 14) {
                    tiles.add(upNextSectionTile = new NextSectionTile("up", tileColor, tileIndex++, col * 40, row * 40));
                } else if (tileIndex == 105 && sectionIndex % 15 != 0) {
                    tiles.add(leftNextSectionTile = new NextSectionTile("left", tileColor, tileIndex++, col * 40, row * 40));
                } else if (tileIndex == 119 && (sectionIndex - 14) % 15 != 0) {
                    tiles.add(rightNextSectionTile = new NextSectionTile("right", tileColor, tileIndex++, col * 40, row * 40));
                } else if (tileIndex == 217 && sectionIndex < 210) {
                    tiles.add(downNextSectionTile = new NextSectionTile("down", tileColor, tileIndex++, col * 40, row * 40));
                } else if(sectionIndex == 197 && (tileIndex == 80 || tileIndex == 95 || tileIndex == 110 || tileIndex == 125 || tileIndex == 140 || tileIndex == 141 || tileIndex == 142 || tileIndex == 143 || tileIndex == 144 || tileIndex == 129 || tileIndex == 114 || tileIndex == 99 || tileIndex == 84)) {
                    tiles.add(new BlockedTile(tileIndex++, col * 40, row * 40));
                } else {
                    tiles.add(new Tile(tileColor, tileIndex++, col * 40, row * 40));
                }
            }
        }

        characters = new ArrayList<Character>();

        if(sectionIndex == 197) {
            addCharacter(new Character("You", tiles.get(112)), tiles.get(112));
        }
    }

    public void draw(Graphics2D g2) {
        Tile userTile = user.getTile();

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

            calculatePath(userTile, currentTile);

            for(Tile t : tiles)
                t.draw(g2);
            for(Character c : characters)
                c.draw(g2);

            if(!(userTile instanceof NextSectionTile)) {
                moved = true;
            }
        } else {

        }
    }

    public void addCharacter(Character character, Tile startingTile) {
        ArrayList<Tile> startingPath = new ArrayList<Tile>();
        startingPath.add(startingTile);
        character.setPath(startingPath);
        characters.add(character);

        if(character.getName().equals("You")) {
            user = character;
            user.setCurrentTile(startingTile);
            currentTile = startingTile;
        }
    }

    public void removeUser() {
        characters.remove(user);
    }

    public void updateMouse(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void click() {
        user.setPath(calculatePath(user.getTile(), currentTile));
        if(user.getTile() == currentTile) {
            moved = true;
        }
    }

    public void resetMoved() {
        moved = false;
    }

    public void assignDistanceValues(int i, Tile to) {
        if(!(to instanceof BlockedTile)) {
            to.assignDistanceValue(i);
            int x = to.getX();
            int y = to.getY();
            int tileIndex = to.getIndex();

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
    }

    public int getIndex() {
        return sectionIndex;
    }

    public boolean getMoved() {
        return moved;
    }

    public Character getUser() {
        return user;
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

    public Tile getLeftNextSectionTile() {
        return leftNextSectionTile;
    }

    public Tile getRightNextSectionTile() {
        return rightNextSectionTile;
    }

    public Tile getUpNextSectionTile() {
        return upNextSectionTile;
    }

    public Tile getDownNextSectionTile() {
        return downNextSectionTile;
    }

    public Tile getUserTile() {
        return user.getTile();
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
            int nextTileIndex = nextTile.getIndex();
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
                if(tileDistance < shortestDistance || (tileDistance == shortestDistance && Math.sqrt(Math.pow(toX - t.getX(), 2) + Math.pow(toY - t.getY(), 2)) < Math.sqrt(Math.pow(toX - nextTile.getX(), 2) + Math.pow(toY - nextTile.getY(), 2)))) {
                    nextTile = t;
                    shortestDistance = tileDistance;
                }
            }

            path.add(nextTile);
            nextTile.highlight(true);
        }
        return path;
    }
}
