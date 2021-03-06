import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Color;

/**
 * Write a description of class Object here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Character
{
    private int x, y, nextX, nextY;
    private boolean mouseOver;
    private String name;
    private Tile currentTile;
    private ArrayList<Tile> path;

    public Character(String name, Tile startingTile) {
        this.x = startingTile.getX();
        this.y = startingTile.getY();
        mouseOver = false;
        this.name = name;
        this.currentTile = startingTile;
    }

    public void draw(Graphics2D g2) {
        Ellipse2D.Double circle = new Ellipse2D.Double(x + 5, y + 5, 30, 30);
        //could be any object, doesn't have to be an ellipse
        advance();
        g2.setPaint(Color.BLUE);
        g2.fill(circle);

        if(mouseOver) {

        }
    }

    public void setPath(ArrayList<Tile> path) {
        this.path = path;
    }

    public void setCurrentTile(Tile t) {
        currentTile = t;
        x = t.getX();
        y = t.getY();
    }

    public void advance() {
        if(path.size() != 0) {
            Tile to = path.get(0);
            if(currentTile != to) {
                currentTile.empty();
                currentTile = to;
            }
            nextX = to.getX();
            nextY = to.getY();
            to.addCharacter(this);

            if(nextX < x)
                x -= 10;
            else if(nextX > x)
                x += 10;
            else if(nextY < y)
                y -= 10;
            else if(nextY > y)
                y += 10;

            if(nextX == x && nextY == y)
                path.remove(0);
        }
    }

    public void mouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public Tile getTile() {
        return currentTile;
    }
}
