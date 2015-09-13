import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile
{
    private int index, x, y, distanceValue;
    private boolean highlight;
    private Color color;
    private Character character;

    public Tile(Color color, int index, int x, int y) {
        this.color = color;
        this.index = index;
        this.x = x;
        this.y = y;
        character = null;
        highlight = false;
        distanceValue = 999;
    }

    public void draw(Graphics2D g2) {
        Rectangle r = new Rectangle(x, y, 40, 40);

        g2.setColor(color);
        g2.fill(r);
        g2.setColor(Color.BLACK);
        //g2.drawString(index + "", x + 20, y + 20);
        //g2.drawString(distanceValue + "", x + 20, y + 40);
        if(character != null || highlight){
            g2.setColor(Color.WHITE);
            g2.draw(new Rectangle(x + 1, y + 1, 38, 38));
        }
    }

    public void empty() {
        character = null;
    }

    public void addCharacter(Character character) {
        this.character = character;
    }

    public void highlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void assignDistanceValue(int newDistanceValue) {
        distanceValue = newDistanceValue;
    }

    public void resetDistanceValue() {
        distanceValue = 999;
    }
    
    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistanceValue() {
        return distanceValue;
    }
}
