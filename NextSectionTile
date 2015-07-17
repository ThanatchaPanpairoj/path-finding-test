import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Write a description of class NextSectionTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NextSectionTile extends Tile
{
    private String direction;

    public NextSectionTile(String d, Color color, int i, int x, int y) {
        super(color, i, x, y);
        direction = d;
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(Color.WHITE);
        if(direction.equals("left")) {
            int arrowPointX = getX() + 5;
            int arrowPointY = getY() + 20;
            g2.drawLine(arrowPointX, arrowPointY, getX() + 35, getY() + 20);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 20, getY() + 5);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 20, getY() + 35);
        } else if(direction.equals("right")) {
            int arrowPointX = getX() + 35;
            int arrowPointY = getY() + 20;
            g2.drawLine(arrowPointX, arrowPointY, getX() + 5, getY() + 20);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 20, getY() + 5);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 20, getY() + 35);
        } else if(direction.equals("up")) {
            int arrowPointX = getX() + 20;
            int arrowPointY = getY() + 5;
            g2.drawLine(arrowPointX, arrowPointY, getX() + 20, getY() + 35);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 5, getY() + 20);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 35, getY() + 20);
        } else {
            int arrowPointX = getX() + 20;
            int arrowPointY = getY() + 35;
            g2.drawLine(arrowPointX, arrowPointY, getX() + 20, getY() + 5);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 5, getY() + 20);
            g2.drawLine(arrowPointX, arrowPointY, getX() + 35, getY() + 20);
        }
    }
}
