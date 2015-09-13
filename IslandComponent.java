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
                if(sectionIndex == 197) {
                    sections.add(currentSection = new Section(sectionIndex++));;
                } else {
                    sections.add(new Section(sectionIndex++));
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        nextSection();
        currentSection.draw(g2);
    }

    public void nextSection() {
        Tile userTile = currentSection.getUserTile();

        if(currentSection.getMoved() && userTile instanceof NextSectionTile) {
            NextSectionTile userNextSectionTile = (NextSectionTile)userTile;
            String direction = userNextSectionTile.getDirection();
            Section nextSection;

            if(direction.equals("left")) {
                nextSection = sections.get(currentSection.getIndex() - 1);
                nextSection.addCharacter(currentSection.getUser(), nextSection.getRightNextSectionTile());
            } else if(direction.equals("right")) {
                nextSection = sections.get(currentSection.getIndex() + 1);
                nextSection.addCharacter(currentSection.getUser(), nextSection.getLeftNextSectionTile());
            } else if(direction.equals("up")) {
                nextSection = sections.get(currentSection.getIndex() - 15);
                nextSection.addCharacter(currentSection.getUser(), nextSection.getDownNextSectionTile());
            } else {
                nextSection = sections.get(currentSection.getIndex() + 15);
                nextSection.addCharacter(currentSection.getUser(), nextSection.getUpNextSectionTile());
            }
            nextSection.resetMoved();
            currentSection.removeUser();
            currentSection = nextSection;
        }
    }

    public void updateMouse(int mouseX, int mouseY) {
        currentSection.updateMouse(mouseX, mouseY);
    }

    public void click() {
        currentSection.click();
    }
}
