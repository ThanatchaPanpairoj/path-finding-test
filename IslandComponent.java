import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Write a description of class WorldComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IslandComponent extends JComponent
{
    private int width, height;
    private ArrayList<Section> sections;
    private Section currentSection;

    public IslandComponent(int width, int height) {
        this.width = width;
        this.height = height;
        
        sections = new ArrayList<Section>();
        currentSection = new Section(true);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        currentSection.draw(g2);
    }
    
    public void updateInfo(int mouseX, int mouseY) {
        currentSection.updateInfo(mouseX, mouseY);
    }
    
    public void click() {
        currentSection.click();
    }
}
