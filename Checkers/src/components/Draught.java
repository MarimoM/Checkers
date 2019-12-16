package components;

import behaviour.DraughtBehavior;
import java.awt.*;
import java.util.ArrayList;

public class Draught {
    private int x, y;
    private int size = 50;
    private boolean white;
    private DraughtBehavior behaviour;
    private ArrayList<Draught> enemies = new ArrayList<>();

    public Draught(int x, int y, boolean isWhite, DraughtBehavior behaviour){
        this.x = x;
        this.y = y;
        this.white = isWhite;
        this.behaviour = behaviour;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getSize() { return size; }

    public void setY(int y) { this.y = y; }

    public void setX(int x) { this.x = x; }

    public boolean isWhite() { return white; }

    public void removeEnemies(){ this.enemies.clear(); }

    public void setEnemies() { behaviour.setEnemies(this); }

    public ArrayList<Draught> getEnemies() { return enemies; }

    public void setEnemy(Draught enemy) { this.enemies.add(enemy); }

    public void setBehaviour(DraughtBehavior behaviour) { this.behaviour = behaviour; }

    public boolean checkDirection(int x, int y){ return behaviour.checkDirection(this, x, y); }

    public void drawDrought(Graphics g, boolean isWhite){
        behaviour.drawDrought(g, this.getX(), this.getY(), this.getSize(), isWhite);
    }

    public boolean checkDestination(int x, int y, int enemyX, int enemyY){
        return behaviour.checkDestination(x, y, enemyX, enemyY, this);
    }

    public boolean checkDistance(int endX, int endY) {
        return (Math.abs(endY - this.getY()) == this.size && endX != this.getX()
                && (Math.abs(endX - this.getX()) == this.size));
    }
}
