package behaviour;

import components.Draught;

import java.awt.*;

public interface DraughtBehavior {
    void setEnemies(Draught draught);
    boolean checkDirection(Draught draught, int x, int y);
    void drawDrought(Graphics g, int x, int y, int size, boolean isWhite);
    boolean checkDestination(int x, int y, int enemyX, int enemyY, Draught step);
}
