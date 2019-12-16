package behaviour;

import components.Board;
import components.Draught;

import java.awt.*;
import java.util.ArrayList;

public class QueenBehavior implements DraughtBehavior {
    private Color colorWhite = new Color(240, 97, 1);
    private Color colorBlack = new Color(50,25,112);

    @Override
    public void drawDrought(Graphics g, int x, int y, int size, boolean isWhite) {
        Color color = isWhite ? colorWhite : colorBlack;
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    @Override
    public boolean checkDestination(int x, int y, int enemyX, int enemyY, Draught current) {
        if(current.getY() > enemyY)
            return y < enemyY && isOnOneDiagonal(current, enemyX, enemyY);
        else
            return y > enemyY && isOnOneDiagonal(current, enemyX, enemyY);
    }

    @Override
    public void setEnemies(Draught given) {
        int step = given.getSize();
        var onSameDiagonal = getEnemiesOnSameDiagonal(given);

        ArrayList<Draught> onRightUpDiagonal = new ArrayList<>();
        ArrayList<Draught> onLeftUpDiagonal = new ArrayList<>();
        ArrayList<Draught> onRightDownDiagonal = new ArrayList<>();
        ArrayList<Draught> onLeftDownDiagonal = new ArrayList<>();

        for(Draught draught : onSameDiagonal){
            if(given.getX() > draught.getX() && given.getY() < draught.getY())
                onRightUpDiagonal.add(draught);
            if(given.getX() > draught.getX() && given.getY() > draught.getY())
                onLeftUpDiagonal.add(draught);
            if(given.getX() < draught.getX() && given.getY() < draught.getY())
                onRightDownDiagonal.add(draught);
            if(given.getX() < draught.getX() && given.getY() > draught.getY())
                onLeftDownDiagonal.add(draught);
        }

        ArrayList<Draught> enemies = new ArrayList<>();
        enemies.add(getClosest(given, onRightUpDiagonal, step, -step));
        enemies.add(getClosest(given, onLeftUpDiagonal, -step, step));
        enemies.add(getClosest(given, onRightDownDiagonal, step, step));
        enemies.add(getClosest(given, onLeftDownDiagonal, -step, -step));

        for(Draught enemy : enemies)
            if(enemy != null)
                given.setEnemy(enemy);
    }

    @Override
    public boolean checkDirection(Draught draught, int x, int y) {
        return draught.getX() != x && draught.getY() != y && isOnOneDiagonal(draught, x, y);
    }

    private boolean isOnOneDiagonal(Draught draught, int enemyX, int enemyY) {
        return Math.abs(draught.getX() - enemyX) == Math.abs(draught.getY() - enemyY);
    }

    private Draught getClosest(Draught given, ArrayList<Draught> droughts, int value1, int value2){
        Draught closest = null;
        if(droughts.size() > 0){
            closest = droughts.get(0);
            for(Draught draught : droughts)
                if(Math.abs(given.getY() - draught.getY()) < Math.abs(given.getY() - closest.getY())
                        && Board.isCellEmpty(closest.getX() + value1, closest.getY() + value2))
                    closest = draught;
        }
        return closest;
    }

    private ArrayList<Draught> getEnemiesOnSameDiagonal(Draught given) {
        ArrayList<Draught> onOneDiagonal = new ArrayList<>();

        for(Draught draught : Board.allDraughts)
            if((isOnOneDiagonal(given, draught.getX(), draught.getY()) && given.isWhite() != draught.isWhite()))
                onOneDiagonal.add(draught);

        return onOneDiagonal;
    }
}
