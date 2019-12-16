package behaviour;

import components.Board;
import components.Draught;
import main.Engine;

import java.awt.*;

public class SimpleBehavior implements DraughtBehavior {
    private Color colorWhite = new Color(225, 154, 11);
    private Color colorBlack = new Color(72,209,204);

    @Override
    public void drawDrought(Graphics g, int x, int y, int size, boolean isWhite) {
        Color color = isWhite ? colorWhite : colorBlack;
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    @Override
    public boolean checkDestination(int x, int y, int enemyX, int enemyY, Draught draught) {
        int step = draught.getSize();
        return (x == enemyX - step && y == enemyY + step) || (x == enemyX + step && y == enemyY - step)
                || (x == enemyX + step && y == enemyY + step) || (x == enemyX - step && y == enemyY - step);
    }

    @Override
    public void setEnemies(Draught draught) {
        int step = draught.getSize();
        int value1 = step;
        int value2 = step;

        for(int i = 0; i < 4; i++){
            if(i == 1)
                value2 = -value2;
            else if(i == 2){
                value1 = -value1;
                value2 = step;
            }
            else if(i == 3)
                value1 = value2 = -step;

            Draught enemy = getPotentialEnemy(draught.getX() + value1, draught.getY() + value2, draught);
            if(enemy != null && Board.isCellEmpty(enemy.getX() + value1, enemy.getY() + value2)) {
                draught.setEnemy(enemy);
            }
        }
    }

    @Override
    public boolean checkDirection(Draught draught, int x, int y) {
        if(!Engine.whiteTurn)
            return y <= draught.getY();
        else
            return y >= draught.getY();
    }

    private Draught getPotentialEnemy(int x, int y, Draught given) {
        for(Draught draught : Board.allDraughts)
            if (draught.getX() == x && draught.getY() == y && draught.isWhite() != given.isWhite())
                return draught;

        return null;
    }
}
