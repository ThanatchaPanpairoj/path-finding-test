import java.awt.Graphics;
import java.awt.Graphics2D;

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
        
        for(int sectionIndex = 0, row = 0; row < 15; row++) {
            for(int col = 0; col < 15; col++) {
                if(sectionIndex == 212) {
                    sections.add(currentSection = new Section(sectionIndex++));;
                } else {
                    sections.add(new Section(sectionIndex++));
                }
            }
        }
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
