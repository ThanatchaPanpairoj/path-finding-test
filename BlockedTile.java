import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Write a description of class BlockedTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlockedTile extends Tile
{
    public BlockedTile(int index, int x, int y) {
        super(Color.GRAY, index, x, y);
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.BLACK);
        g2.draw(new Rectangle(getX() + 1, getY() + 1, 38, 38));
    }
}
